package com.example.web.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import lombok.SneakyThrows;
import java.io.IOException;
import com.example.web.tools.*;
import java.text.DecimalFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 房间价格功能实现类
 */
@Service
public class RoomPriceServiceImpl extends ServiceImpl<RoomPriceMapper, RoomPrice> implements RoomPriceService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的RoomPrice表mapper对象
     */
    @Autowired
    private RoomPriceMapper RoomPriceMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        
    @Autowired
    private RoomMapper  RoomMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<RoomPrice> BuilderQuery(RoomPricePagedInput input) {
       //声明一个支持房间价格查询的(拉姆达)表达式
        LambdaQueryWrapper<RoomPrice> queryWrapper = Wrappers.<RoomPrice>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, RoomPrice::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件

        if (input.getRoomId() != null) {
            queryWrapper = queryWrapper.eq(RoomPrice::getRoomId, input.getRoomId());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(RoomPrice::getHotelId, input.getHotelId());
       	 }
        if (input.getBeginActiveTimeRange() != null && !input.getBeginActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(RoomPrice::getBeginActiveTime, input.getBeginActiveTimeRange().get(1));
            queryWrapper = queryWrapper.ge(RoomPrice::getBeginActiveTime, input.getBeginActiveTimeRange().get(0));
        }
        if (input.getEndActiveTimeRange() != null && !input.getEndActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(RoomPrice::getEndActiveTime, input.getEndActiveTimeRange().get(1));
            queryWrapper = queryWrapper.ge(RoomPrice::getEndActiveTime, input.getEndActiveTimeRange().get(0));
        }
      

 
    
      return queryWrapper;
    }
  
    /**
     * 处理房间价格对于的外键数据
     */
   private List<RoomPriceDto> DispatchItem(List<RoomPriceDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (RoomPriceDto item : items) {           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
           
          	            
           //查询出关联的Room表信息           
            Room  RoomEntity= RoomMapper.selectById(item.getRoomId());
            item.setRoomDto(RoomEntity!=null?RoomEntity.MapToDto():new RoomDto());              
       }
       
     return items; 
   }
  
    /**
     * 房间价格分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<RoomPriceDto> List(RoomPricePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<RoomPrice> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(RoomPrice::getCreationTime);
        }

        //构建一个分页查询的model
        Page<RoomPrice> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取房间价格数据
        IPage<RoomPrice> pageRecords= RoomPriceMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= RoomPriceMapper.selectCount(queryWrapper);
        //把RoomPrice实体转换成RoomPrice传输模型
        List<RoomPriceDto> items= Extension.copyBeanList(pageRecords.getRecords(),RoomPriceDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个房间价格查询
     */
    @SneakyThrows
    @Override
    public RoomPriceDto Get(RoomPricePagedInput input) {
       if(input.getId()==null)
        {
         return new RoomPriceDto();
        }
      
       PagedResult<RoomPriceDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new RoomPriceDto()); 
    }

    /**
     *房间价格创建或者修改
     */
    @SneakyThrows
    @Override
    public RoomPriceDto CreateOrEdit(RoomPriceDto input) {
        //声明一个房间价格实体
        RoomPrice RoomPrice=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(RoomPrice);
        //把传输模型返回给前端
        return RoomPrice.MapToDto();
    }
    /**
     * 房间价格删除
     */
    @Override
    public void Delete(IdInput input) {
        RoomPrice entity = RoomPriceMapper.selectById(input.getId());
        RoomPriceMapper.deleteById(entity);
    }

    /**
     * 房间价格批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
}
