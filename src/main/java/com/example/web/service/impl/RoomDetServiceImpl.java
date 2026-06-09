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
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
 * 房间明细功能实现类
 */
@Service
public class RoomDetServiceImpl extends ServiceImpl<RoomDetMapper, RoomDet> implements RoomDetService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的RoomDet表mapper对象
     */
    @Autowired
    private RoomDetMapper RoomDetMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        
    @Autowired
    private RoomMapper  RoomMapper;                        

    /**
     * 操作数据库的RoomMatch表mapper对象
     */
    @Autowired
    private BaseService BaseService;
  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<RoomDet> BuilderQuery(RoomDetPagedInput input) {
       //声明一个支持房间明细查询的(拉姆达)表达式
        LambdaQueryWrapper<RoomDet> queryWrapper = Wrappers.<RoomDet>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, RoomDet::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getFloor())) {
             queryWrapper = queryWrapper.like(RoomDet::getFloor, input.getFloor());
       	 }
        if (Extension.isNotNullOrEmpty(input.getNo())) {
             queryWrapper = queryWrapper.like(RoomDet::getNo, input.getNo());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(RoomDet::getHotelId, input.getHotelId());
       	 }

        if (input.getRoomId() != null) {
            queryWrapper = queryWrapper.eq(RoomDet::getRoomId, input.getRoomId());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(RoomDet::getFloor,input.getKeyWord()).or()   	 
          	   .like(RoomDet::getNo,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理房间明细对于的外键数据
     */
   private List<RoomDetDto> DispatchItem(List<RoomDetDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (RoomDetDto item : items) {           
          	            
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
     * 房间明细分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<RoomDetDto> List(RoomDetPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<RoomDet> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(RoomDet::getCreationTime);
        }

        //构建一个分页查询的model
        Page<RoomDet> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取房间明细数据
        IPage<RoomDet> pageRecords= RoomDetMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= RoomDetMapper.selectCount(queryWrapper);
        //把RoomDet实体转换成RoomDet传输模型
        List<RoomDetDto> items= Extension.copyBeanList(pageRecords.getRecords(),RoomDetDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个房间明细查询
     */
    @SneakyThrows
    @Override
    public RoomDetDto Get(RoomDetPagedInput input) {
       if(input.getId()==null)
        {
         return new RoomDetDto();
        }
      
       PagedResult<RoomDetDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new RoomDetDto()); 
    }

    /**
     *房间明细创建或者修改
     */
    @SneakyThrows
    @Override
    public RoomDetDto CreateOrEdit(RoomDetDto input) {

        //针对同一个酒店下房间编号不能重复
        Long count = RoomDetMapper.selectCount(
            Wrappers.<RoomDet>lambdaQuery()
                .eq(RoomDet::getNo, input.getNo())
                .ne(input.getId() != null && input.getId() != 0, RoomDet::getId, input.getId())
                .eq(RoomDet::getHotelId, input.getHotelId())
        );
        if (count > 0) {
            throw new CustomException("同一个酒店的房间编号不能重复");
        }


        //声明一个房间明细实体
        RoomDet RoomDet=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(RoomDet);
        //把传输模型返回给前端
        return RoomDet.MapToDto();
    }

    /**
     * 查询出酒店所有的房间信息
     */
    @Override
    @SneakyThrows
    public List<RoomViewDateDto> GetAllRoomView(AllRoomViewQueryInput input) {
        List<RoomDet> roomDets = RoomDetMapper
            .selectList(Wrappers.<RoomDet>lambdaQuery().eq(RoomDet::getHotelId, input.getHotelId()));
        //根据楼层进行分组并且排序
        Map<String, List<RoomDet>> roomDetsMaps = roomDets.stream()
            .collect(Collectors.groupingBy(RoomDet::getFloor));
        //根据楼层进行排序
        List<String> floors = roomDetsMaps.keySet().stream()
            .sorted().collect(Collectors.toList());

        List<RoomViewDateDto> roomViewDates = new ArrayList<>();
        //根据传入的日期进行循环
        for (LocalDateTime date = input.getBeginDate(); date.isBefore(input.getEndDate()); date = date.plusDays(1)) {
            RoomViewDateDto roomViewDate = new RoomViewDateDto();
            roomViewDate.setDate(date);

             //根据楼层进行排序
        List<RoomViewDto> roomViews = new ArrayList<>();
        for (String floor : floors) {
            RoomViewDto roomView = new RoomViewDto();
            roomView.setFloor(floor);
            //得到该楼层的明细
            List<RoomDet> floorRoomDets = roomDetsMaps.get(floor);
            //进行房间号升序
            floorRoomDets.sort(Comparator.comparing(RoomDet::getNo));
            List<RoomDetViewDto> roomDetViews = new ArrayList<>();
            for (RoomDet roomDet : floorRoomDets) {
                RoomDetViewDto roomDetView = new RoomDetViewDto();
                roomDetView.setRoomDetId(roomDet.getId());
                roomDetView.setFloor(floor);    
                roomDetView.setNo(roomDet.getNo());
                roomDetView.setRoomId(roomDet.getRoomId());
                //得到最新房间的状态
                RoomMatchDto roomMatchDto = BaseService.GetRoomRoomMatch(input.getHotelId(), roomDet.getRoomId(), roomDet.getId(), date);
                if(roomMatchDto == null){
                    roomDetView.setRoomStatus(RoomStatusEnum.空闲.index());
                    
                }else{
                    roomDetView.setRoomStatus(roomMatchDto.getRoomStatus());
                    roomDetView.setAppointId(roomMatchDto.getAppointId());
                }


                Room RoomEntity = RoomMapper.selectById(roomDet.getRoomId());
                roomDetView.setRoomDto(RoomEntity!=null?RoomEntity.MapToDto():new RoomDto());
                roomDetViews.add(roomDetView);
            }
            roomView.setRoomDetViews(roomDetViews);
            roomViews.add(roomView);
        }
            roomViewDate.setRoomViews(roomViews);
            roomViewDates.add(roomViewDate);
        }

       
        return roomViewDates;
    }



    /**
     * 房间明细删除
     */
    @Override
    public void Delete(IdInput input) {
        RoomDet entity = RoomDetMapper.selectById(input.getId());
        RoomDetMapper.deleteById(entity);
    }

    /**
     * 房间明细批量删除
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
