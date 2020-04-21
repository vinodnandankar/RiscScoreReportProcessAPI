package com.barclays.automation.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barclays.automation.service.RiskScoreReportPAPIService;


@RestController
public class RiskScoreReportPAPIController {
	
	public final RiskScoreReportPAPIService riskScoreReportPAPIService;

	public RiskScoreReportPAPIController(RiskScoreReportPAPIService riskScoreReportPAPIService) {
		this.riskScoreReportPAPIService=riskScoreReportPAPIService;
	}

	
	@GetMapping("/getRiskScoreDetails")
	public Map<String,Object> getRiskScoreDetails() throws Exception {
		return riskScoreReportPAPIService.getRiskScoreDetails();

	}
}
