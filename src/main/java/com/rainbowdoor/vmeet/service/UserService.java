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

    public void updatePasswordByPhone(String password, String phone)
    {
        userMapper.updatePasswordByPhone(password, phone);
    }

    public void updatePhoneByPhone(String newPhone, String OldPhone)
    {
        userMapper.updatePhoneByPhone(newPhone, OldPhone);
    }

    public void updateNameByPhone(String newName, String phone)
    {
        userMapper.updateNameByPhone(newName, phone);
    }

    public int selectCountByPhoneAndPassword(String phone, String password)
    {
        return userMapper.selectCountByPhoneAndPassword(phone, password);
    }

    public String selectPasswordByUsername(String username)
    {
        return userMapper.selectPasswordByUsername(username);
    }

    public String selectPasswordByPhone(String phone)
    {
        return userMapper.selectPasswordByPhone(phone);
    }

    public Integer selectIdByUsername(String username)
    {
        return userMapper.selectIdByUsername(username);
    }

    public Integer selectIdByPhone(String phone)
    {
        return userMapper.selectIdByPhone(phone);
    }


}
