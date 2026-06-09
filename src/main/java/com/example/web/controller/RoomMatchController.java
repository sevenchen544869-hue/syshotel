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
 * 分配房间控制器 
 */
@RestController()
@RequestMapping("/RoomMatch")
public class RoomMatchController {
    @Autowired
    private  RoomMatchService RoomMatchService;
    @Autowired
    private RoomMatchMapper RoomMatchMapper;
    /**
     * 分配房间分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<RoomMatchDto> List(@RequestBody RoomMatchPagedInput input)  {
        return RoomMatchService.List(input);
    }
     /**
     * 单个分配房间查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public RoomMatchDto Get(@RequestBody RoomMatchPagedInput input) {

        return RoomMatchService.Get(input);
    }
  
    /**
     * 分配房间创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public RoomMatchDto CreateOrEdit(@RequestBody RoomMatchDto input) throws Exception {
        return RoomMatchService.CreateOrEdit(input);
    }
    /**
     * 分配房间删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        RoomMatchService.Delete(input);
    }

    /**
     * 分配房间批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        RoomMatchService.BatchDelete(input);
    }
  
    /**
     * 批量分配房间创建或者修改
     */
    @RequestMapping(value = "/BatchCreateOrEdit", method = RequestMethod.POST)
    public void BatchCreateOrEdit(@RequestBody List<RoomMatchDto> input)
    {
        RoomMatchService.BatchCreateOrEdit(input);
    }
 
}
