package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 员工功能的Service接口的定义清单
 */
public interface EmployeeService extends IService<Employee> {

    /**
     * 员工的分页查询方法接口定义
     */
    public PagedResult<EmployeeDto> List(EmployeePagedInput input) ;
    /**
     * 员工的新增或者修改方法接口定义
     */
    public EmployeeDto CreateOrEdit(EmployeeDto input);

     /**
     * 获取员工信息
     */
    public EmployeeDto Get(EmployeePagedInput input);
 	 /**
     * 员工删除
     */
    public void Delete(IdInput input);

    /**
     * 员工批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
