package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.WechatCollectionDto;
import com.example.web.dto.query.WechatCollectionPagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.WechatCollection;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.WechatCollectionMapper;
import com.example.web.service.WechatCollectionService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 会话集合功能实现类
 */
@Service
public class WechatCollectionServiceImpl extends ServiceImpl<WechatCollectionMapper, WechatCollection> implements WechatCollectionService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的WechatCollection表mapper对象
     */
    @Autowired
    private WechatCollectionMapper _WechatCollectionMpper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<WechatCollection> BuilderQuery(WechatCollectionPagedInput input) {
        //声明一个支持会话集合查询的(拉姆达)表达式
        LambdaQueryWrapper<WechatCollection> queryWrapper = Wrappers.<WechatCollection>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, WechatCollection::getId, input.getId());


        if (Extension.isNullOrZero(input.getSelfUserId())) {
            queryWrapper.eq(input.getOtherUserId() != null, WechatCollection::getOtherUserId, input.getOtherUserId());
        }

        if (Extension.isNullOrZero(input.getOtherUserId())) {
            queryWrapper.eq(input.getSelfUserId() != null, WechatCollection::getSelfUserId, input.getSelfUserId());
        }


        //如果前端搜索传入查询条件则拼接查询条件
        return queryWrapper;
    }

    /**
     * 处理会话集合对于的外键数据
     */
    private List<WechatCollectionDto> DispatchItem(List<WechatCollectionDto> items) throws InvocationTargetException, IllegalAccessException {

        for (WechatCollectionDto item : items) {
            //查询出关联的创建用户信息



            //查询出关联的AppUser表信息
            AppUser SelfUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getSelfUserId())).stream().findFirst().orElse(new AppUser());
            item.setSelfUserDto(SelfUserEntity.MapToDto());


            //查询出关联的AppUser表信息
            AppUser OtherUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getOtherUserId())).stream().findFirst().orElse(new AppUser());
            item.setOtherUserDto(OtherUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 会话集合分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<WechatCollectionDto> List(WechatCollectionPagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<WechatCollection> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByDesc(WechatCollection::getCreationTime);
        //构建一个分页查询的model
        Page<WechatCollection> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取会话集合数据
        IPage<WechatCollection> pageRecords = _WechatCollectionMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = _WechatCollectionMpper.selectCount(queryWrapper);
        //把WechatCollection实体转换成WechatCollection传输模型
        List<WechatCollectionDto> items = Extension.copyBeanList(pageRecords.getRecords(), WechatCollectionDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个会话集合查询
     */
    @SneakyThrows
    @Override
    public WechatCollectionDto Get(WechatCollectionPagedInput input) {
        if (input.getId() == null) {
            return new WechatCollectionDto();
        }
        PagedResult<WechatCollectionDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new WechatCollectionDto());
    }

    /**
     * 会话集合创建或者修改
     */
    @SneakyThrows
    @Override
    public WechatCollectionDto CreateOrEdit(WechatCollectionDto input) {
        //声明一个会话集合实体
        WechatCollection WechatCollection = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(WechatCollection);
        //把传输模型返回给前端
        return WechatCollection.MapToDto();
    }

    /**
     * 同步聊天记录到列表
     */
    @SneakyThrows
    @Override
    public void SyncWechatCollection(Integer selfUserId, Integer otherUserId, String message) {

        LambdaQueryWrapper<WechatCollection> queryWrapper = Wrappers.<WechatCollection>lambdaQuery().and(
                wrapper -> wrapper.eq(WechatCollection::getSelfUserId, selfUserId).eq(WechatCollection::getOtherUserId, otherUserId)
        ).or(wrapper -> wrapper.eq(WechatCollection::getSelfUserId, otherUserId).eq(WechatCollection::getOtherUserId, selfUserId));
        List<WechatCollection> wechatCollections = _WechatCollectionMpper.selectList(queryWrapper);
        for (WechatCollection wechatCollection : wechatCollections) {
            wechatCollection.setLastTime(LocalDateTime.now());
            wechatCollection.setLastMessage(message);

        }
        _WechatCollectionMpper.insertOrUpdate(wechatCollections);

    }

    /**
     * 添加一个会话保证双方都有
     */
    @SneakyThrows
    @Override
    public void InsertWechatCollection(WechatCollectionDto input) {

        //声明一个会话集合实体

        Long meCount = _WechatCollectionMpper.selectCount(Wrappers.<WechatCollection>lambdaQuery().eq(WechatCollection::getSelfUserId, input.getSelfUserId()).eq(WechatCollection::getOtherUserId, input.getOtherUserId()));
        if (meCount == 0) {
            WechatCollection me = new WechatCollection();
            me.setLastMessage(null);
            me.setLastTime(null);
            me.setSelfUserId(input.getSelfUserId());
            me.setOtherUserId(input.getOtherUserId());

            //调用数据库的增加或者修改方法
            saveOrUpdate(me);
        }
        Long otherCount = _WechatCollectionMpper.selectCount(Wrappers.<WechatCollection>lambdaQuery().eq(WechatCollection::getSelfUserId, input.getOtherUserId()).eq(WechatCollection::getOtherUserId, input.getSelfUserId()));

        if (otherCount == 0) {
            WechatCollection other = new WechatCollection();
            other.setLastMessage(null);
            other.setLastTime(null);
            other.setSelfUserId(input.getOtherUserId());
            other.setOtherUserId(input.getSelfUserId());
            //调用数据库的增加或者修改方法
            saveOrUpdate(other);
        }
    }


    /**
     * 会话集合删除
     */
    @Override
    public void Delete(IdInput input) {
        WechatCollection entity = _WechatCollectionMpper.selectById(input.getId());
        _WechatCollectionMpper.deleteById(entity);
    }

    /**
     * 会话集合批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }


}
