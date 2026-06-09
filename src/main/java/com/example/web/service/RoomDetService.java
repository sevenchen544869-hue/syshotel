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
 * 房间明细功能的Service接口的定义清单
 */
public interface RoomDetService extends IService<RoomDet> {

    /**
     * 房间明细的分页查询方法接口定义
     */
    public PagedResult<RoomDetDto> List(RoomDetPagedInput input) ;
    /**
     * 房间明细的新增或者修改方法接口定义
     */
    public RoomDetDto CreateOrEdit(RoomDetDto input);

     /**
     * 获取房间明细信息
     */
    public RoomDetDto Get(RoomDetPagedInput input);
 	 /**
     * 房间明细删除
     */
    public void Delete(IdInput input);

    /**
     * 房间明细批量删除
     */
    public void BatchDelete(IdsInput input);

    /**
     * 查询出酒店所有的房间信息
     */
    public List<RoomViewDateDto> GetAllRoomView(AllRoomViewQueryInput input);

}
