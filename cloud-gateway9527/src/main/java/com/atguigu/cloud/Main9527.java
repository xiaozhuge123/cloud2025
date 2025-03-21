package com.atguigu.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.ZonedDateTime;

/**
 * @auther zzyy
 * @create 2023-11-20 12:38
 */
@SpringBootApplication
@EnableDiscoveryClient //服务注册和发现
public class Main9527
{
    public static void main(String[] args)
    {
        SpringApplication.run(Main9527.class,args);
        ZonedDateTime zbj = ZonedDateTime.now(); // 默认时区
        System.out.println(zbj);
    }
}