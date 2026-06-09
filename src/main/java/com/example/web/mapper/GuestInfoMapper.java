package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 旅客信息表对应的Mapper
 */
@Mapper
public interface GuestInfoMapper  extends BaseMapper<GuestInfo> {

}
