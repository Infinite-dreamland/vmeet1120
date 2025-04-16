package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.Asset;
import com.rainbowdoor.vmeet.mapper.AssetOwnershipMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetOwnershipService {
    @Resource
    private AssetOwnershipMapper assetOwnershipMapper;

    public int selectCountByUidAndAid(Integer uid, Integer aid)
    {
        return assetOwnershipMapper.selectCountByUidAndAid(uid, aid);
    }

    public void insertAssetOwnership(Integer uid, Integer aid)
    {
        assetOwnershipMapper.insertAssetOwnership(uid, aid);
    }

    public List<Asset> selectAssetsByUid(Integer uid)
    {
        return assetOwnershipMapper.selectAssetsByUid(uid);
    }
}
