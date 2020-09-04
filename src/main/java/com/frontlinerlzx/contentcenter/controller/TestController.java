package com.frontlinerlzx.contentcenter.controller;
import com.frontlinerlzx.contentcenter.dao.content.ShareMapper;
import com.frontlinerlzx.contentcenter.domain.entity.content.Share;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.02 - 下午 03:50
 * 类说明：
 */
@RestController
@Slf4j
public class TestController {

    @Resource
    private ShareMapper shareMapper;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("test2")
    public List<ServiceInstance> setDiscoveryClient(){
        //查询指定服务的所有实例信息
        return discoveryClient.getInstances("user-center");
    }

    @GetMapping("test")
    public List<Share> testInsert() {
        //1. 做插入
        Share share = new Share();
        share.setId(0);
        share.setUserId(0);
        share.setTitle("xxx");
        share.setCreateTime(new Date());
        share.setUpdateTime(new Date());
        share.setIsOriginal(false);
        share.setAuthor("我");
        share.setCover("xxx");
        share.setSummary("");
        share.setPrice(0);
        share.setDownloadUrl("");
        share.setBuyCount(1);
        share.setShowFlag(false);
        share.setAuditStatus("");
        share.setReason("");

        shareMapper.insert(share);

        //2.做查询
        List<Share> shares = shareMapper.selectAll();
        return shares;
    }
}
