package com.abc.tpi.model.migrator;

import java.util.HashMap;
import java.util.Map;

import com.abc.tpi.model.service.BusinessService;

public class TpiEntityMapMigratorWrapper {

	Map<Long,com.abc.tpi.model.service.Service> serviceMap = new HashMap<Long,com.abc.tpi.model.service.Service>();
	Map<Long,BusinessService> businessServiceMap = new HashMap<Long,BusinessService>();
	
	ServiceSubscriptionEntityMigratorWrapper serviceSubscription;
	
	public TpiEntityMapMigratorWrapper(ServiceSubscriptionEntityMigratorWrapper serviceSubscription) 
	{
		this.serviceSubscription=serviceSubscription;
	}
	
	public void addServiceToMap(ServiceEntityMigratorWrapper serviceEntity)
	{
		
	}
	
	public void addBusinessServiceToMap(long serviceId,BusinessServiceEntityMigratorWrapper businessService)
	{
		
	}

}
