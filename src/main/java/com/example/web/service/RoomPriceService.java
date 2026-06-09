package com.example.web.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.tools.dto.*;
import com.example.web.enums.*;
import java.lang.reflect.InvocationTargetException;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import jakarta.servlet.http.HttpServletResponse;
/**
 * 房间价格功能的Service接口的定义清单
 */
public interface RoomPriceService extends IService<RoomPrice> {

    /**
     * 房间价格的分页查询方法接口定义
     */
    public PagedResult<RoomPriceDto> List(RoomPricePagedInput input) ;
    /**
     * 房间价格的新增或者修改方法接口定义
     */
    public RoomPriceDto CreateOrEdit(RoomPriceDto input);

     /**
     * 获取房间价格信息
     */
    public RoomPriceDto Get(RoomPricePagedInput input);
 	 /**
     * 房间价格删除
     */
    public void Delete(IdInput input);

    /**
     * 房间价格批量删除
     */
    public void BatchDelete(IdsInput input);
  

}
