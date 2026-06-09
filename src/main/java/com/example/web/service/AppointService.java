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
 * 预约记录功能的Service接口的定义清单
 */
public interface AppointService extends IService<Appoint> {

    /**
     * 预约记录的分页查询方法接口定义
     */
    public PagedResult<AppointDto> List(AppointPagedInput input) ;
    /**
     * 预约记录的新增或者修改方法接口定义
     */
    public AppointDto CreateOrEdit(AppointDto input);

     /**
     * 获取预约记录信息
     */
    public AppointDto Get(AppointPagedInput input);
 	 /**
     * 预约记录删除
     */
    public void Delete(IdInput input);

    /**
     * 预约记录批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 用户预定
     */
    public AppointDto ToOrder(AppointDto input);
  
    /**
     * 用户预订取消
     */
    public void UserCancel(IdInput input);

    /**
     * 酒店处理用户申请取消
     */
    public void HotelDoWithUserCancel(AppointDto input);

    /**
     * 酒店处理预约确认
     */
    public void HotelConfirm(AppointDto input);
    /**
     * 酒店处理预约取消
     */
    public void HotelCancel(AppointDto input);
    /**
     * 用户支付
     */
    public AppointDto Payment(AppointDto input);
    /**
      * 自动取消未付款的订单
     */
    public void AutoCancel();
    /**
     * 退房
     */
    public void CheckOut(AppointDto input);
    /**
     * 评价
     */
    public void Comment(AppointDto input);
    /**
     * 酒店评价
     */
    public PagedResult<AppointDto> CommentList(AppointPagedInput input);
}
