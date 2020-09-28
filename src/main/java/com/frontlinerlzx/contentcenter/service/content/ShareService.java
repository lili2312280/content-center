package com.frontlinerlzx.contentcenter.service.content;
import com.frontlinerlzx.contentcenter.dao.content.ShareMapper;
import com.frontlinerlzx.contentcenter.domain.dto.content.ShareDTO;
import com.frontlinerlzx.contentcenter.domain.dto.user.UserDTO;
import com.frontlinerlzx.contentcenter.domain.entity.content.Share;
import com.frontlinerlzx.contentcenter.feighclient.UserCenterFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

/**
 * 创建者： 李芝贤
 * 创建时间： 2020.09.03 - 上午 11:34
 * 类说明：
 */
@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ShareService {

    private final ShareMapper shareMapper;

//    private final RestTemplate restTemplate;

    private final UserCenterFeignClient userCenterFeignClient;
    private final DiscoveryClient discoveryClient;

    public ShareDTO findById(Integer id) {
        Share share = shareMapper.selectByPrimaryKey(id);
        Integer userId = share.getUserId();
        //找到指定服务
        //Ribbon会根据user-center找到指定的服务uri进行请求，实现负载均衡+uri自动获取
//        UserDTO userDTO = restTemplate.getForObject(
//                "http://user-center/users/{userId}",
//                UserDTO.class, userId

//        );


        UserDTO userDTO = userCenterFeignClient.findById(userId);
        //消息的装配



        ShareDTO shareDTO = new ShareDTO();
        BeanUtils.copyProperties(share,shareDTO);
        shareDTO.setWxNickname(userDTO.getWxNickname());
        return shareDTO;
    }




}
