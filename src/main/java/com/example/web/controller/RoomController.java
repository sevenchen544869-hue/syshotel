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
 * 房间控制器 
 */
@RestController()
@RequestMapping("/Room")
public class RoomController {
    @Autowired
    private  RoomService RoomService;
    @Autowired
    private RoomMapper RoomMapper;
    /**
     * 房间分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<RoomDto> List(@RequestBody RoomPagedInput input)  {
        return RoomService.List(input);
    }
     /**
     * 单个房间查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public RoomDto Get(@RequestBody RoomPagedInput input) {

        return RoomService.Get(input);
    }
  
    /**
     * 房间创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public RoomDto CreateOrEdit(@RequestBody RoomDto input) throws Exception {
        return RoomService.CreateOrEdit(input);
    }
    /**
     * 房间删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        RoomService.Delete(input);
    }

    /**
     * 房间批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        RoomService.BatchDelete(input);
    }
  
    /**
     * 房间设施信息保存
     */
    @RequestMapping(value = "/EquipmentInfoSave", method = RequestMethod.POST)
    public RoomDto EquipmentInfoSave(@RequestBody RoomDto input)
    {
        if (input == null || input.getId() == null || input.getId() == 0) {
            throw new CustomException("缺少房间Id");
        }
        return RoomService.EquipmentInfoSave(input);
    }
 
    /**
     * 用户房间查询
     */
    @RequestMapping(value = "/UserList", method = RequestMethod.POST)
    public List<RoomDto> UserList(@RequestBody RoomPagedInput input)
    {
        return RoomService.UserList(input);
    }

    /**
     * 用户房间查询
     */
    @RequestMapping(value = "/UserGet", method = RequestMethod.POST)
    public RoomDto UserGet(@RequestBody RoomPagedInput input)
    {
        return RoomService.UserGet(input);
    }
}
