package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.CharacterConfig;
import com.rainbowdoor.vmeet.mapper.CharacterConfigMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class CharacterConfigService {
    @Resource
    CharacterConfigMapper characterConfigMapper;

    public CharacterConfig findConfigByAidAndUid(int aid, int uid) {
        return characterConfigMapper.findConfigByAidAndUid(aid, uid);
    }

    public void insertConfig(Integer aid, Integer uid, String config)
    {
        characterConfigMapper.insertConfig(aid, uid, config);
    }

    public void updateConfig(Integer aid, Integer uid, String config)
    {
        characterConfigMapper.updateConfig(aid, uid, config);
    }
}
