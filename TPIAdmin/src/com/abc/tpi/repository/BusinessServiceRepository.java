package com.abc.tpi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;
import com.abc.tpi.model.service.BusinessService;

@Repository("businessServiceRepository")
public interface BusinessServiceRepository extends JpaRepository<BusinessService, Long> {
	Collection<BusinessService> namedFindBusinessServicesByServiceId(@Param("serviceId") long serviceId);
	Collection<BusinessService> namedFindBusinessServicesByServiceSubscriptionAndServiceId(@Param("subscriptionId") long subscriptionId, @Param("serviceId") long serviceId);
	List<PartnerSubscriptionRecord> namedInEnvelopeByServiceSubscriptionId(@Param("subscriptionId") long subscriptionId);
	List<PartnerSubscriptionRecord> namedInEnvelopeByPartnerName(@Param("partnerName") String partnerName);
	List<Object> namedGenericInEnvelopeByServiceSubscriptionId(@Param("subscriptionId") long subscriptionId);
	List<PartnerSubscriptionRecord> namedInEnvelopeBySrId(@Param("srId") String srId);
	List<PartnerSubscriptionRecord> namedInEnvelopeBySrIdBusinessServiceIds(@Param ("srId") String srId, @Param ("ids") List<Long> ids);
	List<Long> namedFindAllNotMigratedBusinessServices();
	List<Long> namedFindNotMigratedBusinessServicesBySrId(@Param ("srId") String srId);
	BusinessService namedFindBusinessServicesByServiceSubscriptionAndServiceIdAndId(@Param("subscriptionId") long subscriptionId, @Param("serviceId") long serviceId, @Param("bsId") long bsId);
	Integer namedFindBusinessServicesVersionNumByServiceId(@Param("serviceId") long serviceId);
	
}
