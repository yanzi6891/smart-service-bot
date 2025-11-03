package com.smartbot.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smartbot.domain.entity.Conversation;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会话Mapper
 *
 * @author SmartBot Team
 */
@Mapper
public interface ConversationMapper extends BaseMapper<Conversation> {

}
