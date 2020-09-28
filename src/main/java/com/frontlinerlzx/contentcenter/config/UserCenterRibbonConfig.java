package com.frontlinerlzx.contentcenter.config;


import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.15 - 下午 03:54
 * 类说明：
 */
@Configuration
//@RibbonClients(defaultConfiguration = RibbonConfig.class)
public class UserCenterRibbonConfig {
    @Bean
    public IRule ribbonRule(){
        return new NacosSameClusterWeightRule();
    }
}
