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
 * 设备信息控制器 
 */
@RestController()
@RequestMapping("/EquipmentInfo")
public class EquipmentInfoController {
    @Autowired
    private  EquipmentInfoService EquipmentInfoService;
    @Autowired
    private EquipmentInfoMapper EquipmentInfoMapper;
    /**
     * 设备信息分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<EquipmentInfoDto> List(@RequestBody EquipmentInfoPagedInput input)  {
        return EquipmentInfoService.List(input);
    }
     /**
     * 单个设备信息查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public EquipmentInfoDto Get(@RequestBody EquipmentInfoPagedInput input) {

        return EquipmentInfoService.Get(input);
    }
  
    /**
     * 设备信息创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public EquipmentInfoDto CreateOrEdit(@RequestBody EquipmentInfoDto input) throws Exception {
        return EquipmentInfoService.CreateOrEdit(input);
    }
    /**
     * 设备信息删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        EquipmentInfoService.Delete(input);
    }

    /**
     * 设备信息批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        EquipmentInfoService.BatchDelete(input);
    }
  

 
}
