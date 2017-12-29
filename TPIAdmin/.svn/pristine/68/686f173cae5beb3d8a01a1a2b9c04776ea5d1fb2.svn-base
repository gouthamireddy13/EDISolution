package com.abc.tpi.model.migrator;

import java.util.ArrayList;
import java.util.List;

public class ServiceApprovalReview extends ApprovalReview {
	List<BusinessServiceApprovalReview> businessServices;

	public List<BusinessServiceApprovalReview> getBusinessServices() {
		return businessServices;
	}

	public void addBussinessServiceReview(BusinessServiceApprovalReview businessServiceReview) 
	{
		if (this.businessServices==null)
		{
			businessServices = new ArrayList<BusinessServiceApprovalReview>();
		}
		
		businessServices.add(businessServiceReview);
		
	}
}
