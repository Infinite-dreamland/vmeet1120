package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id, name FROM Users WHERE id=#{uid}")
    UserInfo selectUserInfoByUid(Integer uid);
}
