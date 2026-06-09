package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.WechatCollection;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会话集合表对应的Mapper
 */
@Mapper
public interface WechatCollectionMapper  extends BaseMapper<WechatCollection> {

}
