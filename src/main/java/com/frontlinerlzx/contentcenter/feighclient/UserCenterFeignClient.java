package com.frontlinerlzx.contentcenter.feighclient;

import com.frontlinerlzx.contentcenter.domain.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.17 - 上午 09:28
 * 类说明：
 */
//@FeignClient(name = "user-center", configuration = UserCenterFeignConfig.class)
@FeignClient(name = "user-center")
public interface UserCenterFeignClient {
    @GetMapping("/users/{id}")
    UserDTO findById(@PathVariable Integer id);
}
