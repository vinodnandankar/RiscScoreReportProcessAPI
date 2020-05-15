package com.barclays.automation.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.model.DataSheet;

@Service
public class RiskScoreReportPAPIService {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public Map<String, Object> getRiskScoreDetails() {

		Predicate<DataSheet> pendingRamlReviewStatusPredicate = api -> "Pending".equalsIgnoreCase(api.getRamlReviewStatus());
		Predicate<DataSheet> pendingVeracodeStatusPredicate = api -> "Pending".equalsIgnoreCase(api.getVeracodeStatus());
		Predicate<DataSheet> pendingPenTestStatusPredicate = api -> "Pending".equalsIgnoreCase(api.getPenTestStatus());
		Predicate<DataSheet> veracodeSlaBreachPredicate = api -> "SLA Breached".equalsIgnoreCase(api.getVeracodeSlaBreach());
		Predicate<DataSheet> penTestSlaBreachPredicate = api -> "SLA Breached".equalsIgnoreCase(api.getPenTestSlaBreach());
		
		Predicate<DataSheet> externalAPITypePredicate = api -> "External".equalsIgnoreCase(api.getApiType());
		Predicate<DataSheet> internalAPITypePredicate = api -> "Internal".equalsIgnoreCase(api.getApiType());

		Predicate<DataSheet> crticalRiskClassificationPredicate = api -> "Critical".equalsIgnoreCase(api.getApiRiskClassificatin());
		Predicate<DataSheet> highRiskClassificationPredicate = api -> "High".equalsIgnoreCase(api.getApiRiskClassificatin());
		Predicate<DataSheet> mediumRiskClassificationPredicate = api -> "Medium".equalsIgnoreCase(api.getApiRiskClassificatin());
		Predicate<DataSheet> lowRiskClassificationPredicate = api -> "Low".equalsIgnoreCase(api.getApiRiskClassificatin());
		
		
		Map<String, Object> response = new HashMap<>();

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		List<DataSheet> responseRiskScoreDetails = restTemplate()
				.exchange("http://localhost:9292/upload", HttpMethod.POST, entity, new ParameterizedTypeReference<List<DataSheet>>() {}).getBody();
		response.put("RiskScoreDetails", responseRiskScoreDetails);

		Supplier<Stream<DataSheet>> extenalAPITypeStream = () -> responseRiskScoreDetails.parallelStream().filter(externalAPITypePredicate);
		Supplier<Stream<DataSheet>> internalAPITypeStream = () -> responseRiskScoreDetails.parallelStream().filter(internalAPITypePredicate);

		Map<String, Object> externalAPISummaryMap = new HashMap<>();
		externalAPISummaryMap.put("reportDate", new Date());
		externalAPISummaryMap.put("totalVulnerabilities", responseRiskScoreDetails.parallelStream().
				filter(pendingRamlReviewStatusPredicate.or(pendingVeracodeStatusPredicate).or(pendingPenTestStatusPredicate).or(veracodeSlaBreachPredicate).or(penTestSlaBreachPredicate)).count());
		externalAPISummaryMap.put("extApiPenTestPending", extenalAPITypeStream.get().filter(pendingPenTestStatusPredicate).count());
		externalAPISummaryMap.put("extApiVeracodeScanPending", extenalAPITypeStream.get().filter(pendingVeracodeStatusPredicate).count());
		externalAPISummaryMap.put("extApiRamlReviewPending", extenalAPITypeStream.get().filter(pendingRamlReviewStatusPredicate).count());
		externalAPISummaryMap.put("intApiPenTestPending", internalAPITypeStream.get().filter(pendingPenTestStatusPredicate).count());
		externalAPISummaryMap.put("intApiVeracodeScanPending", internalAPITypeStream.get().filter(pendingVeracodeStatusPredicate).count());
		externalAPISummaryMap.put("intApiRamlReviewPending", internalAPITypeStream.get().filter(pendingRamlReviewStatusPredicate).count());
//		externalAPISummaryMap.put("totalExternalApiCount",extenalAPITypeStream.get().filter(externalAPITypePredicate).count());
//		externalAPISummaryMap.put("totalInternalApiTypeCount", internalAPITypeStream.get().filter(internalAPITypePredicate).count());

