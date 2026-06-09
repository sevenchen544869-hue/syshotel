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
 * 酒店功能实现类
 */
@Service
public class HotelServiceImpl extends ServiceImpl<HotelMapper, Hotel> implements HotelService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Hotel表mapper对象
     */
    @Autowired
    private HotelMapper HotelMapper;

    @Autowired
    private AppointMapper AppointMapper;

    @Autowired
    private BaseService BaseService;
  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Hotel> BuilderQuery(HotelPagedInput input) {
       //声明一个支持酒店查询的(拉姆达)表达式
        LambdaQueryWrapper<Hotel> queryWrapper = Wrappers.<Hotel>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Hotel::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(Hotel::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getCheckInTip())) {
             queryWrapper = queryWrapper.like(Hotel::getCheckInTip, input.getCheckInTip());
       	 }
        if (Extension.isNotNullOrEmpty(input.getAddress())) {
             queryWrapper = queryWrapper.like(Hotel::getAddress, input.getAddress());
       	 }
        if (Extension.isNotNullOrEmpty(input.getMustRead())) {
             queryWrapper = queryWrapper.like(Hotel::getMustRead, input.getMustRead());
       	 }
        if (Extension.isNotNullOrEmpty(input.getHotelImages())) {
             queryWrapper = queryWrapper.like(Hotel::getHotelImages, input.getHotelImages());
       	 }
        if (Extension.isNotNullOrEmpty(input.getServices())) {
             queryWrapper = queryWrapper.like(Hotel::getServices, input.getServices());
       	 }
        if (Extension.isNotNullOrEmpty(input.getProviceCityArea())) {
             queryWrapper = queryWrapper.like(Hotel::getProviceCityArea, input.getProviceCityArea());
       	 }
        if (Extension.isNotNullOrEmpty(input.getNotice())) {
             queryWrapper = queryWrapper.like(Hotel::getNotice, input.getNotice());
       	 }

        if (input.getHotelUserId() != null) {
            queryWrapper = queryWrapper.eq(Hotel::getHotelUserId, input.getHotelUserId());
       	 }
        if (input.getIsPet() != null) {
            queryWrapper = queryWrapper.eq(Hotel::getIsPet, input.getIsPet());
       	 }
        if (input.getIsAudit() != null) {
            queryWrapper = queryWrapper.eq(Hotel::getIsAudit, input.getIsAudit());
       	 }
        
        if (Extension.isNotNullOrEmpty(input.getGlobalAddress())) {
            List<Integer> hotelIds = BaseService.GetHotelIdsByAddress(input.getGlobalAddress());
            if (hotelIds.size() > 0) {
                queryWrapper = queryWrapper.in(Hotel::getId, hotelIds);
            }
            else {
                queryWrapper = queryWrapper.eq(Hotel::getId, 0);
            }
        }
 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(Hotel::getName,input.getKeyWord()).or()   	 
          	   .like(Hotel::getCheckInTip,input.getKeyWord()).or()   	 
          	   .like(Hotel::getAddress,input.getKeyWord()).or()   	 
          	   .like(Hotel::getMustRead,input.getKeyWord()).or()   	 
          	   .like(Hotel::getHotelImages,input.getKeyWord()).or()   	 
          	   .like(Hotel::getServices,input.getKeyWord()).or()   	 
          	   .like(Hotel::getProviceCityArea,input.getKeyWord()).or()   	 
          	   .like(Hotel::getNotice,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理酒店对于的外键数据
     */
   private List<HotelDto> DispatchItem(List<HotelDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (HotelDto item : items) {           
          	            
           //查询出关联的AppUser表信息           
            AppUser  HotelUserEntity= AppUserMapper.selectById(item.getHotelUserId());
            item.setHotelUserDto(HotelUserEntity!=null?HotelUserEntity.MapToDto():new AppUserDto());  
            
            item.setCommentCount(GetTotalCommentCount(item.getId()));
            item.setCommentScore(GetAvgCommentScore(item.getId()));
            item.setTotalAppointCount(GetTotalAppointCount(item.getId()));
       }
       
     return items; 
   }

   /**
    * 获取评论数量
    * @param hotelId
    * @return
    */
   private Long GetTotalCommentCount(Integer hotelId) {
    LambdaQueryWrapper<Appoint> queryWrapper = Wrappers.<Appoint>lambdaQuery()
                .eq(Appoint::getHotelId, hotelId)
                .eq(Appoint::getAppointStatus, AppointStatusEnum.完成.index());
        return AppointMapper.selectCount(queryWrapper);
    }

    /**
     * 得到总预定人数
     * @param hotelId
     * @return
     */
    private Long GetTotalAppointCount(Integer hotelId) {
        LambdaQueryWrapper<Appoint> queryWrapper = Wrappers.<Appoint>lambdaQuery()
                .eq(Appoint::getHotelId, hotelId)
                .notIn(Appoint::getAppointStatus, AppointStatusEnum.用户取消.index(), AppointStatusEnum.酒店取消.index(), 
                AppointStatusEnum.系统取消.index(), AppointStatusEnum.申请取消.index());
        return AppointMapper.selectCount(queryWrapper);
    }
    /**
     * 获取评论平均分
     */
    private Double GetAvgCommentScore(Integer hotelId) {
        LambdaQueryWrapper<Appoint> queryWrapper = Wrappers.<Appoint>lambdaQuery()
                .eq(Appoint::getHotelId, hotelId)
                .eq(Appoint::getAppointStatus, AppointStatusEnum.完成.index());
        List<Appoint> appoints = AppointMapper.selectList(queryWrapper);
        if (appoints.isEmpty()) {
            return 0.0;
        }
        return appoints.stream().mapToDouble(Appoint::getCommentScore).average().orElse(0.0);
    }

    /**
     * 酒店分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<HotelDto> List(HotelPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Hotel> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Hotel::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Hotel> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取酒店数据
        IPage<Hotel> pageRecords= HotelMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= HotelMapper.selectCount(queryWrapper);
        //把Hotel实体转换成Hotel传输模型
        List<HotelDto> items= Extension.copyBeanList(pageRecords.getRecords(),HotelDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个酒店查询
     */
    @SneakyThrows
    @Override
    public HotelDto Get(HotelPagedInput input) {
       if(input.getId()==null)
        {
         return new HotelDto();
        }
      
       PagedResult<HotelDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new HotelDto()); 
    }

    /**
     *酒店创建或者修改
     */
    @SneakyThrows
    @Override
    public HotelDto CreateOrEdit(HotelDto input) {
        //声明一个酒店实体
        Hotel Hotel=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Hotel);
        //把传输模型返回给前端
        return Hotel.MapToDto();
    }
    /**
     * 酒店删除
     */
    @Override
    public void Delete(IdInput input) {
        Hotel entity = HotelMapper.selectById(input.getId());
        HotelMapper.deleteById(entity);
    }

    /**
     * 酒店批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }

/**
     * 热门酒店top5
     */
