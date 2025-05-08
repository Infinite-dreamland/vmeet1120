package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.Asset;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AssetOwnershipMapper {
    @Select("SELECT COUNT(*) FROM AssetOwnership WHERE uid=#{uid} AND aid=#{aid}")
    int selectCountByUidAndAid(Integer uid, Integer aid);

    @Insert("INSERT INTO AssetOwnership (uid, aid) VALUES (#{uid}, #{aid})")
    void insertAssetOwnership(Integer uid, Integer aid);

    @Select("SELECT Assets.* FROM Assets, AssetOwnership WHERE Assets.id=AssetOwnership.aid AND AssetOwnership.uid=#{uid}")
    List<Asset> selectAssetsByUid(Integer uid);

    @Select("SELECT Assets.* FROM Assets, AssetOwnership WHERE Assets.id=AssetOwnership.aid AND AssetOwnership.uid=#{uid} AND Assets.type = #{type}")
    List<Asset> selectAssetsByUidAndType(Integer uid, String type);

}
