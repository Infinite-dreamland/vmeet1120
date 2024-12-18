package com.rainbowdoor.vmeet.service;

import com.rainbowdoor.vmeet.entity.Friendship;
import com.rainbowdoor.vmeet.mapper.FriendshipMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendshipService {
    @Resource
    private FriendshipMapper friendshipMapper;

    public void insertFriendship(Integer uid1, Integer uid2, String status)
    {
        friendshipMapper.insertFriendship(uid1, uid2, status);
    }

    public void updateStatusByUid1AndUid2(Integer uid1, Integer uid2, String status)
    {
        friendshipMapper.updateStatusByUid1AndUid2(status, uid1, uid2);
    }

    public List<Friendship> selectAcceptedFriendshipsByUid(Integer uid)
    {
        return friendshipMapper.selectAcceptedFriendshipsByUid(uid);
    }

    public List<Friendship> selectPendingFriendshipsByUid(Integer uid)
    {
        return friendshipMapper.selectPendingFriendshipsByUid(uid);
    }

    public List<Friendship> selectRejectedFriendshipsByUid(Integer uid)
    {
        return friendshipMapper.selectRejectedFriendshipsByUid(uid);
    }

    public List<Friendship> selectFriendshipsByUid(Integer uid)
    {
        return friendshipMapper.selectFriendshipsByUid(uid);
    }

    public Friendship selectFriendshipByUid1AndUid2(Integer uid1, Integer uid2)
    {
        return friendshipMapper.selectFriendshipByUid1AndUid2(uid1, uid2);
    }

    public int selectCountByUid1AndUid2(Integer uid1, Integer uid2)
    {
        return friendshipMapper.selectCountByUid1AndUid2(uid1, uid2);
    }

    public int selectCountByUid1AndUid2Ordered(Integer uid1, Integer uid2)
    {
        return friendshipMapper.selectCountByUid1AndUid2Ordered(uid1, uid2);
    }

    public List<Friendship> selectAcceptedFriendshipsByUid2(Integer uid2)
    {
        return friendshipMapper.selectAcceptedFriendshipsByUid2(uid2);
    }

    public List<Friendship> selectPendingFriendshipsByUid2(Integer uid2)
    {
        return friendshipMapper.selectPendingFriendshipsByUid2(uid2);
    }

    public List<Friendship> selectRejectedFriendshipsByUid2(Integer uid2)
    {
        return friendshipMapper.selectRejectedFriendshipsByUid2(uid2);
    }

    public List<Friendship> selectFriendshipsByUid2(Integer uid2)
    {
        return friendshipMapper.selectFriendshipsByUid2(uid2);
    }

    public void deleteFriendshipByUid1AndUid2(Integer uid1, Integer uid2)
    {
        friendshipMapper.deleteFriendshipByUid1AndUid2(uid1, uid2);
    }

    public Friendship selectFriendshipByUid1AndUid2Ordered(Integer uid1, Integer uid2)
    {
        return friendshipMapper.selectFriendshipByUid1AndUid2Ordered(uid1, uid2);
    }

    public int selectAcceptedCountByUid1AndUid2(Integer uid1, Integer uid2)
    {
        return friendshipMapper.selectAcceptedCountByUid1AndUid2(uid1, uid2);
    }
}
