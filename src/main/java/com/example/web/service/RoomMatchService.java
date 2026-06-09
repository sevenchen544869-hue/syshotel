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
 * 分配房间功能的Service接口的定义清单
 */
public interface RoomMatchService extends IService<RoomMatch> {

    /**
     * 分配房间的分页查询方法接口定义
     */
    public PagedResult<RoomMatchDto> List(RoomMatchPagedInput input) ;
    /**
     * 分配房间的新增或者修改方法接口定义
     */
    public RoomMatchDto CreateOrEdit(RoomMatchDto input);

     /**
     * 获取分配房间信息
     */
    public RoomMatchDto Get(RoomMatchPagedInput input);
 	 /**
     * 分配房间删除
     */
    public void Delete(IdInput input);

    /**
     * 分配房间批量删除
     */
    public void BatchDelete(IdsInput input);
  
    /**
     * 批量分配房间创建或者修改
     */
    public void BatchCreateOrEdit(List<RoomMatchDto> input);
}
