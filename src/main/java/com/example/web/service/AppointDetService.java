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
 * 预约明细功能的Service接口的定义清单
 */
public interface AppointDetService extends IService<AppointDet> {

    /**
     * 预约明细的分页查询方法接口定义
     */
    public PagedResult<AppointDetDto> List(AppointDetPagedInput input) ;
    /**
     * 预约明细的新增或者修改方法接口定义
     */
    public AppointDetDto CreateOrEdit(AppointDetDto input);

     /**
     * 获取预约明细信息
     */
    public AppointDetDto Get(AppointDetPagedInput input);
 	 /**
     * 预约明细删除
     */
    public void Delete(IdInput input);

    /**
     * 预约明细批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
