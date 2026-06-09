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
 * 酒店积分控制器 
 */
@RestController()
@RequestMapping("/HotelIntegral")
public class HotelIntegralController {
    @Autowired
    private  HotelIntegralService HotelIntegralService;
    @Autowired
    private HotelIntegralMapper HotelIntegralMapper;
    /**
     * 酒店积分分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<HotelIntegralDto> List(@RequestBody HotelIntegralPagedInput input)  {
        return HotelIntegralService.List(input);
    }
     /**
     * 单个酒店积分查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public HotelIntegralDto Get(@RequestBody HotelIntegralPagedInput input) {

        return HotelIntegralService.Get(input);
    }
  
    /**
     * 酒店积分创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public HotelIntegralDto CreateOrEdit(@RequestBody HotelIntegralDto input) throws Exception {
        return HotelIntegralService.CreateOrEdit(input);
    }
    /**
     * 酒店积分删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        HotelIntegralService.Delete(input);
    }

    /**
     * 酒店积分批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        HotelIntegralService.BatchDelete(input);
    }
  
    /**
     * 得到用户在该酒店下的积分 求和积分值
     */
    @RequestMapping(value = "/GetUserHotelIntegral", method = RequestMethod.POST)
    public double GetUserHotelIntegral(@RequestBody HotelIntegralPagedInput input)
    {
        return HotelIntegralService.GetUserHotelIntegral(input);
    }

    /**
     * 得到用户所有酒店对应的积分
     */
    @RequestMapping(value = "/GetUserAllHotelIntegral", method = RequestMethod.POST)
    public List<Object> GetUserAllHotelIntegral(@RequestBody HotelIntegralPagedInput input)
    {
        return HotelIntegralService.GetUserAllHotelIntegral(input);
    }
 
}
