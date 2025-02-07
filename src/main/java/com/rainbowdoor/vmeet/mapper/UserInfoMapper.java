package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT id, name, credits, signature FROM Users, UserProfiles WHERE Users.id=#{uid} AND Users.id=UserProfiles.uid")
    UserInfo selectUserInfoByUid(Integer uid);

    @Insert("INSERT INTO UserProfiles (uid) VALUES (#{uid})")
    void insertUserProfile(Integer uid);

    @Update("UPDATE UserProfiles SET credits=#{credits} WHERE uid=#{uid}")
    void updateCreditsByUid(Integer credits, Float uid);
}
