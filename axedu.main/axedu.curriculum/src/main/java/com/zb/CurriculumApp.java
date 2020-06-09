package com.zb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author 王淑婷
 * @Description TODO
 * @Date 2020/6/4
 * @Version V1.0
 */
@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableScheduling  //开启定时任务
@EnableCaching //开启缓存
public class CurriculumApp {
    public static void main(String[] args) {
        SpringApplication.run(CurriculumApp.class);
    }
}
