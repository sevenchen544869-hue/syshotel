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
 * 留言反馈功能实现类
 */
@Service
public class LeaveFeedbackServiceImpl extends ServiceImpl<LeaveFeedbackMapper, LeaveFeedback> implements LeaveFeedbackService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的LeaveFeedback表mapper对象
     */
    @Autowired
    private LeaveFeedbackMapper LeaveFeedbackMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<LeaveFeedback> BuilderQuery(LeaveFeedbackPagedInput input) {
       //声明一个支持留言反馈查询的(拉姆达)表达式
        LambdaQueryWrapper<LeaveFeedback> queryWrapper = Wrappers.<LeaveFeedback>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, LeaveFeedback::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getTitle())) {
             queryWrapper = queryWrapper.like(LeaveFeedback::getTitle, input.getTitle());
       	 }

        if (input.getDoWithUserId() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedback::getDoWithUserId, input.getDoWithUserId());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedback::getHotelId, input.getHotelId());
       	 }

        if (input.getFeedbackUserId() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedback::getFeedbackUserId, input.getFeedbackUserId());
       	 }
        if (input.getIsDoWith() != null) {
            queryWrapper = queryWrapper.eq(LeaveFeedback::getIsDoWith, input.getIsDoWith());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(LeaveFeedback::getTitle,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理留言反馈对于的外键数据
     */
   private List<LeaveFeedbackDto> DispatchItem(List<LeaveFeedbackDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (LeaveFeedbackDto item : items) {           
          	            
           //查询出关联的AppUser表信息           
            AppUser  FeedbackUserEntity= AppUserMapper.selectById(item.getFeedbackUserId());
            item.setFeedbackUserDto(FeedbackUserEntity!=null?FeedbackUserEntity.MapToDto():new AppUserDto());              
           
          	            
           //查询出关联的AppUser表信息           
            AppUser  DoWithUserEntity= AppUserMapper.selectById(item.getDoWithUserId());
            item.setDoWithUserDto(DoWithUserEntity!=null?DoWithUserEntity.MapToDto():new AppUserDto());              
           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
       }
       
     return items; 
   }
  
    /**
     * 留言反馈分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<LeaveFeedbackDto> List(LeaveFeedbackPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<LeaveFeedback> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(LeaveFeedback::getCreationTime);
        }

        //构建一个分页查询的model
        Page<LeaveFeedback> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取留言反馈数据
        IPage<LeaveFeedback> pageRecords= LeaveFeedbackMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= LeaveFeedbackMapper.selectCount(queryWrapper);
        //把LeaveFeedback实体转换成LeaveFeedback传输模型
        List<LeaveFeedbackDto> items= Extension.copyBeanList(pageRecords.getRecords(),LeaveFeedbackDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个留言反馈查询
     */
    @SneakyThrows
    @Override
    public LeaveFeedbackDto Get(LeaveFeedbackPagedInput input) {
       if(input.getId()==null)
        {
         return new LeaveFeedbackDto();
        }
      
       PagedResult<LeaveFeedbackDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new LeaveFeedbackDto()); 
    }

    /**
     *留言反馈创建或者修改
     */
    @SneakyThrows
    @Override
    public LeaveFeedbackDto CreateOrEdit(LeaveFeedbackDto input) {
        //声明一个留言反馈实体
        LeaveFeedback LeaveFeedback=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(LeaveFeedback);
        //把传输模型返回给前端
        return LeaveFeedback.MapToDto();
    }
    /**
     * 留言反馈删除
     */
    @Override
    public void Delete(IdInput input) {
        LeaveFeedback entity = LeaveFeedbackMapper.selectById(input.getId());
        LeaveFeedbackMapper.deleteById(entity);
    }

    /**
     * 留言反馈批量删除
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