@SneakyThrows
@Override
public PagedResult<HotelDto> HotHotelList(HotelPagedInput input) {
        //构建where条件+排序
    LambdaQueryWrapper<Hotel> queryWrapper = BuilderQuery(input);
    
    //根据预约记录查询出预约人数最多的5个酒店你给我酒店的ids
    LambdaQueryWrapper<Appoint> appointQueryWrapper = Wrappers.<Appoint>lambdaQuery()
        .select(Appoint::getHotelId)
        .notIn(Appoint::getAppointStatus, AppointStatusEnum.用户取消.index(), AppointStatusEnum.酒店取消.index(), 
        AppointStatusEnum.系统取消.index(), AppointStatusEnum.申请取消.index())
        .groupBy(Appoint::getHotelId)
        .last("order by count(1) desc limit 5");
    List<Appoint> appoints = AppointMapper.selectList(appointQueryWrapper);
    List<Integer> hotelIds = appoints.stream().map(Appoint::getHotelId).collect(Collectors.toList());
    if (hotelIds.isEmpty())
    {
        return PagedResult.GetInstance(new ArrayList<>(), 0L);
    }
    queryWrapper.in(Hotel::getId, hotelIds);

    //构建一个分页查询的model
    Page<Hotel> page = new Page<>(input.getPage(), input.getLimit());
     //从数据库进行分页查询获取酒店数据
    IPage<Hotel> pageRecords= HotelMapper.selectPage(page, queryWrapper);
    //获取所有满足条件的数据行数
    Long totalCount= HotelMapper.selectCount(queryWrapper);
    //把Hotel实体转换成Hotel传输模型
    List<HotelDto> items= Extension.copyBeanList(pageRecords.getRecords(),HotelDto.class);

       DispatchItem(items);
    //返回一个分页结构给前端
    return PagedResult.GetInstance(items,totalCount);

}

}
