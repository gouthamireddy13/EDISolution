package com.abc.tpi.service.migrator;

import java.util.List;

import com.abc.tpi.model.migrator.ObjectTracker;
import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;

public interface MigratorDataService {
	List<ObjectTracker> getDataForSrId(String srId);
	List<ServiceSubscriptionApprovalReview> getApprovalReviewForSriId(String srId);
}
