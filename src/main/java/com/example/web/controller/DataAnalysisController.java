package com.example.web.controller;

import com.example.web.dto.query.AdminHotelDataAnalysisQueryInput;
import com.example.web.dto.query.HotelDataAnalysisQueryInput;
import com.example.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController()
@RequestMapping("/DataAnalysis")
public class DataAnalysisController {
    @Autowired()
    private BaseService BaseService;

    @RequestMapping(value = "/HotelDataAnalysis", method = RequestMethod.POST)
    public HashMap<String, Object> HotelDataAnalysis(@RequestBody HotelDataAnalysisQueryInput input) {
        return BaseService.HotelDataAnalysis(input);
    }

    @RequestMapping(value = "/SynthesizeStatistics", method = RequestMethod.POST)
    public HashMap<String, Object> SynthesizeStatistics(@RequestBody HotelDataAnalysisQueryInput input) {
        return BaseService.SynthesizeStatistics(input);
    }
}
