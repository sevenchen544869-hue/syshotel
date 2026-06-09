package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.WechatMessage;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会话消息表对应的Mapper
 */
@Mapper
public interface WechatMessageMapper  extends BaseMapper<WechatMessage> {

}
