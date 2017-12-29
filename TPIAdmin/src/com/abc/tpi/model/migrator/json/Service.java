package com.abc.tpi.model.migrator.json;

import java.util.List;

public class Service extends ObjectState {
	List<BusinessService> businessServices;

	public List<BusinessService> getBusinessServices() {
		return businessServices;
	}

	public void setBusinessServices(List<BusinessService> businessServices) {
		this.businessServices = businessServices;
	}
}
