package com.example.web.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.web.dto.RoomMatchDto;
import com.example.web.dto.query.AdminHotelDataAnalysisQueryInput;
import com.example.web.dto.query.HotelDataAnalysisQueryInput;
import com.example.web.entity.*;
import com.example.web.enums.AppointStatusEnum;
import com.example.web.mapper.*;
import com.example.web.service.BaseService;
import com.example.web.tools.Extension;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseServiceImpl implements BaseService {

    @Autowired
    private AppUserMapper AppUserMapper;
    @Autowired
    private RoomMapper RoomMapper;

    @Autowired
    private RoomDetMapper RoomDetMapper;

    @Autowired
    private FavourableSettingMapper FavourableSettingMapper;
    @Autowired
    private RoomPriceMapper RoomPriceMapper;

    @Autowired
    private AppointMapper AppointMapper;
    /**
     * 操作数据库的RoomMatch表mapper对象
     */
    @Autowired
    private RoomMatchMapper RoomMatchMapper;

    @Autowired
    private HotelMapper HotelMapper;
    
    @Autowired
    private StorageRecordMapper StorageRecordMapper;
    @Autowired
    private EmployeeMapper EmployeeMapper;
    @Autowired
    private GuestInfoMapper GuestInfoMapper;

    /**
     * 得到房间选择时间段的价格
     *
     * @param hotelId
     * @param roomId
     * @return
     */
    public Double GetRoomPriceByDateList(Integer hotelId, Integer roomId, LocalDateTime checkInDate,
            LocalDateTime checkOutDate, Boolean isIgnoreFavourable) {
        Double price = 0.0;

        for (LocalDateTime date = checkInDate; date.isBefore(checkOutDate); date = date.plusDays(1)) {
            price += GetRoomPriceByDate(hotelId, roomId, date, isIgnoreFavourable);
        }
        return price;
    }

    /**
     * 得到房间选择某天的价格
     *
     * @param hotelId
     * @param roomId
     * @return
     */
    public Double GetRoomPriceByDate(Integer hotelId, Integer roomId, LocalDateTime date, Boolean isIgnoreFavourable) {
        Room room = RoomMapper.selectById(roomId);
        // 平常价格
        Double price = Extension.ToFixed0(((room.getMaxPrice() + room.getMinPrice()) / 2));

        // 查询当天的价格
        RoomPrice roomPrice = RoomPriceMapper.selectList(Wrappers.<RoomPrice>lambdaQuery()
                .eq(RoomPrice::getHotelId, hotelId)
                .eq(RoomPrice::getRoomId, roomId)
                .ge(RoomPrice::getBeginActiveTime, date)
                .le(RoomPrice::getEndActiveTime, date)).stream().findFirst().orElse(null);
        if (roomPrice != null) {
            price = roomPrice.getPrice();
        }

        // 查询当天满足的折扣
        FavourableSetting favourableSetting = FavourableSettingMapper
                .selectList(Wrappers.<FavourableSetting>lambdaQuery()
                        .eq(FavourableSetting::getHotelId, hotelId)
                        .ge(FavourableSetting::getBeginActiveTime, date)
                        .le(FavourableSetting::getEndActiveTime, date))
                .stream().findFirst().orElse(null);
        if (favourableSetting != null) {
            price = Extension.ToFixed0(price * (1 - favourableSetting.getDiscount()));
        }

        return price;
    }

    /**
     * 得到房间剩余数量
     */
    public Long GetRemainingRoomNumber(Integer hotelId, Integer roomId, LocalDateTime checkInDate,
            LocalDateTime checkOutDate) {

        // 总房间数量
        Long roomDetCount = RoomDetMapper.selectCount(Wrappers.<RoomDet>lambdaQuery()
                .eq(RoomDet::getHotelId, hotelId)
                .eq(RoomDet::getRoomId, roomId));

        Long appointCount = AppointMapper.selectCount(Wrappers.<Appoint>lambdaQuery()
                .eq(Appoint::getHotelId, hotelId)
                .eq(Appoint::getRoomId, roomId)
                .notIn(Appoint::getAppointStatus, AppointStatusEnum.用户取消.index(),
                        AppointStatusEnum.酒店取消.index(), AppointStatusEnum.系统取消.index())
                .ge(Appoint::getBeginAppointTime, checkInDate)
                .le(Appoint::getEndAppointTime, checkOutDate));

        return roomDetCount - appointCount;
    }

    /**
     * 得到某天某个房间的使用情况
     */
    @SneakyThrows
    @Override
    public RoomMatchDto GetRoomRoomMatch(Integer hotelId, Integer roomId, Integer roomDetId, LocalDateTime date) {
        RoomMatch roomMatch = RoomMatchMapper.selectList(Wrappers.<RoomMatch>lambdaQuery()
                .eq(RoomMatch::getHotelId, hotelId)
                .eq(RoomMatch::getRoomId, roomId)
                .eq(RoomMatch::getRoomDetId, roomDetId)
                .ge(RoomMatch::getBeginActiveTime, date)
                .le(RoomMatch::getEndActiveTime, date).orderByDesc(RoomMatch::getCreationTime)).stream().findFirst()
                .orElse(null);
        if (roomMatch != null) {
            return roomMatch.MapToDto();
        }
        return null;
    }

    /**
     * 根据地址获取对应的酒店ids
     */
    @SneakyThrows
    @Override
    public List<Integer> GetHotelIdsByAddress(String address) {
        List<Hotel> hotels = HotelMapper.selectList(Wrappers.<Hotel>lambdaQuery()
                .like(Hotel::getProviceCityArea, address));
        return hotels.stream().map(Hotel::getId).collect(Collectors.toList());
    }

    /**
     * 酒店数据综合分析
     */
    @SneakyThrows
    @Override
    public HashMap<String, Object> HotelDataAnalysis(HotelDataAnalysisQueryInput input) {
        HashMap<String, Object> result = new HashMap<>();
        Integer hotelId = input.getHotelId();
        LocalDateTime startDate = input.getStartTime();
        LocalDateTime endDate = input.getEndTime();

        // 查询该时间段内所有预约
        List<Appoint> appoints = AppointMapper.selectList(Wrappers.<Appoint>lambdaQuery()
                .eq(hotelId != null, Appoint::getHotelId, hotelId)
                .ge(startDate != null, Appoint::getBeginAppointTime, startDate)
                .le(endDate != null, Appoint::getEndAppointTime, endDate));

        if (appoints == null || appoints.isEmpty()) {
            result.put("message", "该时间段内无预订数据");
            return result;
        }

        // 1. 预约状态统计
        int totalAppointCount = appoints.size();
        int successAppointCount = 0;
        int cancelByUserCount = 0;
        int cancelByHotelCount = 0;
        int cancelBySystemCount = 0;
        int pendingCount = 0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);
            Integer status = appoint.getAppointStatus();

            if (status == null)
                continue;

            if (status.equals(AppointStatusEnum.待入住.index())) {
                successAppointCount++;
            } else if (status.equals(AppointStatusEnum.用户取消.index())) {
                cancelByUserCount++;
            } else if (status.equals(AppointStatusEnum.酒店取消.index())) {
                cancelByHotelCount++;
            } else if (status.equals(AppointStatusEnum.系统取消.index())) {
                cancelBySystemCount++;
            } else if (status.equals(AppointStatusEnum.待确定.index())) {
                pendingCount++;
            }
        }

        HashMap<String, Object> statusStats = new HashMap<>();
        statusStats.put("总预约数", totalAppointCount);
        statusStats.put("预约成功数", successAppointCount);
        statusStats.put("用户取消数", cancelByUserCount);
        statusStats.put("酒店取消数", cancelByHotelCount);
        statusStats.put("系统取消数", cancelBySystemCount);
        statusStats.put("待确认数", pendingCount);
        statusStats.put("成功率", totalAppointCount > 0 ? (double) successAppointCount / totalAppointCount : 0);
        statusStats.put("用户取消率", totalAppointCount > 0 ? (double) cancelByUserCount / totalAppointCount : 0);
        result.put("预约状态统计", statusStats);

        // 2. 按季节统计预约量和收入
        HashMap<String, Integer> seasonAppointCount = new HashMap<>();
        HashMap<String, Double> seasonRevenue = new HashMap<>();
        seasonAppointCount.put("春季", 0);
        seasonAppointCount.put("夏季", 0);
        seasonAppointCount.put("秋季", 0);
        seasonAppointCount.put("冬季", 0);
        seasonRevenue.put("春季", 0.0);
        seasonRevenue.put("夏季", 0.0);
        seasonRevenue.put("秋季", 0.0);
        seasonRevenue.put("冬季", 0.0);

        double totalRevenue = 0.0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);

            if (appoint.getBeginAppointTime() == null)
                continue;

            int month = appoint.getBeginAppointTime().getMonthValue();
            String season;

            if (month >= 3 && month <= 5) {
                season = "春季";
            } else if (month >= 6 && month <= 8) {
                season = "夏季";
            } else if (month >= 9 && month <= 11) {
                season = "秋季";
            } else {
                season = "冬季";
            }

            seasonAppointCount.put(season, seasonAppointCount.get(season) + 1);

            Double price = appoint.getTotalMoney();
            if (price != null) {
                seasonRevenue.put(season, seasonRevenue.get(season) + price);
                totalRevenue += price;
            }
        }

        result.put("季节预约统计", seasonAppointCount);
        result.put("季节收入统计", seasonRevenue);
        result.put("总收入", totalRevenue);

        // 3. 房间类型受欢迎度分析
        if (hotelId != null) {
            List<Room> rooms = RoomMapper.selectList(Wrappers.<Room>lambdaQuery()
                    .eq(Room::getHotelId, hotelId));

            if (rooms != null && !rooms.isEmpty()) {
                HashMap<Integer, String> roomIdToName = new HashMap<>();
                HashMap<String, Integer> roomTypeBookingCount = new HashMap<>();
                HashMap<String, Double> roomTypeRevenue = new HashMap<>();

                for (Room room : rooms) {
                    if (room.getId() != null && room.getTitle() != null) {
                        roomIdToName.put(room.getId(), room.getTitle());
                        roomTypeBookingCount.put(room.getTitle(), 0);
                        roomTypeRevenue.put(room.getTitle(), 0.0);
                    }
                }

                for (int i = 0; i < appoints.size(); i++) {
                    Appoint appoint = appoints.get(i);
                    Integer roomId = appoint.getRoomId();

                    if (roomId != null && roomIdToName.containsKey(roomId)) {
                        String roomName = roomIdToName.get(roomId);
                        roomTypeBookingCount.put(roomName, roomTypeBookingCount.get(roomName) + 1);

                        Double price = appoint.getTotalMoney();
                        if (price != null) {
                            roomTypeRevenue.put(roomName, roomTypeRevenue.get(roomName) + price);
                        }
                    }
                }

                result.put("房型预约统计", roomTypeBookingCount);
                result.put("房型收入统计", roomTypeRevenue);
            }
        }

        // 4. 入住时长分析
        int oneDayStay = 0;
        int twoDayStay = 0;
        int threeDayStay = 0;
        int fourToSevenDayStay = 0;
        int moreThanSevenDayStay = 0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);

            if (appoint.getBeginAppointTime() != null && appoint.getEndAppointTime() != null) {
                long days = java.time.Duration.between(
                        appoint.getBeginAppointTime(),
                        appoint.getEndAppointTime()).toDays();

                if (days == 1) {
                    oneDayStay++;
                } else if (days == 2) {
                    twoDayStay++;
                } else if (days == 3) {
                    threeDayStay++;
                } else if (days >= 4 && days <= 7) {
                    fourToSevenDayStay++;
                } else if (days > 7) {
                    moreThanSevenDayStay++;
                }
            }
        }

        HashMap<String, Integer> stayDurationStats = new HashMap<>();
        stayDurationStats.put("入住1天", oneDayStay);
        stayDurationStats.put("入住2天", twoDayStay);
        stayDurationStats.put("入住3天", threeDayStay);
        stayDurationStats.put("入住4-7天", fourToSevenDayStay);
        stayDurationStats.put("入住超过7天", moreThanSevenDayStay);
        result.put("入住时长统计", stayDurationStats);

        // 5. 周内/周末预订分析
        int weekdayBookings = 0;
        int weekendBookings = 0;
        double weekdayRevenue = 0.0;
        double weekendRevenue = 0.0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);

            if (appoint.getBeginAppointTime() != null) {
                int dayOfWeek = appoint.getBeginAppointTime().getDayOfWeek().getValue();
                boolean isWeekend = (dayOfWeek == 6 || dayOfWeek == 7);

                if (isWeekend) {
                    weekendBookings++;
                    if (appoint.getTotalMoney() != null) {
                        weekendRevenue += appoint.getTotalMoney();
                    }
                } else {
                    weekdayBookings++;
                    if (appoint.getTotalMoney() != null) {
                        weekdayRevenue += appoint.getTotalMoney();
                    }
                }
            }
        }

        HashMap<String, Object> weekdayWeekendStats = new HashMap<>();
        weekdayWeekendStats.put("工作日预订数", weekdayBookings);
        weekdayWeekendStats.put("周末预订数", weekendBookings);
        weekdayWeekendStats.put("工作日收入", weekdayRevenue);
        weekdayWeekendStats.put("周末收入", weekendRevenue);
        weekdayWeekendStats.put("周末预订比例", totalAppointCount > 0 ? (double) weekendBookings / totalAppointCount : 0);
        result.put("工作日/周末统计", weekdayWeekendStats);

        // 6. 提前预订天数分析
        int sameDayBooking = 0;
        int oneDayAdvance = 0;
        int twoDaysAdvance = 0;
        int threeToSevenDaysAdvance = 0;
        int moreThanSevenDaysAdvance = 0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);

            if (appoint.getCreationTime() != null && appoint.getBeginAppointTime() != null) {
                long daysInAdvance = java.time.Duration.between(
                        appoint.getCreationTime(),
                        appoint.getBeginAppointTime()).toDays();

                if (daysInAdvance == 0) {
                    sameDayBooking++;
                } else if (daysInAdvance == 1) {
                    oneDayAdvance++;
                } else if (daysInAdvance == 2) {
                    twoDaysAdvance++;
                } else if (daysInAdvance >= 3 && daysInAdvance <= 7) {
                    threeToSevenDaysAdvance++;
                } else if (daysInAdvance > 7) {
                    moreThanSevenDaysAdvance++;
                }
            }
        }

        HashMap<String, Integer> advanceBookingStats = new HashMap<>();
        advanceBookingStats.put("当天预订", sameDayBooking);
        advanceBookingStats.put("提前1天", oneDayAdvance);
        advanceBookingStats.put("提前2天", twoDaysAdvance);
        advanceBookingStats.put("提前3-7天", threeToSevenDaysAdvance);
        advanceBookingStats.put("提前超过7天", moreThanSevenDaysAdvance);
        result.put("提前预订天数统计", advanceBookingStats);

        return result;
    }


    /**
     * 管理员酒店数据分析
     */
    @SneakyThrows
    @Override
    public HashMap<String, Object> SynthesizeStatistics(HotelDataAnalysisQueryInput input) {
        HashMap<String, Object> result = new HashMap<>();

        java.time.LocalDateTime startTime = null;
        java.time.LocalDateTime endTime = null;

        // 兼容 QueryInput 未提供 getter 的场景，避免直接调用报错
        if (input != null) {
            try {
                java.lang.reflect.Field startField = input.getClass().getDeclaredField("StartTime");
                startField.setAccessible(true);
                Object startObj = startField.get(input);
                if (startObj instanceof java.time.LocalDateTime) {
                    startTime = (java.time.LocalDateTime) startObj;
                }
            } catch (Exception ignored) {
            }

            try {
                java.lang.reflect.Field endField = input.getClass().getDeclaredField("EndTime");
                endField.setAccessible(true);
                Object endObj = endField.get(input);
                if (endObj instanceof java.time.LocalDateTime) {
                    endTime = (java.time.LocalDateTime) endObj;
                }
            } catch (Exception ignored) {
            }
        }

        // 不依赖 Calendar 实参：默认统计近12个月
        if (endTime == null) {
            endTime = java.time.LocalDateTime.now();
        }
        if (startTime == null) {
            startTime = endTime.minusMonths(12);
        }
        if (startTime.isAfter(endTime)) {
            java.time.LocalDateTime temp = startTime;
            startTime = endTime;
            endTime = temp;
        }

        // 枚举状态预定义变量（按要求不使用动态数组）
        final int statusPendingPay = AppointStatusEnum.待支付.index();
        final int statusPendingConfirm = AppointStatusEnum.待确定.index();
        final int statusPendingCheckIn = AppointStatusEnum.待入住.index();
        final int statusCheckOutDone = AppointStatusEnum.完成退房.index();
        final int statusDone = AppointStatusEnum.完成.index();
        final int statusUserCancel = AppointStatusEnum.用户取消.index();
        final int statusHotelCancel = AppointStatusEnum.酒店取消.index();
        final int statusSystemCancel = AppointStatusEnum.系统取消.index();

        List<Appoint> appoints = AppointMapper.selectList(Wrappers.<Appoint>lambdaQuery()
                .ge(Appoint::getCreationTime, startTime)
                .le(Appoint::getCreationTime, endTime));

        result.put("StartTime", startTime);
        result.put("EndTime", endTime);

        if (appoints == null || appoints.isEmpty()) {
            result.put("TotalAppointCount", 0);
            result.put("TotalRevenue", 0.0);
            result.put("AvgOrderPrice", 0.0);
            result.put("CancelRate", 0.0);
            result.put("message", "当前统计区间内暂无订单数据");

            result.put("StatusSummary", new HashMap<String, Object>());
            result.put("SeasonSummary", new HashMap<String, Object>());
            result.put("MonthSummary", new HashMap<String, Object>());
            result.put("HourSummary", new HashMap<String, Object>());
            result.put("LeadTimeSummary", new HashMap<String, Object>());
            result.put("StayDurationSummary", new HashMap<String, Object>());
            result.put("RiskInsight", new HashMap<String, Object>());
            return result;
        }

        int totalCount = appoints.size();
        int paidOrConfirmedCount = 0;
        int cancelCount = 0;

        int pendingPayCount = 0;
        int pendingConfirmCount = 0;
        int pendingCheckInCount = 0;
        int checkOutDoneCount = 0;
        int doneCount = 0;
        int userCancelCount = 0;
        int hotelCancelCount = 0;
        int systemCancelCount = 0;

        double totalRevenue = 0.0;

        HashMap<String, Integer> seasonCount = new HashMap<>();
        HashMap<String, Double> seasonRevenue = new HashMap<>();
        seasonCount.put("春季", 0);
        seasonCount.put("夏季", 0);
        seasonCount.put("秋季", 0);
        seasonCount.put("冬季", 0);
        seasonRevenue.put("春季", 0.0);
        seasonRevenue.put("夏季", 0.0);
        seasonRevenue.put("秋季", 0.0);
        seasonRevenue.put("冬季", 0.0);

        HashMap<String, Integer> monthCount = new HashMap<>();
        HashMap<String, Double> monthRevenue = new HashMap<>();
        for (int m = 1; m <= 12; m++) {
            String key = m + "月";
            monthCount.put(key, 0);
            monthRevenue.put(key, 0.0);
        }

        HashMap<String, Integer> hourCount = new HashMap<>();
        hourCount.put("凌晨(0-5)", 0);
        hourCount.put("上午(6-11)", 0);
        hourCount.put("下午(12-17)", 0);
        hourCount.put("晚上(18-23)", 0);

        int lead0Day = 0;
        int lead1Day = 0;
        int lead2To3Day = 0;
        int lead4To7Day = 0;
        int lead8To14Day = 0;
        int leadOver14Day = 0;

        int stay1Day = 0;
        int stay2Day = 0;
        int stay3To5Day = 0;
        int stay6To10Day = 0;
        int stayOver10Day = 0;

        for (int i = 0; i < appoints.size(); i++) {
            Appoint appoint = appoints.get(i);
            if (appoint == null) {
                continue;
            }

            Integer status = appoint.getAppointStatus();
            if (status != null) {
                if (status == statusPendingPay) {
                    pendingPayCount++;
                } else if (status == statusPendingConfirm) {
                    pendingConfirmCount++;
                } else if (status == statusPendingCheckIn) {
                    pendingCheckInCount++;
                    paidOrConfirmedCount++;
                } else if (status == statusCheckOutDone) {
                    checkOutDoneCount++;
                    paidOrConfirmedCount++;
                } else if (status == statusDone) {
                    doneCount++;
                    paidOrConfirmedCount++;
                } else if (status == statusUserCancel) {
                    userCancelCount++;
                    cancelCount++;
                } else if (status == statusHotelCancel) {
                    hotelCancelCount++;
                    cancelCount++;
                } else if (status == statusSystemCancel) {
                    systemCancelCount++;
                    cancelCount++;
                }
            }

            Double money = appoint.getTotalMoney() == null ? 0.0 : appoint.getTotalMoney();
            totalRevenue += money;

            java.time.LocalDateTime beginTime = appoint.getBeginAppointTime();
            if (beginTime != null) {
                int month = beginTime.getMonthValue();
                String seasonKey = "冬季";
                if (month >= 3 && month <= 5) {
                    seasonKey = "春季";
                } else if (month >= 6 && month <= 8) {
                    seasonKey = "夏季";
                } else if (month >= 9 && month <= 11) {
                    seasonKey = "秋季";
                }
                seasonCount.put(seasonKey, seasonCount.get(seasonKey) + 1);
                seasonRevenue.put(seasonKey, seasonRevenue.get(seasonKey) + money);

                String monthKey = month + "月";
                monthCount.put(monthKey, monthCount.get(monthKey) + 1);
                monthRevenue.put(monthKey, monthRevenue.get(monthKey) + money);
            }

            java.time.LocalDateTime createTime = appoint.getCreationTime();
            if (createTime != null) {
                int hour = createTime.getHour();
                if (hour <= 5) {
                    hourCount.put("凌晨(0-5)", hourCount.get("凌晨(0-5)") + 1);
                } else if (hour <= 11) {
                    hourCount.put("上午(6-11)", hourCount.get("上午(6-11)") + 1);
                } else if (hour <= 17) {
                    hourCount.put("下午(12-17)", hourCount.get("下午(12-17)") + 1);
                } else {
                    hourCount.put("晚上(18-23)", hourCount.get("晚上(18-23)") + 1);
                }
            }

            if (createTime != null && beginTime != null) {
                long leadDays = java.time.Duration.between(createTime, beginTime).toDays();
                if (leadDays <= 0) {
                    lead0Day++;
                } else if (leadDays == 1) {
                    lead1Day++;
                } else if (leadDays <= 3) {
                    lead2To3Day++;
                } else if (leadDays <= 7) {
                    lead4To7Day++;
                } else if (leadDays <= 14) {
                    lead8To14Day++;
                } else {
                    leadOver14Day++;
                }
            }

            java.time.LocalDateTime endAppointTime = appoint.getEndAppointTime();
            if (beginTime != null && endAppointTime != null) {
                long stayDays = java.time.Duration.between(beginTime, endAppointTime).toDays();
                if (stayDays <= 1) {
                    stay1Day++;
                } else if (stayDays == 2) {
                    stay2Day++;
                } else if (stayDays <= 5) {
                    stay3To5Day++;
                } else if (stayDays <= 10) {
                    stay6To10Day++;
                } else {
                    stayOver10Day++;
                }
            }

        }

        HashMap<String, Object> statusSummary = new HashMap<>();
        statusSummary.put("待支付", pendingPayCount);
        statusSummary.put("待确定", pendingConfirmCount);
        statusSummary.put("待入住", pendingCheckInCount);
        statusSummary.put("完成退房", checkOutDoneCount);
        statusSummary.put("已完成", doneCount);
        statusSummary.put("用户取消", userCancelCount);
        statusSummary.put("酒店取消", hotelCancelCount);
        statusSummary.put("系统取消", systemCancelCount);
        statusSummary.put("取消率", totalCount == 0 ? 0.0 : (double) cancelCount / totalCount);
        statusSummary.put("有效履约率", totalCount == 0 ? 0.0 : (double) paidOrConfirmedCount / totalCount);

        HashMap<String, Object> seasonSummary = new HashMap<>();
        seasonSummary.put("季节订单量", seasonCount);
        seasonSummary.put("季节营收", seasonRevenue);

        HashMap<String, Object> monthSummary = new HashMap<>();
        monthSummary.put("月度订单量", monthCount);
        monthSummary.put("月度营收", monthRevenue);

        HashMap<String, Integer> leadTimeSummary = new HashMap<>();
        leadTimeSummary.put("当天预订", lead0Day);
        leadTimeSummary.put("提前1天", lead1Day);
        leadTimeSummary.put("提前2-3天", lead2To3Day);
        leadTimeSummary.put("提前4-7天", lead4To7Day);
        leadTimeSummary.put("提前8-14天", lead8To14Day);
        leadTimeSummary.put("提前14天以上", leadOver14Day);

        HashMap<String, Integer> stayDurationSummary = new HashMap<>();
        stayDurationSummary.put("1天内", stay1Day);
        stayDurationSummary.put("2天", stay2Day);
        stayDurationSummary.put("3-5天", stay3To5Day);
        stayDurationSummary.put("6-10天", stay6To10Day);
        stayDurationSummary.put("10天以上", stayOver10Day);

        HashMap<String, Object> riskInsight = new HashMap<>();
        int nightOrderCount = hourCount.get("凌晨(0-5)") == null ? 0 : hourCount.get("凌晨(0-5)");
        double nightOrderRatio = totalCount == 0 ? 0.0 : (double) nightOrderCount / totalCount;
        riskInsight.put("高取消风险", (double) cancelCount / totalCount > 0.35);
        riskInsight.put("取消率", totalCount == 0 ? 0.0 : (double) cancelCount / totalCount);
        riskInsight.put("夜间下单占比", nightOrderRatio);
        riskInsight.put("长住占比", totalCount == 0 ? 0.0 : (double) (stay6To10Day + stayOver10Day) / totalCount);
        riskInsight.put("建议", ((double) cancelCount / totalCount > 0.35)
                ? "建议强化确认金与二次确认策略，并在高取消时段进行客服干预"
                : "取消率处于健康区间，可继续优化复购与会员权益转化");

        result.put("TotalAppointCount", totalCount);
        result.put("TotalRevenue", totalRevenue);
        result.put("AvgOrderPrice", totalCount == 0 ? 0.0 : totalRevenue / totalCount);
        result.put("CancelRate", totalCount == 0 ? 0.0 : (double) cancelCount / totalCount);
        result.put("StatusSummary", statusSummary);
        result.put("SeasonSummary", seasonSummary);
        result.put("MonthSummary", monthSummary);
        result.put("HourSummary", hourCount);
        result.put("LeadTimeSummary", leadTimeSummary);
        result.put("StayDurationSummary", stayDurationSummary);
        result.put("RiskInsight", riskInsight);

        return result;
    }

}
