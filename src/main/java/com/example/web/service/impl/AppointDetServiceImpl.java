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
 * 预约明细功能实现类
 */
@Service
public class AppointDetServiceImpl extends ServiceImpl<AppointDetMapper, AppointDet> implements AppointDetService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的AppointDet表mapper对象
     */
    @Autowired
    private AppointDetMapper AppointDetMapper;
    @Autowired
    private AppointMapper  AppointMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<AppointDet> BuilderQuery(AppointDetPagedInput input) {
       //声明一个支持预约明细查询的(拉姆达)表达式
        LambdaQueryWrapper<AppointDet> queryWrapper = Wrappers.<AppointDet>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, AppointDet::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getSex())) {
             queryWrapper = queryWrapper.like(AppointDet::getSex, input.getSex());
       	 }
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(AppointDet::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getAge())) {
             queryWrapper = queryWrapper.like(AppointDet::getAge, input.getAge());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(AppointDet::getPhone, input.getPhone());
       	 }
        if (Extension.isNotNullOrEmpty(input.getIdCard())) {
             queryWrapper = queryWrapper.like(AppointDet::getIdCard, input.getIdCard());
       	 }

        if (input.getAppointId() != null) {
            queryWrapper = queryWrapper.eq(AppointDet::getAppointId, input.getAppointId());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(AppointDet::getSex,input.getKeyWord()).or()   	 
          	   .like(AppointDet::getName,input.getKeyWord()).or()   	 
          	   .like(AppointDet::getAge,input.getKeyWord()).or()   	 
          	   .like(AppointDet::getPhone,input.getKeyWord()).or()   	 
          	   .like(AppointDet::getIdCard,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理预约明细对于的外键数据
     */
   private List<AppointDetDto> DispatchItem(List<AppointDetDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (AppointDetDto item : items) {           
          	            
           //查询出关联的Appoint表信息           
            Appoint  AppointEntity= AppointMapper.selectById(item.getAppointId());
            item.setAppointDto(AppointEntity!=null?AppointEntity.MapToDto():new AppointDto());              
       }
       
     return items; 
   }
  
    /**
     * 预约明细分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<AppointDetDto> List(AppointDetPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<AppointDet> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(AppointDet::getCreationTime);
        }

        //构建一个分页查询的model
        Page<AppointDet> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取预约明细数据
        IPage<AppointDet> pageRecords= AppointDetMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= AppointDetMapper.selectCount(queryWrapper);
        //把AppointDet实体转换成AppointDet传输模型
        List<AppointDetDto> items= Extension.copyBeanList(pageRecords.getRecords(),AppointDetDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个预约明细查询
     */
    @SneakyThrows
    @Override
    public AppointDetDto Get(AppointDetPagedInput input) {
       if(input.getId()==null)
        {
         return new AppointDetDto();
        }
      
       PagedResult<AppointDetDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new AppointDetDto()); 
    }

    /**
     *预约明细创建或者修改
     */
    @SneakyThrows
    @Override
    public AppointDetDto CreateOrEdit(AppointDetDto input) {
        //声明一个预约明细实体
        AppointDet AppointDet=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(AppointDet);
        //把传输模型返回给前端
        return AppointDet.MapToDto();
    }
    /**
     * 预约明细删除
     */
    @Override
    public void Delete(IdInput input) {
        AppointDet entity = AppointDetMapper.selectById(input.getId());
        AppointDetMapper.deleteById(entity);
    }

    /**
     * 预约明细批量删除
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
