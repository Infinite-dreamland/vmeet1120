package com.rainbowdoor.vmeet.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.StatementType;

@Mapper
public interface UserTokenMapper {

    @Options(statementType = StatementType.CALLABLE)
    @Select("CALL addToken(#{uid}, #{token})")
    void insertUserToken(String token, int uid);

    @Select("SELECT uid FROM UserTokens WHERE token=#{token}")
    Integer selectUidByToken(String token);
}
