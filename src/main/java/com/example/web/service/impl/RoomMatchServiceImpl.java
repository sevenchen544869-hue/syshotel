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
import java.util.Objects;
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
 * 分配房间功能实现类
 */
@Service
public class RoomMatchServiceImpl extends ServiceImpl<RoomMatchMapper, RoomMatch> implements RoomMatchService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的RoomMatch表mapper对象
     */
    @Autowired
    private RoomMatchMapper RoomMatchMapper;
    @Autowired
    private AppointMapper  AppointMapper;                        
    @Autowired
    private RoomDetMapper  RoomDetMapper;                        
    @Autowired
    private RoomMapper  RoomMapper;                        
    @Autowired
    private HotelMapper  HotelMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<RoomMatch> BuilderQuery(RoomMatchPagedInput input) {
       //声明一个支持分配房间查询的(拉姆达)表达式
        LambdaQueryWrapper<RoomMatch> queryWrapper = Wrappers.<RoomMatch>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, RoomMatch::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件

        if (input.getRoomStatus() != null) {
            queryWrapper = queryWrapper.eq(RoomMatch::getRoomStatus, input.getRoomStatus());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(RoomMatch::getHotelId, input.getHotelId());
       	 }

        if (input.getRoomId() != null) {
            queryWrapper = queryWrapper.eq(RoomMatch::getRoomId, input.getRoomId());
       	 }

        if (input.getRoomDetId() != null) {
            queryWrapper = queryWrapper.eq(RoomMatch::getRoomDetId, input.getRoomDetId());
       	 }

        if (input.getAppointId() != null) {
            queryWrapper = queryWrapper.eq(RoomMatch::getAppointId, input.getAppointId());
       	 }
      

 
    
      return queryWrapper;
    }
  
    /**
     * 处理分配房间对于的外键数据
     */
   private List<RoomMatchDto> DispatchItem(List<RoomMatchDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (RoomMatchDto item : items) {           
          	            
           //查询出关联的Appoint表信息           
            Appoint  AppointEntity= AppointMapper.selectById(item.getAppointId());
            item.setAppointDto(AppointEntity!=null?AppointEntity.MapToDto():new AppointDto());              
           
          	            
           //查询出关联的RoomDet表信息           
            RoomDet  RoomDetEntity= RoomDetMapper.selectById(item.getRoomDetId());
            item.setRoomDetDto(RoomDetEntity!=null?RoomDetEntity.MapToDto():new RoomDetDto());              
           
          	            
           //查询出关联的Room表信息           
            Room  RoomEntity= RoomMapper.selectById(item.getRoomId());
            item.setRoomDto(RoomEntity!=null?RoomEntity.MapToDto():new RoomDto());              
           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
       }
       
     return items; 
   }
  
    /**
     * 分配房间分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<RoomMatchDto> List(RoomMatchPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<RoomMatch> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(RoomMatch::getCreationTime);
        }

        //构建一个分页查询的model
        Page<RoomMatch> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取分配房间数据
        IPage<RoomMatch> pageRecords= RoomMatchMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= RoomMatchMapper.selectCount(queryWrapper);
        //把RoomMatch实体转换成RoomMatch传输模型
        List<RoomMatchDto> items= Extension.copyBeanList(pageRecords.getRecords(),RoomMatchDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个分配房间查询
     */
    @SneakyThrows
    @Override
    public RoomMatchDto Get(RoomMatchPagedInput input) {
       if(input.getId()==null)
        {
         return new RoomMatchDto();
        }
      
       PagedResult<RoomMatchDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new RoomMatchDto()); 
    }

    /**
     *分配房间创建或者修改
     */
    @SneakyThrows
    @Override
    public RoomMatchDto CreateOrEdit(RoomMatchDto input) {
        //声明一个分配房间实体
        RoomMatch RoomMatch=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(RoomMatch);
        //把传输模型返回给前端
        return RoomMatch.MapToDto();
    }
    /**
     * 批量分配房间创建或者修改
     */
    @SneakyThrows
    @Override
    public void BatchCreateOrEdit(List<RoomMatchDto> input) {
        if (input == null || input.isEmpty()) {
            throw new CustomException("分配数据不能为空");
        }
        for (RoomMatchDto item : input) {
            if (item == null) {
                continue;
            }
            //声明一个分配房间实体
            RoomMatch RoomMatch = item.MapToEntity();
            //调用数据库的增加或者修改方法
            saveOrUpdate(RoomMatch);
        }
        // 修改订单状态（兼容前端未传 appointId 的场景，避免空指针）
        Integer appointId = input.stream()
                .filter(Objects::nonNull)
                .map(RoomMatchDto::getAppointId)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
        if (appointId != null) {
            Appoint appoint = AppointMapper.selectById(appointId);
            if (appoint != null) {
                appoint.setAppointStatus(AppointStatusEnum.已入住.index());
                AppointMapper.updateById(appoint);
            }
        }
    }
    /**
     * 分配房间删除
     */
    @Override
    public void Delete(IdInput input) {
        RoomMatch entity = RoomMatchMapper.selectById(input.getId());
        RoomMatchMapper.deleteById(entity);
    }
    /**
     * 分配房间批量删除
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
