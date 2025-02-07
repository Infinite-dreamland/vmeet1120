package com.rainbowdoor.vmeet.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AssetOwnershipMapper {
    @Select("SELECT COUNT(*) FROM AssetOwnership WHERE uid=#{uid} AND aid=#{aid}")
    int selectCountByUidAndAid(Integer uid, Integer aid);

    @Insert("INSERT INTO AssetOwnership (uid, aid) VALUES (#{uid}, #{aid})")
    void insertAssetOwnership(Integer uid, Integer aid);

}
