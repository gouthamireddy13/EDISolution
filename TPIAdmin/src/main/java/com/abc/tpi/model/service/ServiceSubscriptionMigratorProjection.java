package com.abc.tpi.model.service;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="serviceSubscriptionMigratorProjection", types={ServiceSubscription.class})
public interface ServiceSubscriptionMigratorProjection 
{
	Long getId();
	
	
}
