package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import com.barclays.automation.util.JsonDateDeserializer;
import com.barclays.automation.util.JsonDateSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class DataSheet implements Serializable{

	private static final long serialVersionUID = -1275414045316392907L;
	
	private int srNo;
	private String apiVersion;
	private String apiName;
	private String apiType;
	private String apiRiskClassificatin;
	private String ramlReviewStatus;
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
	private Date ramlReviewDate;
	private String veracodeStatus;
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
	private Date veracodeDate;
	private String penTestStatus;
	@JsonDeserialize(using = JsonDateDeserializer.class)
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonFormat(pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
	private Date penTestDate;
	private String veracodeSlaBreach;
	private String penTestSlaBreach;
	private String ramlReviewPending;
	private int riskScore;
	private String overallRiskClassification;
	private String transactionCycle;
	
	public DataSheet() {
	}

	public DataSheet(int srNo, String apiVersion, String apiName, String apiType, String apiRiskClassificatin,
			String ramlReviewStatus, Date ramlReviewDate, String veracodeStatus, Date veracodeDate,
			String penTestStatus, Date penTestDate, String veracodeSlaBreach, String penTestSlaBreach,
			String ramlReviewPending, int riskScore, String overallRiskClassification, String transactionCycle) {
		super();
		this.srNo = srNo;
		this.apiVersion = apiVersion;
		this.apiName = apiName;
		this.apiType = apiType;
		this.apiRiskClassificatin = apiRiskClassificatin;
		this.ramlReviewStatus = ramlReviewStatus;
		this.ramlReviewDate = ramlReviewDate;
		this.veracodeStatus = veracodeStatus;
		this.veracodeDate = veracodeDate;
		this.penTestStatus = penTestStatus;
		this.penTestDate = penTestDate;
		this.veracodeSlaBreach = veracodeSlaBreach;
		this.penTestSlaBreach = penTestSlaBreach;
		this.ramlReviewPending = ramlReviewPending;
		this.riskScore = riskScore;
		this.overallRiskClassification = overallRiskClassification;
		this.transactionCycle = transactionCycle;
	}



	@Override
	public String toString() {
		return "DataSheet [srNo=" + srNo + ", apiVersion=" + apiVersion + ", apiName=" + apiName + ", apiType="
				+ apiType + ", apiRiskClassificatin=" + apiRiskClassificatin + ", ramlReviewStatus=" + ramlReviewStatus
				+ ", ramlReviewDate=" + ramlReviewDate + ", veracodeStatus=" + veracodeStatus + ", veracodeDate="
				+ veracodeDate + ", penTestStatus=" + penTestStatus + ", penTestDate=" + penTestDate
				+ ", veracodeSlaBreach=" + veracodeSlaBreach + ", penTestSlaBreach=" + penTestSlaBreach
				+ ", ramlReviewPending=" + ramlReviewPending + ", riskScore=" + riskScore + ",,transactionCycle=\\\" + transactionCycle + \"]";
	}

	public int getSrNo() {
		return srNo;
	}

	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getApiVersion() {
		return apiVersion;
	}

	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public String getApiType() {
		return apiType;
	}

	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	public String getApiRiskClassificatin() {
		return apiRiskClassificatin;
	}

	public void setApiRiskClassificatin(String apiRiskClassificatin) {
		this.apiRiskClassificatin = apiRiskClassificatin;
	}

	public String getRamlReviewStatus() {
		return ramlReviewStatus;
	}

	public void setRamlReviewStatus(String ramlReviewStatus) {
		this.ramlReviewStatus = ramlReviewStatus;
	}

	public Date getRamlReviewDate() {
		return ramlReviewDate;
	}

	public void setRamlReviewDate(Date ramlReviewDate) {
		this.ramlReviewDate = ramlReviewDate;
	}

	public String getVeracodeStatus() {
		return veracodeStatus;
	}

	public void setVeracodeStatus(String veracodeStatus) {
		this.veracodeStatus = veracodeStatus;
	}

	public Date getVeracodeDate() {
		return veracodeDate;
	}

	public void setVeracodeDate(Date veracodeDate) {
		this.veracodeDate = veracodeDate;
	}

	public String getPenTestStatus() {
		return penTestStatus;
	}

	public void setPenTestStatus(String penTestStatus) {
		this.penTestStatus = penTestStatus;
	}

	public Date getPenTestDate() {
		return penTestDate;
	}

	public void setPenTestDate(Date penTestDate) {
		this.penTestDate = penTestDate;
	}

	public String getVeracodeSlaBreach() {
		return veracodeSlaBreach;
	}

	public void setVeracodeSlaBreach(String veracodeSlaBreach) {
		this.veracodeSlaBreach = veracodeSlaBreach;
	}

	public String getPenTestSlaBreach() {
		return penTestSlaBreach;
	}

	public void setPenTestSlaBreach(String penTestSlaBreach) {
		this.penTestSlaBreach = penTestSlaBreach;
	}

	public String getRamlReviewPending() {
		return ramlReviewPending;
	}

	public void setRamlReviewPending(String ramlReviewPending) {
		this.ramlReviewPending = ramlReviewPending;
	}

	public int getRiskScore() {
		return riskScore;
	}

	public void setRiskScore(int riskScore) {
		this.riskScore = riskScore;
	}

	public String getOverallRiskClassification() {
		return overallRiskClassification;
	}

	public void setOverAllRiskClassification(String overallRiskClassification) {
		this.overallRiskClassification = overallRiskClassification;
	}

	public String getTransactionCycle() {
		return transactionCycle;
	}

	public void setTransactionCycle(String transactionCycle) {
		this.transactionCycle = transactionCycle;
	}
	
	
}

