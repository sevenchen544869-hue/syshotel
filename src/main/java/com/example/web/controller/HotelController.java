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
 * 酒店控制器 
 */
@RestController()
@RequestMapping("/Hotel")
public class HotelController {
    @Autowired
    private  HotelService HotelService;
    @Autowired
    private HotelMapper HotelMapper;
    /**
     * 酒店分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<HotelDto> List(@RequestBody HotelPagedInput input)  {
        return HotelService.List(input);
    }
     /**
     * 单个酒店查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public HotelDto Get(@RequestBody HotelPagedInput input) {

        return HotelService.Get(input);
    }
  
    /**
     * 酒店创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public HotelDto CreateOrEdit(@RequestBody HotelDto input) throws Exception {
        return HotelService.CreateOrEdit(input);
    }
    /**
     * 酒店删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        HotelService.Delete(input);
    }

    /**
     * 酒店批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        HotelService.BatchDelete(input);
    }
  

    /**
     * 热门酒店top5
     */
    @RequestMapping(value = "/HotHotelList", method = RequestMethod.POST)
    public PagedResult<HotelDto> HotHotelList(@RequestBody HotelPagedInput input)
    {
        return HotelService.HotHotelList(input);
    }
}
