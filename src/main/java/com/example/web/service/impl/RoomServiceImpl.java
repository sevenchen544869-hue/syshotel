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
import java.util.Arrays;
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
 * 房间功能实现类
 */
@Service
public class RoomServiceImpl extends ServiceImpl<RoomMapper, Room> implements RoomService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Room表mapper对象
     */
    @Autowired
    private RoomMapper RoomMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        

    @Autowired
    private FavourableSettingMapper FavourableSettingMapper;
    @Autowired
    private RoomPriceMapper RoomPriceMapper;

    @Autowired
    private EquipmentInfoMapper EquipmentInfoMapper;


    @Autowired
    private RoomDetMapper RoomDetMapper;

    @Autowired
    private BaseService BaseService;
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Room> BuilderQuery(RoomPagedInput input) {
       //声明一个支持房间查询的(拉姆达)表达式
        LambdaQueryWrapper<Room> queryWrapper = Wrappers.<Room>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Room::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getEquipmentInfoIds())) {
             queryWrapper = queryWrapper.like(Room::getEquipmentInfoIds, input.getEquipmentInfoIds());
       	 }
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
             queryWrapper = queryWrapper.like(Room::getTitle, input.getTitle());
       	 }

        if (Extension.isNotNullOrEmpty(input.getHotelName())) {
            List<Hotel> hotelList = HotelMapper.selectList(Wrappers.<Hotel>lambdaQuery()
                .like(Hotel::getName, input.getHotelName()));
            if (hotelList.size() > 0) {
                List<Integer> hotelIds = hotelList.stream().map(Hotel::getId).collect(Collectors.toList());
                queryWrapper = queryWrapper.in(Room::getHotelId, hotelIds);
            }
            else {
                queryWrapper = queryWrapper.eq(Room::getHotelId, -1);
            }
        }

        if (input.getFeaturedRoomShape() != null) {
            queryWrapper = queryWrapper.eq(Room::getFeaturedRoomShape, input.getFeaturedRoomShape());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(Room::getHotelId, input.getHotelId());
       	 }

        if (input.getBreakfastType() != null) {
            queryWrapper = queryWrapper.eq(Room::getBreakfastType, input.getBreakfastType());
       	 }

        if (input.getRoomShape() != null) {
            queryWrapper = queryWrapper.eq(Room::getRoomShape, input.getRoomShape());
       	 }
        if (input.getIsFreeCancel() != null) {
            queryWrapper = queryWrapper.eq(Room::getIsFreeCancel, input.getIsFreeCancel());
       	 }
        if (input.getIsImmediatelyConfirm() != null) {
            queryWrapper = queryWrapper.eq(Room::getIsImmediatelyConfirm, input.getIsImmediatelyConfirm());
       	 }
        
            if (Extension.isNotNullOrEmpty(input.getGlobalAddress())) {
                List<Integer> hotelIds = BaseService.GetHotelIdsByAddress(input.getGlobalAddress());
                if (hotelIds.size() > 0) {
                    queryWrapper = queryWrapper.in(Room::getHotelId, hotelIds);
                }
                else {
                    queryWrapper = queryWrapper.eq(Room::getHotelId, 0);
                }
            }
            
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(Room::getEquipmentInfoIds,input.getKeyWord()).or()   	 
          	   .like(Room::getTitle,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理房间对于的外键数据
     */
   private List<RoomDto> DispatchItem(List<RoomDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (RoomDto item : items) {           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
       
            //查询出关联的优惠设置信息           
            List<FavourableSetting> FavourableSettingEntities= FavourableSettingMapper.selectList(Wrappers.<FavourableSetting>lambdaQuery().eq(FavourableSetting::getRoomId, item.getId()));
            item.setFavourableSettingDtos(Extension.copyBeanList(FavourableSettingEntities,FavourableSettingDto.class));
            //查询出关联的房间价格信息           
            List<RoomPrice> RoomPriceEntities= RoomPriceMapper.selectList(Wrappers.<RoomPrice>lambdaQuery().eq(RoomPrice::getRoomId, item.getId()));
            item.setRoomPriceDtos(Extension.copyBeanList(RoomPriceEntities,RoomPriceDto.class));

            // EquipmentInfoIds 可能为 null / 空字符串，需先做空值保护
            List<Integer> EquipmentInfoIdsList = new ArrayList<>();
            if (Extension.isNotNullOrEmpty(item.getEquipmentInfoIds())) {
                EquipmentInfoIdsList = Arrays.stream(item.getEquipmentInfoIds().split(","))
                    .filter(Extension::isNotNullOrEmpty)
                    .map(String::trim)
                    .filter(Extension::isNotNullOrEmpty)
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
            }

            if(EquipmentInfoIdsList.size() > 0){
                // 查询出关联的设施信息
                List<EquipmentInfo> EquipmentInfoEntities= EquipmentInfoMapper
                    .selectList(Wrappers.<EquipmentInfo>lambdaQuery().in(EquipmentInfo::getId, EquipmentInfoIdsList));
                item.setEquipmentInfoDtos(Extension.copyBeanList(EquipmentInfoEntities,EquipmentInfoDto.class));
            } else {
                item.setEquipmentInfoDtos(new ArrayList<>());
            }

            //查询出关联的房间明细信息
            Long RoomNumber = RoomDetMapper
                .selectCount(Wrappers.<RoomDet>lambdaQuery().eq(RoomDet::getRoomId, item.getId()));
            item.setRoomNumber(RoomNumber);
            


        }
        return items; 
    }
  
    /**
     * 房间分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<RoomDto> List(RoomPagedInput input) {
        // 先构建 where 条件
        LambdaQueryWrapper<Room> queryWrapper = BuilderQuery(input);

        //构建where条件+排序
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Room::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Room> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取房间数据
        IPage<Room> pageRecords= RoomMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= RoomMapper.selectCount(queryWrapper);
        //把Room实体转换成Room传输模型
        List<RoomDto> items= Extension.copyBeanList(pageRecords.getRecords(),RoomDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  


    /**
     * 用户房间查询
     */
    @SneakyThrows
    @Override
    public List<RoomDto> UserList(RoomPagedInput input) {
        // 先构建 where 条件
        LambdaQueryWrapper<Room> queryWrapper = BuilderQuery(input);

        List<Room> items = RoomMapper.selectList(queryWrapper);

        //把Room实体转换成Room传输模型
        List<RoomDto> itemsDto= Extension.copyBeanList(items,RoomDto.class);

        for (RoomDto item : itemsDto) {
            //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());

            //查询出关联的RoomDet表信息
            Long RoomNumber = RoomDetMapper.selectCount(Wrappers.<RoomDet>lambdaQuery().eq(RoomDet::getRoomId, item.getId()));
            item.setRoomNumber(RoomNumber);

            Double price = BaseService.GetRoomPriceByDateList(item.getHotelId(), item.getId()
            , input.getSelectDateList().get(0), input.getSelectDateList().get(1), false);
            item.setActualPrice(price);

            Double originalPrice = BaseService.GetRoomPriceByDateList(item.getHotelId(), item.getId()
            , input.getSelectDateList().get(0), input.getSelectDateList().get(1), true);
            item.setOriginalPrice(originalPrice);

            Long remainingRoomNumber = BaseService.GetRemainingRoomNumber(item.getHotelId(), item.getId()
            , input.getSelectDateList().get(0), input.getSelectDateList().get(1));
            item.setRemainingRoomNumber(remainingRoomNumber);
        }

        

        // 过滤真实价格
        if (input.getMaxPrice() != null && input.getMinPrice() != null) {
            itemsDto = itemsDto.stream().filter(item -> item.getActualPrice() >= input.getMinPrice()
                    && item.getActualPrice() <= input.getMaxPrice()).collect(Collectors.toList());
        }
        //返回一个分页结构给前端
        return itemsDto;

    }


    /**
     * 用户房间查询
     */
    @SneakyThrows
    @Override
    public RoomDto UserGet(RoomPagedInput input) {
        List<RoomDto> items = UserList(input);
        //返回一个分页结构给前端
        return items.stream().findFirst().orElse(new RoomDto());

    }


    /**
     * 单个房间查询
     */
    @SneakyThrows
    @Override
    public RoomDto Get(RoomPagedInput input) {
       if(input.getId()==null)
        {
         return new RoomDto();
        }
      
       PagedResult<RoomDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new RoomDto()); 
    }

    /**
     *房间创建或者修改
     */
    @SneakyThrows
    @Override
    public RoomDto CreateOrEdit(RoomDto input) {

        //针对同一个酒店房间的标题不能重复
        Long count = RoomMapper.selectCount(Wrappers.<Room>lambdaQuery().eq(Room::getTitle, input.getTitle())
            .ne(input.getId() != null && input.getId() != 0, Room::getId, input.getId())
            .eq(Room::getHotelId, input.getHotelId()));
        if(count > 0 ){
            throw new CustomException("同一个酒店房间的标题不能重复");
        }

            //先进行删除
            FavourableSettingMapper.delete(Wrappers.<FavourableSetting>lambdaQuery().eq(FavourableSetting::getRoomId, input.getId()));
            RoomPriceMapper.delete(Wrappers.<RoomPrice>lambdaQuery().eq(RoomPrice::getRoomId, input.getId()));

        //声明一个房间实体
        Room Room=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Room);

        // 兼容新增场景：保存后使用数据库中的真实房间ID
        Integer roomId = Room.getId();

        //添加优惠设置和房间价格设置
        if (input.getFavourableSettingDtos() != null) {
            for (FavourableSettingDto item : input.getFavourableSettingDtos()) {
                item.setRoomId(roomId);
                FavourableSettingMapper.insert(item.MapToEntity());
            }
        }
        if (input.getRoomPriceDtos() != null) {
            for (RoomPriceDto item : input.getRoomPriceDtos()) {
                item.setRoomId(roomId);
                RoomPriceMapper.insert(item.MapToEntity());
            }
        }
        //把传输模型返回给前端
        return Room.MapToDto();
    }

    /**
     *房间设施信息保存
     */
     @SneakyThrows
     @Override
     public RoomDto EquipmentInfoSave(RoomDto input) {
        
        Room room = RoomMapper.selectById(input.getId());
        room.setEquipmentInfoIds(input.getEquipmentInfoIds());
         
        
         saveOrUpdate(room);
 
       
         //把传输模型返回给前端
         return room.MapToDto();
     }


    /**
     * 房间删除
     */
    @Override
    public void Delete(IdInput input) {
        Room entity = RoomMapper.selectById(input.getId());
        RoomMapper.deleteById(entity);
    }

    /**
     * 房间批量删除
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
