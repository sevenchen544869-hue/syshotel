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
 * 酒店积分功能的Service接口的定义清单
 */
public interface HotelIntegralService extends IService<HotelIntegral> {

    /**
     * 酒店积分的分页查询方法接口定义
     */
    public PagedResult<HotelIntegralDto> List(HotelIntegralPagedInput input) ;
    /**
     * 酒店积分的新增或者修改方法接口定义
     */
    public HotelIntegralDto CreateOrEdit(HotelIntegralDto input);

     /**
     * 获取酒店积分信息
     */
    public HotelIntegralDto Get(HotelIntegralPagedInput input);
 	 /**
     * 酒店积分删除
     */
    public void Delete(IdInput input);

    /**
     * 酒店积分批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 得到用户在该酒店下的积分 求和积分值
     */
    public double GetUserHotelIntegral(HotelIntegralPagedInput input);

    /**
     * 得到用户所有酒店对应的积分
     */
    public List<Object> GetUserAllHotelIntegral(HotelIntegralPagedInput input);
}
