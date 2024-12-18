package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.Chat;
import com.rainbowdoor.vmeet.mapper.ChatMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {
    @Resource
    private ChatMapper chatMapper;

    public void insertChat(Integer from_uid, Integer to_uid, String content)
    {
        chatMapper.insertChat(from_uid, to_uid, content);
    }

    public void updateVisibilityById(Integer id, boolean visibility)
    {
        chatMapper.updateVisibilityById(visibility, id);
    }

    public Chat selectChatById(Integer id)
    {
        return chatMapper.selectChatById(id);
    }

    public List<Chat> selectChatsByUid1AndUid2(Integer uid1, Integer uid2, int limit, int offset)
    {
        return chatMapper.selectChatsByUid1AndUid2(uid1, uid2, limit, offset);
    }

    public int selectCountByUid1AndUid2(Integer uid1, Integer uid2)
    {
        return chatMapper.selectCountByUid1AndUid2(uid1, uid2);
    }
}
