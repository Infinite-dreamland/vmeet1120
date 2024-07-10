package com.rainbowdoor.vmeet.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssetMapper {
    @Select("SELECT COUNT(*) FROM Assets WHERE name=#{name} AND uid=#{uid} AND type=#{type}")
    int selectAssetCountByNameUidAndType(String name, Integer uid, String type);

    @Insert("INSERT INTO Assets (name, uid, type, privacy) VALUES (#{name}, #{uid}, #{type}, #{privacy})")
    void insertAsset(String name, Integer uid, String type, String privacy);
}
