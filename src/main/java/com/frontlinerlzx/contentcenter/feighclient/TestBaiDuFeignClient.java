package com.frontlinerlzx.contentcenter.feighclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.17 - 上午 09:28
 * 类说明：
 */
//@FeignClient(name = "user-center", configuration = UserCenterFeignConfig.class)
@FeignClient(name = "baidu",url = "http://baidu.com")
public interface TestBaiDuFeignClient {
    @GetMapping("")
    String index();
}
