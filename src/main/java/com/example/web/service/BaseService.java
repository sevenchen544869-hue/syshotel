package com.example.web.service;

import com.example.web.dto.RoomMatchDto;
import com.example.web.dto.query.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

/**
 * 放置通用快速基础逻辑的Service接口的定义清单
 */
public interface BaseService {

        /**
         * 得到房间选择时间段的价格
         */
        public Double GetRoomPriceByDateList(Integer hotelId, Integer roomId, LocalDateTime checkInDate,
                        LocalDateTime checkOutDate, Boolean isIgnoreFavourable);

        /**
         * 得到房间选择某天的价格
         */
        public Double GetRoomPriceByDate(Integer hotelId, Integer roomId, LocalDateTime date,
                        Boolean isIgnoreFavourable);

        /**
         * 得到房间剩余数量
         * 
         */
        public Long GetRemainingRoomNumber(Integer hotelId, Integer roomId, LocalDateTime checkInDate,
                        LocalDateTime checkOutDate);

        /**
         * 得到房间使用情况
         */
        public RoomMatchDto GetRoomRoomMatch(Integer hotelId, Integer roomId, Integer roomDetId, LocalDateTime date);

        /**
         * 根据地址获取对应的酒店ids
         */
        public List<Integer> GetHotelIdsByAddress(String address);

        public HashMap<String, Object> HotelDataAnalysis(HotelDataAnalysisQueryInput input);

        public HashMap<String, Object> SynthesizeStatistics(HotelDataAnalysisQueryInput input);

}
