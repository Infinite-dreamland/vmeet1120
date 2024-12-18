package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.Chat;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ChatMapper {
    @Insert("INSERT INTO Chat (from_uid, to_uid, content, created_time) VALUES (#{from_uid}, #{to_uid}, #{content}, NOW())")
    void insertChat(int from_uid, int to_uid, String content);

    @Update("UPDATE Chat SET visibility=#{visibility} WHERE id=#{id}")
    void updateVisibilityById(boolean visibility, int id);

    @Select("SELECT * FROM Chat WHERE id=#{id}")
    Chat selectChatById(int id);

    @Select("SELECT * FROM Chat WHERE (from_uid=#{uid1} AND to_uid=#{uid2} OR from_uid=#{uid2} AND to_uid=#{uid1}) AND visibility=1 ORDER BY created_time ASC LIMIT #{limit} OFFSET #{offset}")
    List<Chat> selectChatsByUid1AndUid2(int uid1, int uid2, int limit, int offset);

    @Select("SELECT COUNT(*) FROM Chat WHERE (from_uid=#{uid1} AND to_uid=#{uid2} OR from_uid=#{uid2} AND to_uid=#{uid1}) AND visibility=1")
    int selectCountByUid1AndUid2(int uid1, int uid2);

}
