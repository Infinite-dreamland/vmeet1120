package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public void insertUser(String name, String password, String phone)
    {
        userMapper.insertUser(name, password, phone);
    }

    public int selectCountByNameAndPassword(String name, String password)
    {
        return userMapper.selectCountByNameAndPassword(name, password);
    }
}
