package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.UserInfo;
import com.rainbowdoor.vmeet.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserInfoService {
    @Resource
    private UserInfoMapper userInfoMapper;

    public UserInfo selectUserInfoByUid(Integer uid) {
        return userInfoMapper.selectUserInfoByUid(uid);
    }
}
