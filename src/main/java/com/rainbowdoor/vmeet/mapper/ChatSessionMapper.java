package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.ChatSession;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChatSessionMapper {
    @Select("SELECT * FROM ChatSession WHERE uid1=#{uid1}")
    List<ChatSession> selectChatSessionsByUid1(int uid1);

    @Insert("INSERT INTO ChatSession (uid1, uid2) VALUES (#{uid1}, #{uid2})")
    void insertChatSession(int uid1, int uid2);

    @Select("SELECT COUNT(*) FROM ChatSession WHERE uid1=#{uid1} AND uid2=#{uid2}")
    int selectCountByUid1AndUid2(int uid1, int uid2);

    @Delete("DELETE FROM ChatSession WHERE id=#{id}")
    void deleteChatSessionById(int id);

    @Select("SELECT * FROM ChatSession WHERE id=#{id}")
    ChatSession selectChatSessionById(int id);
}
