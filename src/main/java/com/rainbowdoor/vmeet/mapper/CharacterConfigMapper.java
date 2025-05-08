package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.CharacterConfig;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.data.relational.core.sql.In;

@Mapper
public interface CharacterConfigMapper {
    @Select("SELECT * FROM CharacterConfig WHERE aid=#{aid} AND uid=#{uid}")
    public CharacterConfig findConfigByAidAndUid(Integer aid, Integer uid);

    @Insert("INSERT INTO CharacterConfig (aid, uid, config) VALUES (#{aid}, #{uid}, #{config})")
    public void insertConfig(Integer aid, Integer uid, String config);

    @Update("UPDATE CharacterConfig SET config=#{config} WHERE aid=#{aid} AND uid=#{uid}")
    public void updateConfig(Integer aid, Integer uid, String config);
}
