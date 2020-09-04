package com.frontlinerlzx.contentcenter.service.content;
import com.frontlinerlzx.contentcenter.dao.content.ShareMapper;
import com.frontlinerlzx.contentcenter.domain.dto.content.ShareDTO;
import com.frontlinerlzx.contentcenter.domain.dto.user.UserDTO;
import com.frontlinerlzx.contentcenter.domain.entity.content.Share;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.03 - 上午 11:34
 * 类说明：
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;

    private final RestTemplate restTemplate;

    private final DiscoveryClient discoveryClient;

    public ShareDTO findById(Integer id) {
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        List<ServiceInstance> instances = discoveryClient.getInstances("user-center");
        ServiceInstance usercenter = instances.get(0);
        System.out.println(id);
        String url = usercenter.getUri()+"/users/{id}";
        UserDTO userDTO = restTemplate.getForObject(
                url,
                UserDTO.class, userId
        );

        //消息的装配

        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());


        return shareDTO;
    }




}
