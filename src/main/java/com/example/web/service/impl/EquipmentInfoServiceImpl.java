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
 * 设备信息功能实现类
 */
@Service
public class EquipmentInfoServiceImpl extends ServiceImpl<EquipmentInfoMapper, EquipmentInfo> implements EquipmentInfoService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的EquipmentInfo表mapper对象
     */
    @Autowired
    private EquipmentInfoMapper EquipmentInfoMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<EquipmentInfo> BuilderQuery(EquipmentInfoPagedInput input) {
       //声明一个支持设备信息查询的(拉姆达)表达式
        LambdaQueryWrapper<EquipmentInfo> queryWrapper = Wrappers.<EquipmentInfo>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, EquipmentInfo::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(EquipmentInfo::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getGroupName())) {
             queryWrapper = queryWrapper.like(EquipmentInfo::getGroupName, input.getGroupName());
       	 }
        if (input.getIsFree() != null) {
            queryWrapper = queryWrapper.eq(EquipmentInfo::getIsFree, input.getIsFree());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(EquipmentInfo::getName,input.getKeyWord()).or()   	 
          	   .like(EquipmentInfo::getGroupName,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理设备信息对于的外键数据
     */
   private List<EquipmentInfoDto> DispatchItem(List<EquipmentInfoDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (EquipmentInfoDto item : items) {       }
       
     return items; 
   }
  
    /**
     * 设备信息分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<EquipmentInfoDto> List(EquipmentInfoPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<EquipmentInfo> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(EquipmentInfo::getCreationTime);
        }

        //构建一个分页查询的model
        Page<EquipmentInfo> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取设备信息数据
        IPage<EquipmentInfo> pageRecords= EquipmentInfoMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= EquipmentInfoMapper.selectCount(queryWrapper);
        //把EquipmentInfo实体转换成EquipmentInfo传输模型
        List<EquipmentInfoDto> items= Extension.copyBeanList(pageRecords.getRecords(),EquipmentInfoDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个设备信息查询
     */
    @SneakyThrows
    @Override
    public EquipmentInfoDto Get(EquipmentInfoPagedInput input) {
       if(input.getId()==null)
        {
         return new EquipmentInfoDto();
        }
      
       PagedResult<EquipmentInfoDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new EquipmentInfoDto()); 
    }

    /**
     *设备信息创建或者修改
     */
    @SneakyThrows
    @Override
    public EquipmentInfoDto CreateOrEdit(EquipmentInfoDto input) {
        //声明一个设备信息实体
        EquipmentInfo EquipmentInfo=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(EquipmentInfo);
        //把传输模型返回给前端
        return EquipmentInfo.MapToDto();
    }
    /**
     * 设备信息删除
     */
    @Override
    public void Delete(IdInput input) {
        EquipmentInfo entity = EquipmentInfoMapper.selectById(input.getId());
        EquipmentInfoMapper.deleteById(entity);
    }

    /**
     * 设备信息批量删除
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
