package com.rainbowdoor.vmeet.mapper;

import com.rainbowdoor.vmeet.entity.Friendship;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FriendshipMapper {
    @Select("SELECT * FROM Friendship WHERE (uid1=#{uid} OR uid2=#{uid}) AND status=\"accepted\"")
    List<Friendship> selectAcceptedFriendshipsByUid(Integer uid);

    @Select("SELECT * FROM Friendship WHERE (uid1=#{uid} OR uid2=#{uid}) AND status=\"pending\"")
    List<Friendship> selectPendingFriendshipsByUid(Integer uid);

    @Select("SELECT * FROM Friendship WHERE (uid1=#{uid} OR uid2=#{uid}) AND status=\"rejected\"")
    List<Friendship> selectRejectedFriendshipsByUid(Integer uid);

    @Select("SELECT * FROM Friendship WHERE uid1=#{uid} OR uid2=#{uid}")
    List<Friendship> selectFriendshipsByUid(Integer uid);

    @Insert("INSERT INTO Friendship (uid1, uid2, status) VALUES (#{uid1}, #{uid2}, #{status})")
    void insertFriendship(Integer uid1, Integer uid2, String status);

    @Select("SELECT COUNT(*) FROM Friendship WHERE uid1=#{uid1} AND uid2=#{uid2} OR uid1=#{uid2} AND uid2=#{uid1}")
    int selectCountByUid1AndUid2(Integer uid1, Integer uid2);

    @Select("SELECT COUNT(*) FROM Friendship WHERE uid1=#{uid1} AND uid2=#{uid2}")
    int selectCountByUid1AndUid2Ordered(Integer uid1, Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid1=#{uid1} AND uid2=#{uid2} OR uid1=#{uid2} AND uid2=#{uid1}")
    Friendship selectFriendshipByUid1AndUid2(Integer uid1, Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid1=#{uid1} AND uid2=#{uid2}")
    Friendship selectFriendshipByUid1AndUid2Ordered(Integer uid1, Integer uid2);

    @Update("UPDATE Friendship SET status=#{status}, last_modified_time=NOW() WHERE uid1=#{uid1} AND uid2=#{uid2} OR uid1=#{uid2} AND uid2=#{uid1}")
    void updateStatusByUid1AndUid2(String status, Integer uid1, Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid2=#{uid2} AND status=\"accepted\"")
    List<Friendship> selectAcceptedFriendshipsByUid2(Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid2=#{uid2} AND status=\"pending\"")
    List<Friendship> selectPendingFriendshipsByUid2(Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid2=#{uid2} AND status=\"rejected\"")
    List<Friendship> selectRejectedFriendshipsByUid2(Integer uid2);

    @Select("SELECT * FROM Friendship WHERE uid2=#{uid2}")
    List<Friendship> selectFriendshipsByUid2(Integer uid2);

    @Delete("DELETE FROM Friendship WHERE uid1=#{uid1} AND uid2=#{uid2} OR uid1=#{uid2} AND uid2=#{uid1}")
    void deleteFriendshipByUid1AndUid2(Integer uid1, Integer uid2);
}
