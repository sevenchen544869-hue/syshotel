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
 * 旅客信息功能的Service接口的定义清单
 */
public interface GuestInfoService extends IService<GuestInfo> {

    /**
     * 旅客信息的分页查询方法接口定义
     */
    public PagedResult<GuestInfoDto> List(GuestInfoPagedInput input) ;
    /**
     * 旅客信息的新增或者修改方法接口定义
     */
    public GuestInfoDto CreateOrEdit(GuestInfoDto input);

     /**
     * 获取旅客信息信息
     */
    public GuestInfoDto Get(GuestInfoPagedInput input);
 	 /**
     * 旅客信息删除
     */
    public void Delete(IdInput input);

    /**
     * 旅客信息批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
