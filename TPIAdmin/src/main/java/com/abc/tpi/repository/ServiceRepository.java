package com.abc.tpi.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.abc.tpi.model.service.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> 
{
	List<Long> namedFindAllNotMigratedServices();
	List<Long> namedFindNotMigratedServicesBySrId(@Param ("srId") String srId);
	Collection<Long> namedGetBusinessServicesForServiceById(@Param ("sId") long sId);
	Service namedFindByServiceSubscriptionIdAndId(@Param ("ssId") long ssId,@Param ("sId") long sId);
	Integer namedFindVersionNumById(@Param ("sId") long sId);
	//Add by Arindam Sikdar
	Collection<Long> namedGetServiceForBusinessServiceById(@Param ("bsId") long bsId);
}
