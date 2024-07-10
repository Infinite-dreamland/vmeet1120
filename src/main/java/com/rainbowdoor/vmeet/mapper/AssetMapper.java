package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.Asset;
import com.rainbowdoor.vmeet.entity.UserAssetWithoutPrivacy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface AssetMapper {
    @Select("SELECT COUNT(*) FROM Assets WHERE name=#{name} AND uid=#{uid} AND type=#{type}")
    int selectAssetCountByNameUidAndType(String name, Integer uid, String type);

    @Insert("INSERT INTO Assets (name, uid, type, privacy) VALUES (#{name}, #{uid}, #{type}, #{privacy})")
    void insertAsset(String name, Integer uid, String type, String privacy);

    @Update("UPDATE Assets SET last_modified_time = NOW() WHERE name=#{name} AND uid=#{uid} AND type=#{type}")
    void updateLastModifiedTimeByNameUidAndType(String name, Integer uid, String type);

    @Select("SELECT * FROM Assets WHERE uid=#{uid} AND LOWER(name) COLLATE utf8mb4_general_ci LIKE CONCAT('%', LOWER(#{name}), '%') AND type=#{type} ORDER BY name ASC")
    List<Asset> selectAssetsByNameAndUidAndType(String name, Integer uid, String type);

    @Select("SELECT Users.name as username, Assets.id, Assets.name, Assets.type, Assets.created_time, Assets.last_modified_time FROM Assets, Users WHERE Assets.uid = Users.id AND LOWER(Assets.name) COLLATE utf8mb4_general_ci LIKE CONCAT('%', LOWER(#{name}), '%') AND type=#{type} AND privacy=\"public\" ORDER BY name ASC")
    List<UserAssetWithoutPrivacy> selectPublicAssetsByNameAndType(String name, String type);
}
