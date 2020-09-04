package com.frontlinerlzx.contentcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.spring.annotation.MapperScan;

//扫描Mybatis的通用Mapper接口
@MapperScan("com.frontlinerlzx")
@SpringBootApplication
public class UserCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class, args);
    }

//    在Spring容器中创建一个对象，类型RestTemplate 名称/ID 是restTemplate
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}