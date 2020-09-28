package com.frontlinerlzx.contentcenter.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.17 - 上午 09:54
 * 类说明：
 */

/**
 * feign的配置类
 * 不要加@Configuration注解，否则要放在application以外的包
 */
public class UserCenterFeignConfig {
    @Bean
    public Logger.Level level(){
        return Logger.Level.FULL;
    }
}
