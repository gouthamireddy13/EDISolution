package com.abc.tpi.service.migrator.client;

import java.util.List;

import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;

public interface MigratorClientService {
	
	void migrateServiceSubscription(List<ServiceSubscriptionApprovalReview> serviceApproval) throws Exception;

}
