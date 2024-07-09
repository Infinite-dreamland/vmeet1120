package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.mapper.AssetMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AssetService {
    @Resource
    private AssetMapper assetMapper;

    public int selectAssetCountByNameUidAndType(String name, Integer uid, String type)
    {
        return assetMapper.selectAssetCountByNameUidAndType(name, uid, type);
    }
}
