package com.abc.tpi.model.migrator;

import java.util.ArrayList;
import java.util.List;

public class ServiceSubscriptionApprovalReview extends ApprovalReview {

	List<ServiceApprovalReview> services;

	public List<ServiceApprovalReview> getServices() {
		return services;
	}
	
	public void addServiceReview(ServiceApprovalReview serviceReview) 
	{
		if (this.services==null)
		{
			services = new ArrayList<ServiceApprovalReview>();
		}
		
		services.add(serviceReview);		
	}

}
