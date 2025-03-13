package com.atguigu.cloud.controller;

import cn.hutool.core.date.DateUtil;
import com.atguigu.cloud.apis.PayFeignApi;
import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import com.atguigu.cloud.resp.ReturnCodeEnum;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * @auther zzyy
 * @create 2023-11-04 16:00
 */
@RestController
public class OrderController {

    @Resource
    private PayFeignApi payFeignApi;

    /**
     * 一般情况下，通过浏览器的地址栏输入url，发送的只能是get请求
     * 我们底层调用的是post方法，模拟消费者发送get请求，客户端消费者
     * 参数可以不添加@RequestBody
     * @param payDTO
     * @return
     */
    @GetMapping("/feign/pay/add")
    public ResultData addOrder(PayDTO payDTO)
    {
        System.out.println("第一步：模拟本地addOrder新增订单成功(省略sql操作)，第二步：再开启addPay支付微服务远程调用");
        ResultData resultData = payFeignApi.addPay(payDTO);
        return resultData;
    }

    // 删除+修改操作作为家庭作业，O(∩_∩)O。。。。。。。
    @GetMapping("/feign/pay/get/{id}")
    public ResultData getPayInfo(@PathVariable("id") Integer id)
    {
        System.out.println("-------支付微服务远程调用，按照id查询订单支付流水信息");
        ResultData resultData = null;
        try
        {
            System.out.println("调用开始-----:"+ DateUtil.now());
            resultData = payFeignApi.getPayInfo(id);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("调用结束-----:"+DateUtil.now());
            ResultData.fail(ReturnCodeEnum.RC500.getCode(),e.getMessage());
        }
        return resultData;
    }

//    @DeleteMapping("/consumer/pay/del/{id}")
//    public void delOrder(@PathVariable("id") Integer id){
//         restTemplate.delete(PaymentSrv_URL + "/pay/del/"+id,ResultData.class);
//    }
//
//    @PutMapping("/consumer/pay/update")
//    public void updateOrder(@RequestBody PayDTO payDTO){
//        restTemplate.put(PaymentSrv_URL + "/pay/update",payDTO,ResultData.class);
//    }
//
//    @GetMapping("/consumer/pay/getAll")
//    public ResultData getOrderAll(){
//        return restTemplate.getForObject(PaymentSrv_URL + "/pay/getAll",ResultData.class);
//    }
//
//    @GetMapping(value = "/consumer/pay/get/info")
//    private String getInfoByConsul()
//    {
//        return restTemplate.getForObject(PaymentSrv_URL + "/pay/get/info", String.class);
//    }

    /**
     * openfeign天然支持负载均衡演示
     *
     * @return
     */
    @GetMapping(value = "/feign/pay/mylb")
    public String mylb()
    {
        return payFeignApi.mylb();
    }
}
