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
 * 寄存记录功能实现类
 */
@Service
public class StorageRecordServiceImpl extends ServiceImpl<StorageRecordMapper, StorageRecord> implements StorageRecordService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的StorageRecord表mapper对象
     */
    @Autowired
    private StorageRecordMapper StorageRecordMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        

    @Autowired
    private RoomDetMapper RoomDetMapper;
    @Autowired
    private AppointMapper AppointMapper;
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<StorageRecord> BuilderQuery(StorageRecordPagedInput input) {
       //声明一个支持寄存记录查询的(拉姆达)表达式
        LambdaQueryWrapper<StorageRecord> queryWrapper = Wrappers.<StorageRecord>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, StorageRecord::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(StorageRecord::getPhone, input.getPhone());
       	 }
        if (Extension.isNotNullOrEmpty(input.getNo())) {
             queryWrapper = queryWrapper.like(StorageRecord::getNo, input.getNo());
       	 }
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(StorageRecord::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getTakePicture())) {
             queryWrapper = queryWrapper.like(StorageRecord::getTakePicture, input.getTakePicture());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(StorageRecord::getHotelId, input.getHotelId());
       	 }
        if (input.getTakeTimeRange() != null && !input.getTakeTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(StorageRecord::getTakeTime, input.getTakeTimeRange().get(1));
            queryWrapper = queryWrapper.ge(StorageRecord::getTakeTime, input.getTakeTimeRange().get(0));
        }

        if (input.getRecordUserId() != null) {
            queryWrapper = queryWrapper.eq(StorageRecord::getRecordUserId, input.getRecordUserId());
        }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(StorageRecord::getPhone,input.getKeyWord()).or()   	 
          	   .like(StorageRecord::getNo,input.getKeyWord()).or()   	 
          	   .like(StorageRecord::getName,input.getKeyWord()).or()   	 
          	   .like(StorageRecord::getTakePicture,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理寄存记录对于的外键数据
     */
   private List<StorageRecordDto> DispatchItem(List<StorageRecordDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (StorageRecordDto item : items) {           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
       }
       
     return items; 
   }
  
    /**
     * 寄存记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<StorageRecordDto> List(StorageRecordPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<StorageRecord> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(StorageRecord::getCreationTime);
        }

        //构建一个分页查询的model
        Page<StorageRecord> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取寄存记录数据
        IPage<StorageRecord> pageRecords= StorageRecordMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= StorageRecordMapper.selectCount(queryWrapper);
        //把StorageRecord实体转换成StorageRecord传输模型
        List<StorageRecordDto> items= Extension.copyBeanList(pageRecords.getRecords(),StorageRecordDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个寄存记录查询
     */
    @SneakyThrows
    @Override
    public StorageRecordDto Get(StorageRecordPagedInput input) {
       if(input.getId()==null)
        {
         return new StorageRecordDto();
        }
      
       PagedResult<StorageRecordDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new StorageRecordDto()); 
    }

    /**
     *寄存记录创建或者修改
     */
    @SneakyThrows
    @Override
    public StorageRecordDto CreateOrEdit(StorageRecordDto input) {

        // Long roomDetCount = RoomDetMapper.selectCount(Wrappers.<RoomDet>lambdaQuery()
        // .eq(RoomDet::getNo, input.getNo())
        // .eq(null != input.getHotelId(), RoomDet::getHotelId, input.getHotelId()));
        // if(roomDetCount > 0){
        //     throw new CustomException("房间号不存在或已被预约");
        // }
        //先根据用户手机号码查询出用户
        AppUser appUser = AppUserMapper.selectOne(Wrappers.<AppUser>lambdaQuery()
            .eq(AppUser::getPhoneNumber, input.getPhone()));
        if(appUser == null){
            throw new CustomException("用户不存在");
        }

        Long appointCount = AppointMapper.selectCount(Wrappers.<Appoint>lambdaQuery()
            .eq(Appoint::getHotelId, input.getHotelId())
            .eq(Appoint::getToUserId, appUser.getId())
            .notIn(Appoint::getAppointStatus, AppointStatusEnum.酒店取消.index(), AppointStatusEnum.系统取消.index()
            ,AppointStatusEnum.用户取消.index(),AppointStatusEnum.申请取消.index()));
        if(appointCount == 0){
            throw new CustomException("用户没有预约该酒店的信息");
        }
        input.setRecordUserId(appUser.getId());


        //声明一个寄存记录实体
        StorageRecord StorageRecord=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(StorageRecord);
        //把传输模型返回给前端
        return StorageRecord.MapToDto();
    }

    /**
     * 领取寄存物品
     */
    @SneakyThrows
    @Override
    public void TakeGood(StorageRecordDto input) {
        StorageRecord entity = StorageRecordMapper.selectById(input.getId());
        entity.setTakeTime(LocalDateTime.now());
        StorageRecordMapper.updateById(entity);
    }

    /**
     * 寄存记录删除
     */
    @Override
    public void Delete(IdInput input) {
        StorageRecord entity = StorageRecordMapper.selectById(input.getId());
        StorageRecordMapper.deleteById(entity);
    }

    /**
     * 寄存记录批量删除
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
