package com.smartbot.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartbot.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户Mapper
 *
 * @author SmartBot Team
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
