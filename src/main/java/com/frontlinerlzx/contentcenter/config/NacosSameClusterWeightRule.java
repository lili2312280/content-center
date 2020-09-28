package com.frontlinerlzx.contentcenter.config;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.core.Balancer;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.16 - 下午 03:48
 * 类说明：
 */
@Slf4j
public class NacosSameClusterWeightRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }

    @Override
    public Server choose(Object o) {
        //拿到配置文件中的集群名称
        try {
            //获取nacos的配置的本服务的集群名
            String clusterName = nacosDiscoveryProperties.getClusterName();
            BaseLoadBalancer loadBalancer = (BaseLoadBalancer) this.getLoadBalancer();
            //获取微服务名称
            String name = loadBalancer.getName();

            NamingService namingService = nacosDiscoveryProperties.namingServiceInstance();
            //只能健康的实例，true
            List<Instance> instances = namingService.selectInstances(name, true);
            //过滤相同集群下的所有实例
            List<Instance> sameInstance = instances.stream()
                    //拿到配置文件的集群名判断微服务集群名是否为同一机房
                    .filter(instance -> instance.getClusterName().equals(clusterName))
                    .collect(Collectors.toList());
            List<Instance> instancesToBeChosen = new ArrayList<>();
            //判断是否为空的某机房实例集群
            if (CollectionUtils.isEmpty(sameInstance)) {
                instancesToBeChosen = instances;
                log.info("发生跨级群调用  name = {}", name);
            } else {

                instancesToBeChosen = sameInstance;
                log.info("可以在同机房调用 name = {}", name);
            }
            //最后基于权重来获取其中一个实例
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToBeChosen);
            return new NacosServer(instance);
        } catch (NacosException e) {
            log.error("出现异常：{}", e);
            return null;
        }
    }
}
//由于Balancer里面的方法是保护的，可以通过继承类来调用它的方法
class ExtendBalancer extends Balancer {
    //根据权重来拿到指定的实例
    public static Instance getHostByRandomWeight2(List<Instance> hosts) {
        return getHostByRandomWeight(hosts);
    }
}