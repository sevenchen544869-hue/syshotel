package com.example.web.controller;
import com.example.web.enums.*;
import com.example.web.tools.dto.PagedResult;
import com.example.web.tools.dto.SelectResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@RestController
@RequestMapping("/Select")
public class SelectController {

   @RequestMapping(value = "/RoleType",method = RequestMethod.POST)
    public PagedResult<SelectResult> RoleType(){

        List<SelectResult> rs = Arrays.stream(RoleTypeEnum.values()).map(n -> new SelectResult(n.toString(), n.name(), n.index() + "", "")).toList();
        return PagedResult.GetInstance(rs,rs.stream().count());
    }
     /**
     *预约状态枚举接口
     */
    @RequestMapping(value = "/AppointStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> AppointStatusEnum() {

       var rs=Arrays.stream(AppointStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *早餐类型枚举接口
     */
    @RequestMapping(value = "/BreakfastTypeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> BreakfastTypeEnum() {

       var rs=Arrays.stream(BreakfastTypeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *特色房型枚举接口
     */
    @RequestMapping(value = "/FeaturedRoomShapeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> FeaturedRoomShapeEnum() {

       var rs=Arrays.stream(FeaturedRoomShapeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *房间状态枚举接口
     */
    @RequestMapping(value = "/RoomStatusEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> RoomStatusEnum() {

       var rs=Arrays.stream(RoomStatusEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
     /**
     *房型枚举接口
     */
    @RequestMapping(value = "/RoomShapeEnum", method = RequestMethod.POST)
    public PagedResult<SelectResult> RoomShapeEnum() {

       var rs=Arrays.stream(RoomShapeEnum.values()).map(n->new SelectResult(n.toString(),n.name(),Integer.toString(n.index()),"")).toList();
       return PagedResult.GetInstance(rs,rs.stream().count());
    }
  
      
    
}
