package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.Asset;
import com.rainbowdoor.vmeet.entity.UserAssetWithoutPrivacy;
import com.rainbowdoor.vmeet.mapper.AssetMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssetService {
    @Resource
    private AssetMapper assetMapper;

    public int selectAssetCountByNameUidAndType(String name, Integer uid, String type)
    {
        return assetMapper.selectAssetCountByNameUidAndType(name, uid, type);
    }

    public void insertAsset(String name, Integer uid, String type, String privacy)
    {
        assetMapper.insertAsset(name, uid, type, privacy);
    }

    public void updateLastModifiedTimeByNameUidAndType(String name, Integer uid, String type)
    {
        assetMapper.updateLastModifiedTimeByNameUidAndType(name, uid, type);
    }

    public List<Asset> selectAssetsByNameAndUidAndType(String name, Integer uid, String type)
    {
        return assetMapper.selectAssetsByNameAndUidAndType(name, uid, type);
    }

    public List<UserAssetWithoutPrivacy> selectPublicAssetsByNameAndType(String name, String type)
    {
        return assetMapper.selectPublicAssetsByNameAndType(name, type);
    }

    public List<UserAssetWithoutPrivacy> selectPublicAssetsByName(String name, String type)
    {
        return assetMapper.selectPublicAssetsByName(name, type);
    }

    public UserAssetWithoutPrivacy selectPublicAssetById(Integer id)
    {
        return assetMapper.selectPublicAssetById(id);
    }

    public void updateNumBuysById(Integer id)
    {
        assetMapper.updateNumBuysById(id);
    }
}
