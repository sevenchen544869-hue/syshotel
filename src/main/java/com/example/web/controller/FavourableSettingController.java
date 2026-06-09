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
 * 优惠设置控制器 
 */
@RestController()
@RequestMapping("/FavourableSetting")
public class FavourableSettingController {
    @Autowired
    private  FavourableSettingService FavourableSettingService;
    @Autowired
    private FavourableSettingMapper FavourableSettingMapper;
    /**
     * 优惠设置分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<FavourableSettingDto> List(@RequestBody FavourableSettingPagedInput input)  {
        return FavourableSettingService.List(input);
    }
     /**
     * 单个优惠设置查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public FavourableSettingDto Get(@RequestBody FavourableSettingPagedInput input) {

        return FavourableSettingService.Get(input);
    }
  
    /**
     * 优惠设置创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public FavourableSettingDto CreateOrEdit(@RequestBody FavourableSettingDto input) throws Exception {
        return FavourableSettingService.CreateOrEdit(input);
    }
    /**
     * 优惠设置删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        FavourableSettingService.Delete(input);
    }

    /**
     * 优惠设置批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        FavourableSettingService.BatchDelete(input);
    }
  

 
}
