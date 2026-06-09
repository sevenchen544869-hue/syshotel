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
 * 员工功能实现类
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Employee表mapper对象
     */
    @Autowired
    private EmployeeMapper EmployeeMapper;
    @Autowired
    private HotelMapper  HotelMapper;                        

  
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Employee> BuilderQuery(EmployeePagedInput input) {
       //声明一个支持员工查询的(拉姆达)表达式
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.<Employee>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Employee::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getAddress())) {
             queryWrapper = queryWrapper.like(Employee::getAddress, input.getAddress());
       	 }
        if (Extension.isNotNullOrEmpty(input.getNo())) {
             queryWrapper = queryWrapper.like(Employee::getNo, input.getNo());
       	 }
        if (Extension.isNotNullOrEmpty(input.getSex())) {
             queryWrapper = queryWrapper.like(Employee::getSex, input.getSex());
       	 }
        if (Extension.isNotNullOrEmpty(input.getSalary())) {
             queryWrapper = queryWrapper.like(Employee::getSalary, input.getSalary());
       	 }
        if (Extension.isNotNullOrEmpty(input.getIdCard())) {
             queryWrapper = queryWrapper.like(Employee::getIdCard, input.getIdCard());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPhone())) {
             queryWrapper = queryWrapper.like(Employee::getPhone, input.getPhone());
       	 }
        if (Extension.isNotNullOrEmpty(input.getName())) {
             queryWrapper = queryWrapper.like(Employee::getName, input.getName());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(Employee::getHotelId, input.getHotelId());
       	 }

        if (input.getUserId() != null) {
            queryWrapper = queryWrapper.eq(Employee::getUserId, input.getUserId());
       	 }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(Employee::getAddress,input.getKeyWord()).or()   	 
          	   .like(Employee::getNo,input.getKeyWord()).or()   	 
          	   .like(Employee::getSex,input.getKeyWord()).or()   	 
          	   .like(Employee::getSalary,input.getKeyWord()).or()   	 
          	   .like(Employee::getIdCard,input.getKeyWord()).or()   	 
          	   .like(Employee::getPhone,input.getKeyWord()).or()   	 
          	   .like(Employee::getName,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理员工对于的外键数据
     */
   private List<EmployeeDto> DispatchItem(List<EmployeeDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (EmployeeDto item : items) {           
          	            
           //查询出关联的AppUser表信息           
            AppUser  UserEntity= AppUserMapper.selectById(item.getUserId());
            item.setUserDto(UserEntity!=null?UserEntity.MapToDto():new AppUserDto());              
           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());              
       }
       
     return items; 
   }
  
    /**
     * 员工分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<EmployeeDto> List(EmployeePagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Employee> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Employee::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Employee> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取员工数据
        IPage<Employee> pageRecords= EmployeeMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= EmployeeMapper.selectCount(queryWrapper);
        //把Employee实体转换成Employee传输模型
        List<EmployeeDto> items= Extension.copyBeanList(pageRecords.getRecords(),EmployeeDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个员工查询
     */
    @SneakyThrows
    @Override
    public EmployeeDto Get(EmployeePagedInput input) {
       if(input.getId()==null)
        {
         return new EmployeeDto();
        }
      
       PagedResult<EmployeeDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new EmployeeDto()); 
    }

    /**
     *员工创建或者修改
     */
    @SneakyThrows
    @Override
    public EmployeeDto CreateOrEdit(EmployeeDto input) {
        if (!Extension.isNotNullOrEmpty(input.getNo())) {
            input.setNo(GenerateEmployeeNo(input.getHotelId()));
        }
        //声明一个员工实体
        Employee Employee=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Employee);
        //把传输模型返回给前端
        return Employee.MapToDto();
    }

    /**
     * 员工编号生成规则: E + 酒店ID + 0001递增
     */
    private String GenerateEmployeeNo(Integer hotelId) {
        if (hotelId == null) {
            throw new CustomException("酒店ID不能为空");
        }
        String prefix = "EMP" + hotelId;
        LambdaQueryWrapper<Employee> queryWrapper = Wrappers.<Employee>lambdaQuery()
                .eq(Employee::getHotelId, hotelId)
                .likeRight(Employee::getNo, prefix)
                .orderByDesc(Employee::getNo)
                .last("limit 1");
        List<Employee> employees = EmployeeMapper.selectList(queryWrapper);
        int nextNumber = 1;
        if (!employees.isEmpty() && Extension.isNotNullOrEmpty(employees.get(0).getNo())) {
            String lastNo = employees.get(0).getNo();
            String numberPart = lastNo.substring(prefix.length());
            try {
                nextNumber = Integer.parseInt(numberPart) + 1;
            } catch (NumberFormatException ex) {
                nextNumber = 1;
            }
        }
        return prefix + new DecimalFormat("0000").format(nextNumber);
    }
    /**
     * 员工删除
     */
    @Override
    public void Delete(IdInput input) {
        Employee entity = EmployeeMapper.selectById(input.getId());
        EmployeeMapper.deleteById(entity);
    }

    /**
     * 员工批量删除
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
