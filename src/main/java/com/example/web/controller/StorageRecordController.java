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
 * 寄存记录控制器 
 */
@RestController()
@RequestMapping("/StorageRecord")
public class StorageRecordController {
    @Autowired
    private  StorageRecordService StorageRecordService;
    @Autowired
    private StorageRecordMapper StorageRecordMapper;
    /**
     * 寄存记录分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<StorageRecordDto> List(@RequestBody StorageRecordPagedInput input)  {
        return StorageRecordService.List(input);
    }
     /**
     * 单个寄存记录查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public StorageRecordDto Get(@RequestBody StorageRecordPagedInput input) {

        return StorageRecordService.Get(input);
    }
  
    /**
     * 寄存记录创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public StorageRecordDto CreateOrEdit(@RequestBody StorageRecordDto input) throws Exception {
        return StorageRecordService.CreateOrEdit(input);
    }
    /**
     * 寄存记录删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        StorageRecordService.Delete(input);
    }

    /**
     * 寄存记录批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        StorageRecordService.BatchDelete(input);
    }
  
    /**
     * 领取寄存物品
     */
    @RequestMapping(value = "/TakeGood", method = RequestMethod.POST)
    public void TakeGood(@RequestBody StorageRecordDto input)
    {
        StorageRecordService.TakeGood(input);
    }
 
}
