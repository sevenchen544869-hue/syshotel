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
 * 封面功能实现类
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Banner表mapper对象
     */
    @Autowired
    private BannerMapper BannerMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Banner> BuilderQuery(BannerPagedInput input) {
       //声明一个支持封面查询的(拉姆达)表达式
        LambdaQueryWrapper<Banner> queryWrapper = Wrappers.<Banner>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Banner::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
             queryWrapper = queryWrapper.like(Banner::getTitle, input.getTitle());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPath())) {
             queryWrapper = queryWrapper.like(Banner::getPath, input.getPath());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(Banner::getTitle,input.getKeyWord()).or()   	 
          	   .like(Banner::getPath,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理封面对于的外键数据
     */
   private List<BannerDto> DispatchItem(List<BannerDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (BannerDto item : items) {       }
       
     return items; 
   }
  
    /**
     * 封面分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<BannerDto> List(BannerPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Banner> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Banner::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Banner> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取封面数据
        IPage<Banner> pageRecords= BannerMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= BannerMapper.selectCount(queryWrapper);
        //把Banner实体转换成Banner传输模型
        List<BannerDto> items= Extension.copyBeanList(pageRecords.getRecords(),BannerDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个封面查询
     */
    @SneakyThrows
    @Override
    public BannerDto Get(BannerPagedInput input) {
       if(input.getId()==null)
        {
         return new BannerDto();
        }
      
       PagedResult<BannerDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new BannerDto()); 
    }

    /**
     *封面创建或者修改
     */
    @SneakyThrows
    @Override
    public BannerDto CreateOrEdit(BannerDto input) {
        //声明一个封面实体
        Banner Banner=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Banner);
        //把传输模型返回给前端
        return Banner.MapToDto();
    }
    /**
     * 封面删除
     */
    @Override
    public void Delete(IdInput input) {
        Banner entity = BannerMapper.selectById(input.getId());
        BannerMapper.deleteById(entity);
    }

    /**
     * 封面批量删除
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
