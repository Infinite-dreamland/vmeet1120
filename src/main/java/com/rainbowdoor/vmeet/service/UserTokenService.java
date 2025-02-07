package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.mapper.UserTokenMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserTokenService {
    @Resource
    private UserTokenMapper userTokenMapper;

    public void insertUserToken(String token, int uid)
    {
        userTokenMapper.insertUserToken(token, uid);
    }

    public Integer selectUidByToken(String token)
    {
        return userTokenMapper.selectUidByToken(token);
    }
}
