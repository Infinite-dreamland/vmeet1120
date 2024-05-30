package com.rainbowdoor.vmeet.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    @Insert("INSERT INTO Users (name, password, phone) VALUES (#{name}, #{password}, #{phone})")
    void insertUser(String name, String password, String phone);
}
