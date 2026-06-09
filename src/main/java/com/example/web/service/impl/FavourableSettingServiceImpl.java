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
 * 优惠设置功能实现类
 */
@Service
public class FavourableSettingServiceImpl extends ServiceImpl<FavourableSettingMapper, FavourableSetting> implements FavourableSettingService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的FavourableSetting表mapper对象
     */
    @Autowired
    private FavourableSettingMapper FavourableSettingMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        
    @Autowired
    private RoomMapper  RoomMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<FavourableSetting> BuilderQuery(FavourableSettingPagedInput input) {
       //声明一个支持优惠设置查询的(拉姆达)表达式
        LambdaQueryWrapper<FavourableSetting> queryWrapper = Wrappers.<FavourableSetting>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, FavourableSetting::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(FavourableSetting::getHotelId, input.getHotelId());
       	 }

        if (input.getRoomId() != null) {
            queryWrapper = queryWrapper.eq(FavourableSetting::getRoomId, input.getRoomId());
       	 }
        if (input.getEndActiveTimeRange() != null && !input.getEndActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(FavourableSetting::getEndActiveTime, input.getEndActiveTimeRange().get(1));
            queryWrapper = queryWrapper.ge(FavourableSetting::getEndActiveTime, input.getEndActiveTimeRange().get(0));
        }
        if (input.getBeginActiveTimeRange() != null && !input.getBeginActiveTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(FavourableSetting::getBeginActiveTime, input.getBeginActiveTimeRange().get(1));
            queryWrapper = queryWrapper.ge(FavourableSetting::getBeginActiveTime, input.getBeginActiveTimeRange().get(0));
        }
      

 
    
      return queryWrapper;
    }
  
    /**
     * 处理优惠设置对于的外键数据
     */
   private List<FavourableSettingDto> DispatchItem(List<FavourableSettingDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (FavourableSettingDto item : items) {           
          	            
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
     * 优惠设置分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<FavourableSettingDto> List(FavourableSettingPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<FavourableSetting> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(FavourableSetting::getCreationTime);
        }

        //构建一个分页查询的model
        Page<FavourableSetting> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取优惠设置数据
        IPage<FavourableSetting> pageRecords= FavourableSettingMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= FavourableSettingMapper.selectCount(queryWrapper);
        //把FavourableSetting实体转换成FavourableSetting传输模型
        List<FavourableSettingDto> items= Extension.copyBeanList(pageRecords.getRecords(),FavourableSettingDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个优惠设置查询
     */
    @SneakyThrows
    @Override
    public FavourableSettingDto Get(FavourableSettingPagedInput input) {
       if(input.getId()==null)
        {
         return new FavourableSettingDto();
        }
      
       PagedResult<FavourableSettingDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new FavourableSettingDto()); 
    }

    /**
     *优惠设置创建或者修改
     */
    @SneakyThrows
    @Override
    public FavourableSettingDto CreateOrEdit(FavourableSettingDto input) {
        //声明一个优惠设置实体
        FavourableSetting FavourableSetting=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(FavourableSetting);
        //把传输模型返回给前端
        return FavourableSetting.MapToDto();
    }
    /**
     * 优惠设置删除
     */
    @Override
    public void Delete(IdInput input) {
        FavourableSetting entity = FavourableSettingMapper.selectById(input.getId());
        FavourableSettingMapper.deleteById(entity);
    }

    /**
     * 优惠设置批量删除
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
