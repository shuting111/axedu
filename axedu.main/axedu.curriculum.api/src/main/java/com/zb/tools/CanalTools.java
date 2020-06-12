package com.zb.tools;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.common.utils.AddressUtils;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CanalTools {

    public void execution() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(AddressUtils.getHostIp(),
                11111), "example", "root", "ok");
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(),
                    eventType));

            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    //调用的删除的方法
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    //调用的添加的方法
                    addESData(rowData.getAfterColumnsList());
                } else {
                    //调用的修改的方法
                    updateESData(rowData.getAfterColumnsList());
                }
            }
        }
    }

    @Autowired
    private RestHighLevelClient client;
    //修改调用的方法
    private void updateESData(List<Column> columns) {
        System.out.println("同步修改数据");
        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
                data.put(column.getName(),column.getValue() );
            }
            System.out.println(id);
            UpdateRequest updateRequest = new UpdateRequest("my_curriculum", "doc", id);
            updateRequest.doc(data);
            UpdateResponse updateResponse = client.update(updateRequest);
            DocWriteResponse.Result result = updateResponse.getResult();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //添加调用的方法
    private void addESData(List<Column> columns) {
        System.out.println("同步添加数据");
        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
                data.put(column.getName(),column.getValue() );
            }
            System.out.println(id);
            //创建请求对象
            IndexRequest indexRequest = new IndexRequest("my_curriculum", "doc", id);
            //绑定数据
            indexRequest.source(data);
            //执行获取响应
            IndexResponse indexResponse = client.index(indexRequest);
            DocWriteResponse.Result result = indexResponse.getResult();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除调用的方法
    private void printColumn(List<Column> columns) {
        System.out.println("同步删除数据");
        try {
            Map<String, Object> data = new HashMap<>();
            String id ="";
            for (Column column : columns) {
                if(column.getName().equals("id")){
                    id = column.getValue()+"";
                    continue;
                }
            }
            System.out.println(id);
            DeleteRequest deleteRequest = new DeleteRequest("my_curriculum", "doc", id);
            DeleteResponse deleteResponse = client.delete(deleteRequest);
            DocWriteResponse.Result result = deleteResponse.getResult();
            System.out.println(result.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
