package com.frontlinerlzx.ribbonconfig;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.15 - 下午 04:10
 * 类说明：
 */
@Configuration
public class RibbonConfig {
    @Bean
    public IRule ribbonRule(){
        return new RandomRule();
    }

}
