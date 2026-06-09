package com.example.web.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.web.dto.WechatMessageDto;
import com.example.web.dto.query.WechatMessagePagedInput;
import com.example.web.entity.WechatMessage;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;

/**
 * 会话消息功能的Service接口的定义清单
 */
public interface WechatMessageService extends IService<WechatMessage> {

    /**
     * 会话消息的分页查询方法接口定义
     */
    public PagedResult<WechatMessageDto> List(WechatMessagePagedInput input) ;
    /**
     * 会话消息的新增或者修改方法接口定义
     */
    public WechatMessageDto CreateOrEdit(WechatMessageDto input);

     /**
     * 获取会话消息信息
     */
    public WechatMessageDto Get(WechatMessagePagedInput input);
 	 /**
     * 会话消息删除
     */
    public void Delete(IdInput input);

    /**
     * 会话消息批量删除
     */
    public void BatchDelete(IdsInput input);

	
    /**
     * 得到管理员id
     */
    public Integer GetAdminId();

    /**
     * 检测是否可以发消息
     */
    public void CheckCanSendMessage(WechatMessageDto input);

}
