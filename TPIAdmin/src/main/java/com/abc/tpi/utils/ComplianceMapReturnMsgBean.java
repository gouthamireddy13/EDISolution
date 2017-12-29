package com.abc.tpi.utils;

import com.abc.tpi.model.service.ServiceType;

public class ComplianceMapReturnMsgBean {
	String returnMsg;
	int returnFlag;
	ServiceType serviceType;
	
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public int getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(int returnFlag) {
		this.returnFlag = returnFlag;
	}
	public ServiceType getServiceType() {
		return serviceType;
	}
	public void setServiceType(ServiceType serviceType) {
		this.serviceType = serviceType;
	}
	
	

}
