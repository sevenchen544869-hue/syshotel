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
 * 系统通知功能实现类
 */
@Service
public class SysNoticeServiceImpl extends ServiceImpl<SysNoticeMapper, SysNotice> implements SysNoticeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的SysNotice表mapper对象
     */
    @Autowired
    private SysNoticeMapper SysNoticeMapper;

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<SysNotice> BuilderQuery(SysNoticePagedInput input) {
       //声明一个支持系统通知查询的(拉姆达)表达式
        LambdaQueryWrapper<SysNotice> queryWrapper = Wrappers.<SysNotice>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, SysNotice::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
             queryWrapper = queryWrapper.like(SysNotice::getTitle, input.getTitle());
       	 }
        if (input.getIsPutaway() != null) {
            queryWrapper = queryWrapper.eq(SysNotice::getIsPutaway, input.getIsPutaway());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(SysNotice::getTitle,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理系统通知对于的外键数据
     */
   private List<SysNoticeDto> DispatchItem(List<SysNoticeDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (SysNoticeDto item : items) {       }
       
     return items; 
   }
  
    /**
     * 系统通知分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<SysNoticeDto> List(SysNoticePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<SysNotice> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(SysNotice::getCreationTime);
        }

        //构建一个分页查询的model
        Page<SysNotice> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取系统通知数据
        IPage<SysNotice> pageRecords= SysNoticeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= SysNoticeMapper.selectCount(queryWrapper);
        //把SysNotice实体转换成SysNotice传输模型
        List<SysNoticeDto> items= Extension.copyBeanList(pageRecords.getRecords(),SysNoticeDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个系统通知查询
     */
    @SneakyThrows
    @Override
    public SysNoticeDto Get(SysNoticePagedInput input) {
       if(input.getId()==null)
        {
         return new SysNoticeDto();
        }
      
       PagedResult<SysNoticeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new SysNoticeDto()); 
    }

    /**
     *系统通知创建或者修改
     */
    @SneakyThrows
    @Override
    public SysNoticeDto CreateOrEdit(SysNoticeDto input) {
        //声明一个系统通知实体
        SysNotice SysNotice=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(SysNotice);
        //把传输模型返回给前端
        return SysNotice.MapToDto();
    }
    /**
     * 系统通知删除
     */
    @Override
    public void Delete(IdInput input) {
        SysNotice entity = SysNoticeMapper.selectById(input.getId());
        SysNoticeMapper.deleteById(entity);
    }

    /**
     * 系统通知批量删除
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
