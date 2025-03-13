package com.atguigu.cloud.controller;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

/**
 * @auther zzyy
 * @create 2023-11-04 16:00
 */
@RestController
public class OrderController{

    //public static final String PaymentSrv_URL = "http://localhost:8001";//先写死，硬编码

    public static final String PaymentSrv_URL = "http://cloud-payment-service";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
     * 参数可以不添加@RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/consumer/pay/add")
    public ResultData addOrder(PayDTO payDTO){
        return restTemplate.postForObject(PaymentSrv_URL + "/pay/add",payDTO,ResultData.class);
    }
    // 删除+修改操作作为家庭作业，O(∩_∩)O。。。。。。。
    @GetMapping("/consumer/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/"+id, ResultData.class, id);
    }

    @DeleteMapping("/consumer/pay/del/{id}")
    public void delOrder(@PathVariable("id") Integer id){
         restTemplate.delete(PaymentSrv_URL + "/pay/del/"+id,ResultData.class);
    }

    @PutMapping("/consumer/pay/update")
    public void updateOrder(@RequestBody PayDTO payDTO){
        restTemplate.put(PaymentSrv_URL + "/pay/update",payDTO,ResultData.class);
    }

    @GetMapping("/consumer/pay/getAll")
    public ResultData getOrderAll(){
        return restTemplate.getForObject(PaymentSrv_URL + "/pay/getAll",ResultData.class);
    }
}