		response.put("externalAPISummaryMap", new ArrayList<>(Arrays.asList(externalAPISummaryMap)));

		Map<String, Long> holisticTableMap = new HashMap<>();

		holisticTableMap.put("extCriPendingRamlReview", extenalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("extHighPendingRamlReview", extenalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("extMedPendingRamlReview", extenalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("extLowPendingRamlReview", extenalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());

		holisticTableMap.put("extCriPendingVeracodeScan", extenalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("extHighPendingVeracodeScan", extenalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("extMedPendingVeracodeScan", extenalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("extLowPendingVeracodeScan", extenalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());

		holisticTableMap.put("extCriPendingPenTesting", extenalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("extHighPendingPenTesting", extenalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("extMedPendingPenTesting", extenalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("extLowPendingPenTesting", extenalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());

		holisticTableMap.put("extCriVeracodeSLABreach", extenalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("extHighVeracodeSLABreach", extenalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("extMedVeracodeSLABreach", extenalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("extLowVeracodeSLABreach", extenalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());

		holisticTableMap.put("extCriPenTestSLABreach", extenalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("extHighPenTestSLABreach", extenalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("extMedPenTestSLABreach", extenalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("extLowPenTestSLABreach", extenalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());

		
		holisticTableMap.put("intCriPendingRamlReview", internalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("intHighPendingRamlReview", internalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("intMedPendingRamlReview", internalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());
		holisticTableMap.put("intLowPendingRamlReview", internalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingRamlReviewStatusPredicate).count());

		holisticTableMap.put("intCriPendingVeracodeScan", internalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("intHighPendingVeracodeScan", internalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("intMedPendingVeracodeScan", internalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());
		holisticTableMap.put("intLowPendingVeracodeScan", internalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingVeracodeStatusPredicate).count());

		holisticTableMap.put("intCriPendingPenTesting", internalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("intHighPendingPenTesting", internalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("intMedPendingPenTesting", internalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());
		holisticTableMap.put("intLowPendingPenTesting", internalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(pendingPenTestStatusPredicate).count());

		holisticTableMap.put("intCriVeracodeSLABreach", internalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("intHighVeracodeSLABreach", internalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("intMedVeracodeSLABreach", internalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());
		holisticTableMap.put("intLowVeracodeSLABreach", internalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(veracodeSlaBreachPredicate).count());

		holisticTableMap.put("intCriPenTestSLABreach", internalAPITypeStream.get().filter(crticalRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("intHighPenTestSLABreach", internalAPITypeStream.get().filter(highRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("intMedPenTestSLABreach", internalAPITypeStream.get().filter(mediumRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
		holisticTableMap.put("intLowPenTestSLABreach", internalAPITypeStream.get().filter(lowRiskClassificationPredicate).filter(penTestSlaBreachPredicate).count());
//		holisticTableMap.put("totalInternalApiTypeCount", internalAPITypeStream.get().filter(internalAPITypePredicate).count());

		response.put("holisticTableMap", new ArrayList<>(Arrays.asList(holisticTableMap)));

		
		Map<String, Object> overallSecurityRiskMap = new HashMap<>();
		overallSecurityRiskMap.put("noRiskCount", responseRiskScoreDetails.parallelStream().filter(api -> "No Risk".equalsIgnoreCase(api.getOverallRiskClassification())).count());
		overallSecurityRiskMap.put("lowRiskCount", responseRiskScoreDetails.parallelStream().filter(api -> "Low Risk".equalsIgnoreCase(api.getOverallRiskClassification())).count());
		overallSecurityRiskMap.put("mediumRiskCount", responseRiskScoreDetails.parallelStream().filter(api -> "Medium Risk".equalsIgnoreCase(api.getOverallRiskClassification())).count());
		overallSecurityRiskMap.put("highRiskCount", responseRiskScoreDetails.parallelStream().filter(api -> "High Risk".equalsIgnoreCase(api.getOverallRiskClassification())).count());
		overallSecurityRiskMap.put("criticalRiskCount", responseRiskScoreDetails.parallelStream().filter(api -> "Critical Risk".equalsIgnoreCase(api.getOverallRiskClassification())).count());

		response.put("overallSecurityRiskMap", new ArrayList<>(Arrays.asList(overallSecurityRiskMap)));

		System.out.println(response);

		return response;
	}
	
	

}
