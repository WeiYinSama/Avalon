package net.avalon.authority.mapper.generator;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: Weiyin
 * @Create: 2024/1/28 - 10:46
 */
@Mapper
public interface InviteCodeMapper {

    int useInviteCodeByCode(String code);

}
