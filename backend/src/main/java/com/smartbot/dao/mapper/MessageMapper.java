package com.smartbot.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartbot.domain.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * 消息Mapper
 *
 * @author SmartBot Team
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {

}
