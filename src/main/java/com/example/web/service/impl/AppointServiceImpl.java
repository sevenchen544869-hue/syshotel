package com.example.web.service.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.web.SysConst;
import com.example.web.dto.*;
import com.example.web.dto.query.*;
import com.example.web.entity.*;
import com.example.web.mapper.*;
import com.example.web.enums.*;
import com.example.web.service.*;
import com.example.web.tools.dto.*;
import com.example.web.tools.exception.CustomException;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import lombok.SneakyThrows;
import java.io.IOException;
import com.example.web.tools.*;
import java.text.DecimalFormat;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * 预约记录功能实现类
 */
@Service
public class AppointServiceImpl extends ServiceImpl<AppointMapper, Appoint> implements AppointService {

	 /**
     * 操作数据库AppUser表mapper对象
     */
    @Autowired
    private AppUserMapper AppUserMapper;
    /**
     * 操作数据库的Appoint表mapper对象
     */
    @Autowired
    private AppointMapper AppointMapper;
    @Autowired
    private RoomDetMapper  RoomDetMapper;                        
    @Autowired
    private RoomMapper  RoomMapper;                        
    @Autowired
    private HotelMapper  HotelMapper;                        

    @Autowired
    private AppointDetMapper AppointDetMapper;

    @Autowired
    private GuestInfoMapper GuestInfoMapper;
  
    @Autowired
    private RoomMatchMapper RoomMatchMapper;

    @Autowired
    private HotelIntegralMapper HotelIntegralMapper;

    @Autowired
    private MessageNoticeService MessageNoticeService;
   /**
     * 构建表查询sql
     */
    private LambdaQueryWrapper<Appoint> BuilderQuery(AppointPagedInput input) {
       //声明一个支持预约记录查询的(拉姆达)表达式
        LambdaQueryWrapper<Appoint> queryWrapper = Wrappers.<Appoint>lambdaQuery()
                .eq(input.getId() != null && input.getId() != 0, Appoint::getId, input.getId());
   //如果前端搜索传入查询条件则拼接查询条件
        if (Extension.isNotNullOrEmpty(input.getNo())) {
             queryWrapper = queryWrapper.like(Appoint::getNo, input.getNo());
       	 }
        if (Extension.isNotNullOrEmpty(input.getComment())) {
             queryWrapper = queryWrapper.like(Appoint::getComment, input.getComment());
       	 }
        if (Extension.isNotNullOrEmpty(input.getPayType())) {
             queryWrapper = queryWrapper.like(Appoint::getPayType, input.getPayType());
       	 }

        if (input.getHotelId() != null) {
            queryWrapper = queryWrapper.eq(Appoint::getHotelId, input.getHotelId());
       	 }

        if (input.getAppointStatus() != null) {
            queryWrapper = queryWrapper.eq(Appoint::getAppointStatus, input.getAppointStatus());
       	 }

        if (input.getRoomId() != null) {
            queryWrapper = queryWrapper.eq(Appoint::getRoomId, input.getRoomId());
       	 }

        if (input.getRoomDetId() != null) {
            queryWrapper = queryWrapper.eq(Appoint::getRoomDetId, input.getRoomDetId());
       	 }

        if (input.getToUserId() != null) {
            queryWrapper = queryWrapper.eq(Appoint::getToUserId, input.getToUserId());
       	 }
        if (input.getBeginAppointTimeRange() != null && !input.getBeginAppointTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(Appoint::getBeginAppointTime, input.getBeginAppointTimeRange().get(1));
            queryWrapper = queryWrapper.ge(Appoint::getBeginAppointTime, input.getBeginAppointTimeRange().get(0));
        }
        if (input.getPayTimeRange() != null && !input.getPayTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(Appoint::getPayTime, input.getPayTimeRange().get(1));
            queryWrapper = queryWrapper.ge(Appoint::getPayTime, input.getPayTimeRange().get(0));
        }
        if (input.getEndAppointTimeRange() != null && !input.getEndAppointTimeRange().isEmpty()) {
            queryWrapper = queryWrapper.le(Appoint::getEndAppointTime, input.getEndAppointTimeRange().get(1));
            queryWrapper = queryWrapper.ge(Appoint::getEndAppointTime, input.getEndAppointTimeRange().get(0));
        }
      

 
 
     if(Extension.isNotNullOrEmpty(input.getKeyWord()))
        {
			queryWrapper=queryWrapper.and(i->i
          	   .like(Appoint::getNo,input.getKeyWord()).or()   	 
          	   .like(Appoint::getComment,input.getKeyWord()).or()   	 
          	   .like(Appoint::getPayType,input.getKeyWord()).or()   	 
        );
                                       
 		   }
    
      return queryWrapper;
    }
  
