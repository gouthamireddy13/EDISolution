package com.abc.tpi.model.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(name="serviceSubscriptionListView", types={ServiceSubscription.class})
public interface ServiceSubscriptionListViewProjection {
	
	Long getId();
	
	@Value("#{target.getServiceCategory().getName()}")
	String getServiceCategoryName();
	
	@Value("#{target.getPartner().getPartnerName()}")
	String getPartnerName();
	
}
