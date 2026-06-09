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
 * 寄存记录功能的Service接口的定义清单
 */
public interface StorageRecordService extends IService<StorageRecord> {

    /**
     * 寄存记录的分页查询方法接口定义
     */
    public PagedResult<StorageRecordDto> List(StorageRecordPagedInput input) ;
    /**
     * 寄存记录的新增或者修改方法接口定义
     */
    public StorageRecordDto CreateOrEdit(StorageRecordDto input);

     /**
     * 获取寄存记录信息
     */
    public StorageRecordDto Get(StorageRecordPagedInput input);
 	 /**
     * 寄存记录删除
     */
    public void Delete(IdInput input);

    /**
     * 寄存记录批量删除
     */
    public void BatchDelete(IdsInput input);
  
    /**
     * 领取寄存物品
     */
    public void TakeGood(StorageRecordDto input);
}
