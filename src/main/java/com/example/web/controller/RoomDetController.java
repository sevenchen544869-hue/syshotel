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
 * 房间明细控制器 
 */
@RestController()
@RequestMapping("/RoomDet")
public class RoomDetController {
    @Autowired
    private  RoomDetService RoomDetService;
    @Autowired
    private RoomDetMapper RoomDetMapper;
    /**
     * 房间明细分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<RoomDetDto> List(@RequestBody RoomDetPagedInput input)  {
        return RoomDetService.List(input);
    }
     /**
     * 单个房间明细查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public RoomDetDto Get(@RequestBody RoomDetPagedInput input) {

        return RoomDetService.Get(input);
    }
  
    /**
     * 房间明细创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public RoomDetDto CreateOrEdit(@RequestBody RoomDetDto input) throws Exception {
        return RoomDetService.CreateOrEdit(input);
    }
    /**
     * 房间明细删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        RoomDetService.Delete(input);
    }

    /**
     * 房间明细批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        RoomDetService.BatchDelete(input);
    }
  
    /**
     * 查询出酒店所有的房间信息
     */
    @RequestMapping(value = "/GetAllRoomView", method = RequestMethod.POST)
    public List<RoomViewDateDto> GetAllRoomView(@RequestBody AllRoomViewQueryInput input)
    {
        return RoomDetService.GetAllRoomView(input);
    }
 
}
