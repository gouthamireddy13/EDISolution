package com.abc.tpi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;
import com.abc.tpi.model.service.BusinessServiceW;

@Repository("businessServiceRWepository")
public interface BusinessServiceWRepository extends JpaRepository<BusinessServiceW, Long> {
	Collection<BusinessServiceW> namedFindBusinessServicesByServiceId(@Param("serviceId") long serviceId);
	Collection<BusinessServiceW> namedFindBusinessServicesByServiceSubscriptionAndServiceId(@Param("subscriptionId") long subscriptionId, @Param("serviceId") long serviceId);
	List<PartnerSubscriptionRecord> namedInEnvelopeByServiceSubscriptionId(@Param("subscriptionId") long subscriptionId);	
	List<Object> namedGenericInEnvelopeByServiceSubscriptionId(@Param("subscriptionId") long subscriptionId);
	List<PartnerSubscriptionRecord> namedInEnvelopeBySrId(@Param("srId") String srId);
	List<PartnerSubscriptionRecord> namedInEnvelopeBySrIdBusinessServiceIds(@Param ("srId") String srId, @Param ("ids") List<Long> ids);
	
}
