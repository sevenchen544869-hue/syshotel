package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.dto.WechatMessageDto;
import com.example.web.dto.query.WechatMessagePagedInput;
import com.example.web.entity.AppUser;
import com.example.web.entity.WechatMessage;
import com.example.web.enums.RoleTypeEnum;
import com.example.web.mapper.AppUserMapper;
import com.example.web.mapper.WechatMessageMapper;
import com.example.web.service.WechatMessageService;
import com.example.web.tools.Extension;
import com.example.web.tools.dto.IdInput;
import com.example.web.tools.dto.IdsInput;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.exception.CustomException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
/**
 * 会话消息功能实现类
 */
@Service
public class WechatMessageServiceImpl extends ServiceImpl<WechatMessageMapper, WechatMessage> implements WechatMessageService {

    /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper _AppUserMapper;
    /**
     * 操作数据库的WechatMessage表mapper对象
     */
    @Autowired
    private WechatMessageMapper _WechatMessageMpper;


    /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<WechatMessage> BuilderQuery(WechatMessagePagedInput input) {
        //声明一个支持会话消息查询的(拉姆达)表达式
        LambdaQueryWrapper<WechatMessage> queryWrapper = Wrappers.<WechatMessage>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, WechatMessage::getId, input.getId());



        //如果前端搜索传入查询条件则拼接查询条件
        if (!Extension.isNullOrZero(input.getSelfUserId()) && !Extension.isNullOrZero(input.getOtherUserId())) {

            //查询我们2者之间的所有聊天记录
            queryWrapper.and(wrapper->wrapper.eq( WechatMessage::getOtherUserId, input.getOtherUserId())
                    .eq( WechatMessage::getSelfUserId, input.getSelfUserId())
                    )
                    .or(wrapper->wrapper.eq( WechatMessage::getSelfUserId, input.getOtherUserId())
                    .eq( WechatMessage::getOtherUserId, input.getSelfUserId()));

        }



        return queryWrapper;
    }

    /**
     * 处理会话消息对于的外键数据
     */
    private List<WechatMessageDto> DispatchItem(List<WechatMessageDto> items) throws InvocationTargetException, IllegalAccessException {

        for (WechatMessageDto item : items) {


            //查询出关联的AppUser表信息
            AppUser OtherUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getOtherUserId())).stream().findFirst().orElse(new AppUser());
            item.setOtherUserDto(OtherUserEntity.MapToDto());


            //查询出关联的AppUser表信息
            AppUser SelfUserEntity = _AppUserMapper.selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getId, item.getSelfUserId())).stream().findFirst().orElse(new AppUser());
            item.setSelfUserDto(SelfUserEntity.MapToDto());
        }
        return items;
    }

    /**
     * 会话消息分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<WechatMessageDto> List(WechatMessagePagedInput input) {
        //构建where条件+排序
        LambdaQueryWrapper<WechatMessage> queryWrapper = BuilderQuery(input);

        //按创建时间从大到小排序 最新的显示在最前面
        queryWrapper = queryWrapper.orderByAsc(WechatMessage::getCreationTime);
        //构建一个分页查询的model
        Page<WechatMessage> page = new Page<>(input.getPage(), input.getLimit());
        //从数据库进行分页查询获取会话消息数据
        IPage<WechatMessage> pageRecords = _WechatMessageMpper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount = _WechatMessageMpper.selectCount(queryWrapper);
        //把WechatMessage实体转换成WechatMessage传输模型
        List<WechatMessageDto> items = Extension.copyBeanList(pageRecords.getRecords(), WechatMessageDto.class);
        //计算表的数据
        items = DispatchItem(items);


        //返回一个分页结构给前端
        return PagedResult.GetInstance(items, totalCount);

    }

    /**
     * 单个会话消息查询
     */
    @SneakyThrows
    @Override
    public WechatMessageDto Get(WechatMessagePagedInput input) {
        if (input.getId() == null) {
            return new WechatMessageDto();
        }
        PagedResult<WechatMessageDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new WechatMessageDto());
    }

    /**
     * 会话消息创建或者修改
     */
    @SneakyThrows
    @Override
    public WechatMessageDto CreateOrEdit(WechatMessageDto input) {
        //声明一个会话消息实体
        WechatMessage WechatMessage = input.MapToEntity();
        //调用数据库的增加或者修改方法
        saveOrUpdate(WechatMessage);
        //把传输模型返回给前端
        return WechatMessage.MapToDto();
    }

    /**
     * 会话消息删除
     */
    @Override
    public void Delete(IdInput input) {
        WechatMessage entity = _WechatMessageMpper.selectById(input.getId());
        _WechatMessageMpper.deleteById(entity);
    }

    /**
     * 会话消息批量删除
     */
    @Override
    public void BatchDelete(IdsInput input) {
        for (Integer id : input.getIds()) {
            IdInput idInput = new IdInput();
            idInput.setId(id);
            Delete(idInput);
        }
    }
    /**
     * 得到管理员会话消息
     */
    @Override
    public Integer GetAdminId() {
        var rs = _AppUserMapper
                .selectList(Wrappers.<AppUser>lambdaQuery().eq(AppUser::getRoleType, RoleTypeEnum.管理员.index()));
        if (rs.size() == 0) {
            throw new CustomException("管理员不存在");
        }
        // 随机获取一个
        return rs.get((int) (Math.random() * rs.size())).getId();
    }
  
    /**
     * 检测是否可以发消息
     */
    @Override
    @SneakyThrows
    public void CheckCanSendMessage(WechatMessageDto input) {

        // 查询对方的角色
        AppUser otherUser = _AppUserMapper.selectById(input.getOtherUserId());

        // 查询最近的对方是否给你发过消息
        WechatMessage lastMessage = _WechatMessageMpper.selectOne(
                Wrappers.<WechatMessage>lambdaQuery().eq(WechatMessage::getSelfUserId, input.getOtherUserId())
                        .eq(WechatMessage::getOtherUserId, input.getSelfUserId())
                        .orderByDesc(WechatMessage::getCreationTime).last("limit 1"));
        // 如果没发过
        if (lastMessage == null) {
            if (otherUser.getRoleType() == RoleTypeEnum.用户.index()) {

                // 判断你最近24小时内是否发过消息
                WechatMessage selfLastMessage = _WechatMessageMpper.selectOne(
                        Wrappers.<WechatMessage>lambdaQuery().eq(WechatMessage::getSelfUserId, input.getSelfUserId())
                                .eq(WechatMessage::getOtherUserId, input.getOtherUserId())
                                .orderByDesc(WechatMessage::getCreationTime).last("limit 1"));
                if (selfLastMessage != null) {
                    throw new CustomException("24小时内只能发送一条消息,在对方给你发消息之前,你不能发送消息");
                }
            }
        }

    }
}