    /**
     * 处理预约记录对于的外键数据
     */
   private List<AppointDto> DispatchItem(List<AppointDto> items) throws InvocationTargetException, IllegalAccessException {
          
       for (AppointDto item : items) {           
          	            
           //查询出关联的AppUser表信息           
            AppUser  ToUserEntity= AppUserMapper.selectById(item.getToUserId());
            item.setToUserDto(ToUserEntity!=null?ToUserEntity.MapToDto():new AppUserDto());              
           
          	            
        //    //查询出关联的RoomDet表信息           
        //     RoomDet  RoomDetEntity= RoomDetMapper.selectById(item.getRoomDetId());
        //     item.setRoomDetDto(RoomDetEntity!=null?RoomDetEntity.MapToDto():new RoomDetDto());              
           
          	            
           //查询出关联的Room表信息           
            Room  RoomEntity= RoomMapper.selectById(item.getRoomId());
            item.setRoomDto(RoomEntity!=null?RoomEntity.MapToDto():new RoomDto());              
           
          	            
           //查询出关联的Hotel表信息           
            Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
            item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto());    
            
            //查询出关联的AppointDet表信息
            List<AppointDet> AppointDetList = AppointDetMapper.selectList(Wrappers.<AppointDet>lambdaQuery().eq(AppointDet::getAppointId, item.getId()));
            item.setAppointDetDtos(Extension.copyBeanList(AppointDetList,AppointDetDto.class));

            //查询出房间匹配
            List<RoomMatch> roomMatchs = RoomMatchMapper.selectList(Wrappers.<RoomMatch>lambdaQuery().eq(RoomMatch::getAppointId, item.getId()));
            item.setRoomMatchDtos(Extension.copyBeanList(roomMatchs,RoomMatchDto.class));
            for (RoomMatchDto roomMatchDto : item.getRoomMatchDtos()) {
                RoomDet roomDet = RoomDetMapper.selectById(roomMatchDto.getRoomDetId());
                roomMatchDto.setRoomDetDto(roomDet!=null?roomDet.MapToDto():new RoomDetDto());


            }
       }
       
