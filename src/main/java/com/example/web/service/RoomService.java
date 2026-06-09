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
 * 房间功能的Service接口的定义清单
 */
public interface RoomService extends IService<Room> {

    /**
     * 房间的分页查询方法接口定义
     */
    public PagedResult<RoomDto> List(RoomPagedInput input) ;
    /**
     * 房间的新增或者修改方法接口定义
     */
    public RoomDto CreateOrEdit(RoomDto input);

     /**
     * 获取房间信息
     */
    public RoomDto Get(RoomPagedInput input);
 	 /**
     * 房间删除
     */
    public void Delete(IdInput input);

    /**
     * 房间批量删除
     */
    public void BatchDelete(IdsInput input);
  
    /**
     * 房间设施信息保存
     */
    public RoomDto EquipmentInfoSave(RoomDto input);

    /**
     * 用户房间查询
     */
    public List<RoomDto> UserList(RoomPagedInput input);

    /**
     * 用户房间查询
     */
    public RoomDto UserGet(RoomPagedInput input);
}
