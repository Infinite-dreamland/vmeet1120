package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.ChatSession;
import com.rainbowdoor.vmeet.mapper.ChatSessionMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatSessionService {
    @Resource
    private ChatSessionMapper chatSessionMapper;

    public void insertChatSession(Integer uid1, Integer uid2)
    {
        chatSessionMapper.insertChatSession(uid1, uid2);
    }

    public int selectCountByUid1AndUid2(Integer uid1, Integer uid2)
    {
        return chatSessionMapper.selectCountByUid1AndUid2(uid1, uid2);
    }

    public List<ChatSession> selectChatSessionsByUid1(Integer uid1)
    {
        return chatSessionMapper.selectChatSessionsByUid1(uid1);
    }

    public void deleteChatSessionById(Integer id)
    {
        chatSessionMapper.deleteChatSessionById(id);
    }

    public ChatSession selectChatSessionById(Integer id)
    {
        return chatSessionMapper.selectChatSessionById(id);
    }
}