     return items; 
   }
  
    /**
     * 预约记录分页查询
     */
    @SneakyThrows
    @Override
    public PagedResult<AppointDto> List(AppointPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Appoint> queryWrapper = BuilderQuery(input);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Appoint::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Appoint> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取预约记录数据
        IPage<Appoint> pageRecords= AppointMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= AppointMapper.selectCount(queryWrapper);
        //把Appoint实体转换成Appoint传输模型
        List<AppointDto> items= Extension.copyBeanList(pageRecords.getRecords(),AppointDto.class);

		   DispatchItem(items);
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }
  
    /**
     * 单个预约记录查询
     */
    @SneakyThrows
    @Override
    public AppointDto Get(AppointPagedInput input) {
       if(input.getId()==null)
        {
         return new AppointDto();
        }
      
       PagedResult<AppointDto> pagedResult = List(input);
        return pagedResult.getItems().stream().findFirst().orElse(new AppointDto()); 
    }

    /**
     *预约记录创建或者修改
     */
    @SneakyThrows
    @Override
    public AppointDto CreateOrEdit(AppointDto input) {
        //声明一个预约记录实体
        Appoint Appoint=input.MapToEntity();  
        //调用数据库的增加或者修改方法
        saveOrUpdate(Appoint);
        //把传输模型返回给前端
        return Appoint.MapToDto();
    }
    /**
     * 预约记录删除
     */
    @Override
    public void Delete(IdInput input) {
        Appoint entity = AppointMapper.selectById(input.getId());
        AppointMapper.deleteById(entity);

        //删除明细
        AppointDetMapper.delete(Wrappers.<AppointDet>lambdaQuery().eq(AppointDet::getAppointId, input.getId()));

        //删除房间匹配
        RoomMatchMapper.delete(Wrappers.<RoomMatch>lambdaQuery().eq(RoomMatch::getAppointId, input.getId()));

    }

    /**
     * 预约记录批量删除
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
     *用户预定
     */
     @SneakyThrows
     @Override
     public AppointDto ToOrder(AppointDto input) {
        Room room = RoomMapper.selectById(input.getRoomId());
        
        input.setNo("D" + Extension.GenerateOrderNumber());

        input.setAppointStatus(AppointStatusEnum.待支付.index());

        

         //声明一个预约记录实体
         Appoint Appoint=input.MapToEntity();  
         //调用数据库的增加或者修改方法
         saveOrUpdate(Appoint);

         //查询旅客信息
         List<GuestInfo> guestInfos = GuestInfoMapper
             .selectList(Wrappers.<GuestInfo>lambdaQuery().in(GuestInfo::getId, input.getGuestIds()));

        for (GuestInfo guestInfo : guestInfos) {
            AppointDet appointDet = new AppointDet();
            appointDet.setAppointId(Appoint.getId());
            appointDet.setAge(Extension.GetAge(guestInfo.getBirth()) + "");
            appointDet.setSex(guestInfo.getSex());
            appointDet.setName(guestInfo.getName());
            appointDet.setPhone(guestInfo.getPhone());
            appointDet.setIdCard(guestInfo.getIdCard());
            AppointDetMapper.insert(appointDet);
        }

        if (input.getUseIntegral() == true) {
            HotelIntegral hotelIntegral = new HotelIntegral();
            hotelIntegral.setUserId(Appoint.getToUserId());
            hotelIntegral.setHotelId(Appoint.getHotelId());
            hotelIntegral.setIntegralValue(-room.getIntegral() * input.getQty());
            hotelIntegral.setType("酒店预订");
            hotelIntegral.setRelativeNo(Appoint.getNo());
            hotelIntegral.setTitle("酒店预订完成积分抵扣");
            HotelIntegralMapper.insert(hotelIntegral);
        }

         //把传输模型返回给前端
         return Appoint.MapToDto();
     }

     /**
     *用户支付
     */
     @SneakyThrows
     @Override
     public AppointDto Payment(AppointDto input) {
        Appoint appoint = AppointMapper.selectById(input.getId());

        Room room = RoomMapper.selectById(appoint.getRoomId());
        Hotel hotel = HotelMapper.selectById(appoint.getHotelId());
        AppUser appUser = AppUserMapper.selectById(appoint.getToUserId());

        appoint.setPayType(input.getPayType());

        if (room.getIsImmediatelyConfirm() == true) {
            appoint.setAppointStatus(AppointStatusEnum.待入住.index());
        }
        else {
            appoint.setAppointStatus(AppointStatusEnum.待确定.index());

            String Content = "您有新的订单需要处理，订单号：" 
            + appoint.getNo() + "，房间：" + room.getTitle() 
            + "，联系电话：" + appUser.getPhoneNumber() 
            + "，房间数：" + appoint.getQty() 
            + "，使用时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime();
            //发送给酒店管理员邮箱
            MessageNoticeService.AddSendEmailRecord(hotel.getHotelUserId(), "新订单提醒", Content, LocalDateTime.now());
        }
        appoint.setPayTime(LocalDateTime.now());

        saveOrUpdate(appoint);
        return appoint.MapToDto();
     }

     /**
     *用户预订取消
     */
     @SneakyThrows
     @Override
     public void UserCancel(IdInput input) {

        Appoint appoint = AppointMapper.selectById(input.getId());

        Room room = RoomMapper.selectById(appoint.getRoomId());   

        AppUser appUser = AppUserMapper.selectById(appoint.getToUserId());
        Hotel hotel = HotelMapper.selectById(appoint.getHotelId());

        //一个月内只能允许同一个酒店取消5次  （没写）

        if (appoint.getAppointStatus() == AppointStatusEnum.待支付.index()) {
            appoint.setAppointStatus(AppointStatusEnum.用户取消.index());
        }
        else if (appoint.getAppointStatus() == AppointStatusEnum.待确定.index()) {

            if (room.getIsFreeCancel()==true) {
                appoint.setAppointStatus(AppointStatusEnum.用户取消.index());
            }
            else {
                // 非免费取消：变更为申请取消，由酒店侧处理
                appoint.setAppointStatus(AppointStatusEnum.申请取消.index());
                // 先按最低价记录建议退款金额（可由酒店后续调整）
                appoint.setReturnMoney(room.getMinPrice());
            }
        }
        else if (appoint.getAppointStatus() == AppointStatusEnum.待入住.index()) {
            if (room.getIsFreeCancel()==true) {
                appoint.setAppointStatus(AppointStatusEnum.用户取消.index());
            }
            else {
                appoint.setAppointStatus(AppointStatusEnum.申请取消.index());

                String Content = "您有新的订单需要处理，订单号：" 
            + appoint.getNo() + "，房间：" + room.getTitle() 
            + "，联系电话：" + appUser.getPhoneNumber() 
            + "，房间数：" + appoint.getQty() 
            + "，使用时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime()
            + "申请取消，请及时处理";
            //发送给酒店管理员邮箱
            MessageNoticeService.AddSendEmailRecord(hotel.getHotelUserId(), "申请取消订单提醒", Content, LocalDateTime.now());
            }
            
        }
        else {
            throw new CustomException("当前状态不支持取消");
        }

        saveOrUpdate(appoint);
        ReturnIntegral(appoint);
     }
     //积分退还
     private void ReturnIntegral(Appoint input) {
        List<HotelIntegral> hotelIntegrals = HotelIntegralMapper
        .selectList(Wrappers.<HotelIntegral>lambdaQuery()
            .eq(HotelIntegral::getType, "酒店预订")
            .eq(HotelIntegral::getRelativeNo, input.getNo()));
        //求和积分
        if (hotelIntegrals.size() > 0) {
            Double integralValue = hotelIntegrals.stream().mapToDouble(HotelIntegral::getIntegralValue).sum();
            HotelIntegral hotelIntegral = new HotelIntegral();
            hotelIntegral.setUserId(input.getToUserId());
            hotelIntegral.setHotelId(input.getHotelId());
            hotelIntegral.setIntegralValue(Math.abs(integralValue));
            hotelIntegral.setType("酒店预订取消");
            hotelIntegral.setRelativeNo(input.getNo());
            hotelIntegral.setTitle("酒店预订取消积分返还");
            HotelIntegralMapper.insert(hotelIntegral);
        }
     }

     /**
     *酒店处理用户申请取消
     */
     @SneakyThrows
     @Override
     public void HotelDoWithUserCancel(AppointDto input) {

        Appoint appoint = AppointMapper.selectById(input.getId());

        appoint.setAppointStatus(AppointStatusEnum.酒店取消.index());
        if (appoint.getTotalMoney() < input.getReturnMoney()) {
            throw new CustomException("退款金额不能大于订单金额");
        }
        appoint.setReturnMoney(input.getReturnMoney());
        appoint.setAppointStatus(AppointStatusEnum.用户取消.index());
        saveOrUpdate(appoint);
        ReturnIntegral(appoint);
     }

     /**
     *酒店处理预约确认
     */
     @SneakyThrows
     @Override
     public void HotelConfirm(AppointDto input) {
        Appoint appoint = AppointMapper.selectById(input.getId());

        Room room = RoomMapper.selectById(appoint.getRoomId());
        AppUser appUser = AppUserMapper.selectById(appoint.getToUserId());
        Hotel hotel = HotelMapper.selectById(appoint.getHotelId());

        appoint.setAppointStatus(AppointStatusEnum.待入住.index());

        saveOrUpdate(appoint);

        // 给用户发送确认接单通知
        String userContent = "尊敬的用户，您预订的酒店已确认接单。" 
            + "订单号：" + appoint.getNo() 
            + "，房型：" + room.getTitle()
            + "，房间数：" + appoint.getQty()
            + "，入住时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime()
            + "。酒店名称：" + hotel.getName()
            + "，请留意酒店电话并按时办理入住。";
        MessageNoticeService.AddSendEmailRecord(appoint.getToUserId(), "订单确认通知", userContent, LocalDateTime.now());

        // 通知酒店管理员已确认接单
        String adminContent = "酒店已确认接单，订单号：" 
            + appoint.getNo() + "，房间：" + room.getTitle() 
            + "，联系电话：" + appUser.getPhoneNumber() 
            + "，房间数：" + appoint.getQty() 
            + "，使用时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime();
        MessageNoticeService.AddSendEmailRecord(hotel.getHotelUserId(), "订单确认提醒", adminContent, LocalDateTime.now());
     } 

     /**
     *酒店处理预约取消
     */
     @SneakyThrows
     @Override
     public void HotelCancel(AppointDto input) {
        Appoint appoint = AppointMapper.selectById(input.getId());

        Room room = RoomMapper.selectById(appoint.getRoomId());
        AppUser appUser = AppUserMapper.selectById(appoint.getToUserId());
        Hotel hotel = HotelMapper.selectById(appoint.getHotelId());

        appoint.setAppointStatus(AppointStatusEnum.酒店取消.index());
        saveOrUpdate(appoint);
        ReturnIntegral(appoint);

        // 给用户发送取消接单通知
        String userContent = "尊敬的用户，您预订的酒店已取消接单。" 
            + "订单号：" + appoint.getNo() 
            + "，房型：" + room.getTitle()
            + "，房间数：" + appoint.getQty()
            + "，入住时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime()
            + "。酒店名称：" + hotel.getName()
            + "，如有疑问请联系酒店。";
        MessageNoticeService.AddSendEmailRecord(appoint.getToUserId(), "订单取消通知", userContent, LocalDateTime.now());

        // 通知酒店管理员已取消接单
        String adminContent = "酒店已取消接单，订单号：" 
            + appoint.getNo() + "，房间：" + room.getTitle() 
            + "，联系电话：" + appUser.getPhoneNumber() 
            + "，房间数：" + appoint.getQty() 
            + "，使用时间：" + appoint.getBeginAppointTime() + "至" + appoint.getEndAppointTime();
        MessageNoticeService.AddSendEmailRecord(hotel.getHotelUserId(), "订单取消提醒", adminContent, LocalDateTime.now());
     }


     /**
      * 自动取消未付款的订单
     */
     @SneakyThrows
     @Override
     public void AutoCancel() {
        //查询创建时间距离当前时间超过15分钟的订单
        List<Appoint> appoints = AppointMapper
            .selectList(Wrappers.<Appoint>lambdaQuery()
                .eq(Appoint::getAppointStatus, AppointStatusEnum.待支付.index())
                .le(Appoint::getCreationTime, LocalDateTime.now().minusMinutes(15)));
        for (Appoint appoint : appoints) {
            appoint.setAppointStatus(AppointStatusEnum.系统取消.index());
            saveOrUpdate(appoint);
            ReturnIntegral(appoint);
        }
        
     }

     /**
      * 退房
      */
     @SneakyThrows
     @Override
     public void CheckOut(AppointDto input) {
        Appoint appoint = AppointMapper.selectById(input.getId());
        appoint.setAppointStatus(AppointStatusEnum.完成退房.index());
        saveOrUpdate(appoint);

        //查询出房间匹配
        List<RoomMatch> roomMatchs = RoomMatchMapper
            .selectList(Wrappers.<RoomMatch>lambdaQuery().eq(RoomMatch::getAppointId, appoint.getId()));
        for (RoomMatch roomMatch : roomMatchs ) {
            roomMatch.setRoomStatus(RoomStatusEnum.空闲.index());
            //roomMatch.setEndActiveTime(LocalDateTime.now());
            RoomMatchMapper.updateById(roomMatch);
        }
        //赠送积分
        HotelIntegral hotelIntegral = new HotelIntegral();
        hotelIntegral.setUserId(appoint.getToUserId());
        hotelIntegral.setHotelId(appoint.getHotelId());
        hotelIntegral.setIntegralValue(Extension.ToFixed0(appoint.getTotalMoney() * 0.1));
        if (hotelIntegral.getIntegralValue() < 10) {
            hotelIntegral.setIntegralValue(10.0);
        }
        hotelIntegral.setType("酒店退房完成");
        hotelIntegral.setRelativeNo(appoint.getNo());
        hotelIntegral.setTitle("酒店退房完成赠送积分");
        
        HotelIntegralMapper.insert(hotelIntegral);
     }

     /**
      * 评价
      */
     @SneakyThrows
     @Override
     public void Comment(AppointDto input) {
        Appoint appoint = AppointMapper.selectById(input.getId());
        appoint.setAppointStatus(AppointStatusEnum.完成.index());
        appoint.setCommentScore(input.getCommentScore());
        appoint.setComment(input.getComment());

        saveOrUpdate(appoint);

        //如果评价是好评赠送10积分 大于3是好评
        if (input.getCommentScore() > 3) {
            HotelIntegral hotelIntegral = new HotelIntegral();
            hotelIntegral.setUserId(appoint.getToUserId());
            hotelIntegral.setHotelId(appoint.getHotelId());
            hotelIntegral.setIntegralValue(10.0);
            hotelIntegral.setType("酒店评价好评");
            hotelIntegral.setRelativeNo(appoint.getNo());
            hotelIntegral.setTitle("酒店评价好评赠送积分");
            HotelIntegralMapper.insert(hotelIntegral);
        }

     }


     /**
     * 酒店评价
     */
    @SneakyThrows
    @Override
    public PagedResult<AppointDto> CommentList(AppointPagedInput input) {
			//构建where条件+排序
        LambdaQueryWrapper<Appoint> queryWrapper = BuilderQuery(input);
        queryWrapper = queryWrapper
            .isNotNull(Appoint::getComment)
            .ne(Appoint::getComment, "")
            .isNotNull(Appoint::getCommentScore);
        // 动态排序处理
        if (input.getSortItem() != null) {
            // 根据字段名动态排序
            queryWrapper.last("ORDER BY " + input.getSortItem().getFieldName()
                    + (input.getSortItem().getIsAsc() ? " ASC" : " DESC"));
        } else {
            // 默认按创建时间从大到小排序
            queryWrapper = queryWrapper.orderByDesc(Appoint::getCreationTime);
        }

        //构建一个分页查询的model
        Page<Appoint> page = new Page<>(input.getPage(), input.getLimit());
         //从数据库进行分页查询获取预约记录数据
        IPage<Appoint> pageRecords= AppointMapper.selectPage(page, queryWrapper);
        //获取所有满足条件的数据行数
        Long totalCount= AppointMapper.selectCount(queryWrapper);
        //把Appoint实体转换成Appoint传输模型
        List<AppointDto> items= Extension.copyBeanList(pageRecords.getRecords(),AppointDto.class);

		for (AppointDto item : items) {
        //查询出关联的AppUser表信息           
        AppUser  ToUserEntity= AppUserMapper.selectById(item.getToUserId());
        item.setToUserDto(ToUserEntity!=null?ToUserEntity.MapToDto():new AppUserDto());              

        //查询出关联的Room表信息           
        Room  RoomEntity= RoomMapper.selectById(item.getRoomId());
        item.setRoomDto(RoomEntity!=null?RoomEntity.MapToDto():new RoomDto());     
        
        //查询出关联的Hotel表信息           
        Hotel  HotelEntity= HotelMapper.selectById(item.getHotelId());
        item.setHotelDto(HotelEntity!=null?HotelEntity.MapToDto():new HotelDto()); 
        }
        //返回一个分页结构给前端
        return PagedResult.GetInstance(items,totalCount);

    }

    

}
