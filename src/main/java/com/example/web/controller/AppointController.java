package com.example.web.controller;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import lombok.SneakyThrows;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 预约记录控制器 
 */
@RestController()
@RequestMapping("/Appoint")
public class AppointController {
    @Autowired
    private  AppointService AppointService;
    @Autowired
    private AppointMapper AppointMapper;
    /**
     * 预约记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<AppointDto> List(@RequestBody AppointPagedInput input)  {
        return AppointService.List(input);
    }
     /**
     * 单个预约记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public AppointDto Get(@RequestBody AppointPagedInput input) {
        System.out.println("666");
        System.out.println("888");
        System.out.println("666");
        System.out.println("888");
        return AppointService.Get(input);
    }
  
    /**
     * 预约记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public AppointDto CreateOrEdit(@RequestBody AppointDto input) throws Exception {
        return AppointService.CreateOrEdit(input);
    }
    /**
     * 预约记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        AppointService.Delete(input);
    }

    /**
     * 预约记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        AppointService.BatchDelete(input);
    }
  

    /**
     * 用户预定
     */
    @RequestMapping(value = "/ToOrder", method = RequestMethod.POST)
    public AppointDto ToOrder(@RequestBody AppointDto input)
    {
        return AppointService.ToOrder(input);
    }

    /**
     * 用户预订取消
     */
    @RequestMapping(value = "/UserCancel", method = RequestMethod.POST)
    public void UserCancel(@RequestBody IdInput input)
    {
        AppointService.UserCancel(input);
    }

    /**
     * 酒店处理预约取消
     */
    @RequestMapping(value = "/HotelDoWithUserCancel", method = RequestMethod.POST)
    public void HotelCancel(@RequestBody AppointDto input)
    {
        AppointService.HotelDoWithUserCancel(input);
    }

    /**
     * 酒店处理预约确认
     */
    @RequestMapping(value = "/HotelConfirm", method = RequestMethod.POST)
    public void HotelConfirm(@RequestBody AppointDto input)
    {
        AppointService.HotelConfirm(input);
    }
    /**
     * 酒店处理预约取消
     */
    @RequestMapping(value = "/HotelCancel", method = RequestMethod.POST)
    public void HotelCancelConfirm(@RequestBody AppointDto input)
    {
        AppointService.HotelCancel(input);
    }
    /**
     * 用户支付
     */
    @RequestMapping(value = "/Payment", method = RequestMethod.POST)
    public AppointDto Payment(@RequestBody AppointDto input)
    {
        return AppointService.Payment(input);
    }
    /**
     * 退房
     */
    @RequestMapping(value = "/CheckOut", method = RequestMethod.POST)
    public void CheckOut(@RequestBody AppointDto input)
    {
        AppointService.CheckOut(input);
    }
    /**
     * 评价
     */
    @RequestMapping(value = "/Comment", method = RequestMethod.POST)
    public void Comment(@RequestBody AppointDto input)
    {
        AppointService.Comment(input);
    }

    /**
     * 酒店评价
     */
    @RequestMapping(value = "/CommentList", method = RequestMethod.POST)
    public PagedResult<AppointDto> CommentList(@RequestBody AppointPagedInput input)
    {
        return AppointService.CommentList(input);
    }
}
