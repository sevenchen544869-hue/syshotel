package com.example.web.controller;

import com.example.web.dto.WechatMessageDto;
import com.example.web.dto.query.WechatMessagePagedInput;
import com.example.web.mapper.WechatMessageMapper;
import com.example.web.service.WechatMessageService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.ResponseData;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会话消息控制器
 */
@RestController()
@RequestMapping("/WechatMessage")
public class WechatMessageController {
    @Autowired()
    private  WechatMessageService _WechatMessageService;
    @Autowired()
    private WechatMessageMapper _WechatMessageMapper;
    /**
     * 会话消息分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<WechatMessageDto> List(@RequestBody WechatMessagePagedInput input)  {
        return _WechatMessageService.List(input);
    }
     /**
     * 单个会话消息查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public WechatMessageDto Get(@RequestBody WechatMessagePagedInput input) {

        return _WechatMessageService.Get(input);
    }
  
    /**
     * 会话消息创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public WechatMessageDto CreateOrEdit(@RequestBody WechatMessageDto input) throws Exception {
        return _WechatMessageService.CreateOrEdit(input);
    }
    /**
     * 会话消息删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input)
    {
        _WechatMessageService.Delete(input);
    }

    /**
     * 会话消息批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input)
    {
        _WechatMessageService.BatchDelete(input);
    }

  /**
     * 得到管理员id
     */
    @RequestMapping(value = "/GetAdminId", method = RequestMethod.POST)
    public ResponseData<Integer> GetAdminId() {
        return ResponseData.GetResponseDataInstance(_WechatMessageService.GetAdminId(), "成功", true);
    }

    /**
     * 检测是否可以发消息
     */
    @RequestMapping(value = "/CheckCanSendMessage", method = RequestMethod.POST)
    public void CheckCanSendMessage(@RequestBody WechatMessageDto input) {
     
        _WechatMessageService.CheckCanSendMessage(input);
    }
 
}
