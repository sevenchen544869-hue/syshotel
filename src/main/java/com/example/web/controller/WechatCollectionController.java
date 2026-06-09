package com.example.web.controller;

import com.example.web.dto.WechatCollectionDto;
import com.example.web.dto.query.WechatCollectionPagedInput;
import com.example.web.mapper.WechatCollectionMapper;
import com.example.web.service.WechatCollectionService;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会话集合控制器
 */
@RestController()
@RequestMapping("/WechatCollection")
public class WechatCollectionController {
    @Autowired()
    private WechatCollectionService _WechatCollectionService;
    @Autowired()
    private WechatCollectionMapper _WechatCollectionMapper;

    /**
     * 会话集合分页查询
     */
    @RequestMapping(value = "/List", method = RequestMethod.POST)
    @SneakyThrows
    public PagedResult<WechatCollectionDto> List(@RequestBody WechatCollectionPagedInput input) {
        return _WechatCollectionService.List(input);
    }

    /**
     * 单个会话集合查询接口
     */
    @RequestMapping(value = "/Get", method = RequestMethod.POST)
    @SneakyThrows
    public WechatCollectionDto Get(@RequestBody WechatCollectionPagedInput input) {

        return _WechatCollectionService.Get(input);
    }

    /**
     * 会话集合创建或则修改
     */
    @RequestMapping(value = "/CreateOrEdit", method = RequestMethod.POST)
    public WechatCollectionDto CreateOrEdit(@RequestBody WechatCollectionDto input) throws Exception {
        return _WechatCollectionService.CreateOrEdit(input);
    }

    /**
     * 会话集合删除
     */
    @RequestMapping(value = "/Delete", method = RequestMethod.POST)
    public void Delete(@RequestBody IdInput input) {
        _WechatCollectionService.Delete(input);
    }

    /**
     * 会话集合批量删除
     */
    @RequestMapping(value = "/BatchDelete", method = RequestMethod.POST)
    public void BatchDelete(@RequestBody IdsInput input) {
        _WechatCollectionService.BatchDelete(input);
    }


    /**
     * 添加会话到双方列表
     */
    @SneakyThrows
    @RequestMapping(value = "/InsertWechatCollection", method = RequestMethod.POST)
    public void InsertWechatCollection(@RequestBody WechatCollectionDto input) {
        _WechatCollectionService.InsertWechatCollection(input);

    }
}
