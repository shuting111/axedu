package com.zb.pojo;
import java.io.Serializable;
import java.util.Date;

/***
*   
*/
public class Purchaserecord implements Serializable {
    //
    private String purchId;
    //课程编号
    private Integer goodsId;
    //用户编号
    private Integer userId;
    //课程价格
    private Integer price;
    //有效性：1：有效；2：无效
    private Integer valid;
    //开课时间
    private String startTime;
    //结课时间
    private String endTime;
    //状态：1：有效；2失效
    private Integer status;
    //get set 方法

    public String getPurchId() {
        return purchId;
    }

    public void setPurchId(String purchId) {
        this.purchId = purchId;
    }

    public void setGoodsId (Integer  goodsId){
        this.goodsId=goodsId;
    }
    public  Integer getGoodsId(){
        return this.goodsId;
    }
    public void setUserId (Integer  userId){
        this.userId=userId;
    }
    public  Integer getUserId(){
        return this.userId;
    }
    public void setPrice (Integer  price){
        this.price=price;
    }
    public  Integer getPrice(){
        return this.price;
    }
    public void setValid (Integer  valid){
        this.valid=valid;
    }
    public  Integer getValid(){
        return this.valid;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setStatus (Integer  status){
        this.status=status;
    }
    public  Integer getStatus(){
        return this.status;
    }
}
