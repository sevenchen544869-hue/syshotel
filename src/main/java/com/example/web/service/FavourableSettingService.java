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
 * 优惠设置功能的Service接口的定义清单
 */
public interface FavourableSettingService extends IService<FavourableSetting> {

    /**
     * 优惠设置的分页查询方法接口定义
     */
    public PagedResult<FavourableSettingDto> List(FavourableSettingPagedInput input) ;
    /**
     * 优惠设置的新增或者修改方法接口定义
     */
    public FavourableSettingDto CreateOrEdit(FavourableSettingDto input);

     /**
     * 获取优惠设置信息
     */
    public FavourableSettingDto Get(FavourableSettingPagedInput input);
 	 /**
     * 优惠设置删除
     */
    public void Delete(IdInput input);

    /**
     * 优惠设置批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
