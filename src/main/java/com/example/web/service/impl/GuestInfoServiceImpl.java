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
 * 旅客信息功能实现类
 */
@Service
public class GuestInfoServiceImpl extends ServiceImpl<GuestInfoMapper, GuestInfo> implements GuestInfoService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的GuestInfo表mapper对象
     */
    @Autowired
    private GuestInfoMapper GuestInfoMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<GuestInfo> BuilderQuery(GuestInfoPagedInput input) {
       //声明一个支持旅客信息查询的(拉姆达)表达式
        LambdaQueryWrapper<GuestInfo> queryWrapper = Wrappers.<GuestInfo>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, GuestInfo::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(GuestInfo::getPhone, input.getPhone());
       	 }
        if (Extension.isNotNullOrEmpty(input.getIdCard())) {
             queryWrapper = queryWrapper.like(GuestInfo::getIdCard, input.getIdCard());
       	 }
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(GuestInfo::getName, input.getName());
       	 }
        if (Extension.isNotNullOrEmpty(input.getSex())) {
             queryWrapper = queryWrapper.like(GuestInfo::getSex, input.getSex());
       	 }

        if (input.getBelongUserId() != null) {
            queryWrapper = queryWrapper.eq(GuestInfo::getBelongUserId, input.getBelongUserId());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(GuestInfo::getPhone,input.getKeyWord()).or()   	 
          	   .like(GuestInfo::getIdCard,input.getKeyWord()).or()   	 
          	   .like(GuestInfo::getName,input.getKeyWord()).or()   	 
          	   .like(GuestInfo::getSex,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理旅客信息对于的外键数据
     */
   private List<GuestInfoDto> DispatchItem(List<GuestInfoDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (GuestInfoDto item : items) {           
          	            
           //查询出关联的AppUser表信息           
            AppUser  BelongUserEntity= AppUserMapper.selectById(item.getBelongUserId());
            item.setBelongUserDto(BelongUserEntity!=null?BelongUserEntity.MapToDto():new AppUserDto());              
       }
       
     return items; 
   }
  
    /**
     * 旅客信息分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<GuestInfoDto> List(GuestInfoPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<GuestInfo> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(GuestInfo::getCreationTime);
        }

        //构建一个分页查询的model
        Page<GuestInfo> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取旅客信息数据
        IPage<GuestInfo> pageRecords= GuestInfoMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= GuestInfoMapper.selectCount(queryWrapper);
        //把GuestInfo实体转换成GuestInfo传输模型
        List<GuestInfoDto> items= Extension.copyBeanList(pageRecords.getRecords(),GuestInfoDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个旅客信息查询
     */
    @SneakyThrows
    @Override
    public GuestInfoDto Get(GuestInfoPagedInput input) {
       if(input.getId()==null)
        {
         return new GuestInfoDto();
        }
      
       PagedResult<GuestInfoDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new GuestInfoDto()); 
    }

    /**
     *旅客信息创建或者修改
     */
    @SneakyThrows
    @Override
    public GuestInfoDto CreateOrEdit(GuestInfoDto input) {

        //针对同一个旅客信息不能重复
        Long count = GuestInfoMapper
            .selectCount(Wrappers.<GuestInfo>lambdaQuery()
            .eq(GuestInfo::getIdCard, input.getIdCard())
            .eq(GuestInfo::getBelongUserId, input.getBelongUserId())
            .ne(input.getId() != null && input.getId() != 0, GuestInfo::getId, input.getId()));
        if (count > 0) {
            throw new CustomException("请勿添加相同的旅客信息");
        }


        //声明一个旅客信息实体
        GuestInfo GuestInfo=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(GuestInfo);
        //把传输模型返回给前端
        return GuestInfo.MapToDto();
    }
    /**
     * 旅客信息删除
     */
    @Override
    public void Delete(IdInput input) {
        GuestInfo entity = GuestInfoMapper.selectById(input.getId());
        GuestInfoMapper.deleteById(entity);
    }

    /**
     * 旅客信息批量删除
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
