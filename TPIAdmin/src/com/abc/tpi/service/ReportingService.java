package com.abc.tpi.service;
import java.util.List;

import com.abc.tpi.model.reporting.LightWellPartnerRecord;
import com.abc.tpi.model.reporting.PartnerRecord;
import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;
import com.abc.tpi.model.reporting.TPPRecord;

public interface ReportingService {
	List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsByServiceSubscriptionId(long serviceSubscriptionId);
	List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsByPartnerName(String partnerName);
	List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsBySrId(String srId);
	List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsBySrIdBusinessSrvcId(String srId, List<Long> businessServiceIds);
	List<PartnerRecord> getPartnerRecordsByPartnerId(List<Long> prId);
	List<LightWellPartnerRecord> getLightWellIdentityForLwIds(long partnerId, List<Long>lwIds);
	List<TPPRecord> getTPPRecordsByTPPId(List<Long> tppId);
	
}