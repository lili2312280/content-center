package com.frontlinerlzx.contentcenter.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.16 - 下午 03:27
 * 类说明：
 */
@Slf4j
public class NacosWeightConfig extends AbstractLoadBalancerRule {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
    @Override
    public Server choose(Object o) {
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
        //获取微服务名称
        String name = loadBalancer.getName();
        //实现负载均衡算法
        NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
        //nacos client 自动通过权重的负载均衡算法，选择实例
        try {
            //权重获取
            Instance instance = namingService.selectOneHealthyInstance(name);
            log.info("选择的实例是： port={}, instant = {}",instance.getPort(),instance);
            return new NacosServer(instance);
        } catch (NacosException e) {
            e.printStackTrace();
            return null;
        }

    }
}

