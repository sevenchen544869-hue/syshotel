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
 * 留言反馈控制器 
 */
@RestController()
@RequestMapping("/LeaveFeedback")
public class LeaveFeedbackController {
    @Autowired
    private  LeaveFeedbackService LeaveFeedbackService;
    @Autowired
    private LeaveFeedbackMapper LeaveFeedbackMapper;
    /**
     * 留言反馈分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<LeaveFeedbackDto> List(@RequestBody LeaveFeedbackPagedInput input)  {
        return LeaveFeedbackService.List(input);
    }
     /**
     * 单个留言反馈查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public LeaveFeedbackDto Get(@RequestBody LeaveFeedbackPagedInput input) {

        return LeaveFeedbackService.Get(input);
    }
  
    /**
     * 留言反馈创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public LeaveFeedbackDto CreateOrEdit(@RequestBody LeaveFeedbackDto input) throws Exception {
        return LeaveFeedbackService.CreateOrEdit(input);
    }
    /**
     * 留言反馈删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        LeaveFeedbackService.Delete(input);
    }

    /**
     * 留言反馈批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        LeaveFeedbackService.BatchDelete(input);
    }
  

 
}
