package com.abc.tpi.utils;

import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.tpp.LightWellPartner;

public class ABCIDReturnMsgBean {
	
	String returnMsg;
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	
	int returnFlag;
	public int getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(int returnFlag) {
		this.returnFlag = returnFlag;
	}
	
	ServiceCategory serviceCategory;
	public ServiceCategory getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(ServiceCategory serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	
	LightWellPartner lightWellPartner;
	public LightWellPartner getLightWellPartner() {
		return lightWellPartner;
	}
	public void setLightWellPartner(LightWellPartner lightWellPartner) {
		this.lightWellPartner = lightWellPartner;
	}
}
