package com.abc.dashboard.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * @author Manoj Kumar Dwari
 *
 */

@Entity
@Table(name="ABC_SERVICE_CATEGORY_LOOKUP",schema="PCCE")
@IdClass(PcceAbcBusinessServiceKey.class)
public class PcceAbcBusinessService implements Serializable {

	private static final long serialVersionUID = -6398488982476253203L;
	
	@Id
	@Column(name="SERVICE_KEY",nullable=true)
	private String serviceKey;
	
	@Id
	@Column(name="SERVICE_CATEGORY",nullable=true)
	private String serviceCategory;
	
	@Id
	@Column(name="CATEGORY_ID",nullable=true)
	private String categoryId;
	
	@Id
	@Column(name="SERVICE_LEVEL",nullable=true)
	private String serviceLevel;
	
	@Id
	@Column(name="SERVICE_LEVEL_ID",nullable=true)
	private String serviceLevelId;
	
	@Id
	@Column(name="BUSINESS_UNIT",nullable=true)
	private String businessUnit;
	
	@Id
	@Column(name="BUSINESS_SUB_UNIT",nullable=true)
	private String businessSubUnit;
	
	@Id
	@Column(name="SEVICE_PREAMBLE",nullable=true)
	private String servicePreamble;
	
	@Id
	@Column(name="TRANSACTION_TYPE",nullable=true)
	private String transactionType;
	
	@Id
	@Column(name="DIRECTION",nullable=true)
	private String direction;
	
	@Id
	@Column(name="SERVICE_TYPE",nullable=true)
	private String serviceType;
	
	@Id
	@Column(name="INTERCOMPANY_SENDTO_BU",nullable=true)
	private String intCompSendBu;
	
	@Id
	@Column(name="SERVICE_DESCRIPTION",nullable=true)
	private String serviceDesc;
	
	
	
	
	public String getServiceKey() {
		return serviceKey;
	}




	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}




	public String getServiceCategory() {
		return serviceCategory;
	}




	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}



	public String getCategoryId() {
		return categoryId;
	}




	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}




	public String getServiceLevel() {
		return serviceLevel;
	}




	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}




	public String getServiceLevelId() {
		return serviceLevelId;
	}




	public void setServiceLevelId(String serviceLevelId) {
		this.serviceLevelId = serviceLevelId;
	}




	public String getBusinessUnit() {
		return businessUnit;
	}




	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}




	public String getBusinessSubUnit() {
		return businessSubUnit;
	}




	public void setBusinessSubUnit(String businessSubUnit) {
		this.businessSubUnit = businessSubUnit;
	}




	public String getServicePreamble() {
		return servicePreamble;
	}




	public void setServicePreamble(String servicePreamble) {
		this.servicePreamble = servicePreamble;
	}




	public String getTransactionType() {
		return transactionType;
	}




	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}




	public String getDirection() {
		return direction;
	}




	public void setDirection(String direction) {
		this.direction = direction;
	}




	public String getServiceType() {
		return serviceType;
	}




	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}




	public String getIntCompSendBu() {
		return intCompSendBu;
	}




	public void setIntCompSendBu(String intCompSendBu) {
		this.intCompSendBu = intCompSendBu;
	}




	public String getServiceDesc() {
		return serviceDesc;
	}




	public void setServiceDesc(String serviceDesc) {
		this.serviceDesc = serviceDesc;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
