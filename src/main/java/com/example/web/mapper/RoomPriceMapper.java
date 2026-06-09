package com.example.web.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.web.entity.*;
import org.apache.ibatis.annotations.Mapper;

/**
 * 房间价格表对应的Mapper
 */
@Mapper
public interface RoomPriceMapper  extends BaseMapper<RoomPrice> {

}
