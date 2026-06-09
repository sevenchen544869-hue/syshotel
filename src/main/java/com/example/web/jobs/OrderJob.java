package com.example.web.jobs;


import com.example.web.service.AppointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;



@Component
public class OrderJob {

    @Autowired
    private AppointService appointService;
    //每隔5秒检测一次
    @Scheduled(cron = "0/5 * * * * ?")
    public void cancelUnpaidOrders() {

        appointService.AutoCancel();
    }

    //查询出所有预约记录
    

}
