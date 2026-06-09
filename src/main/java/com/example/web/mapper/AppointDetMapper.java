package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 预约明细表对应的Mapper
 */
@Mapper
public interface AppointDetMapper  extends BaseMapper<AppointDet> {

}
