package com.abc.dashboard.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdServiceType;
import com.abc.tpi.model.service.ServiceType;

@Repository("sdServiceAccessRepository")
public interface SdServiceAccessRepository extends JpaRepository<SdServiceAccess, Long> 
{
	<T> List<T> findAllBy(Class<T> type);
	<T> T findOneById(Class<T> type, long id);
	
	Set<SdServiceAccess> namedFindAllServiceAccess();
	<T> T namedFindSdServiceAccessBySrceIdAndDestIdAndSrvcCatDefIdAndSrvcPreambleAndSdSrvcType(@Param("sourceId") String sourceId,@Param("destinationId") String destinationId,@Param("serviceCategoryDefId") Long serviceCategoryDefId,@Param("servicePreamble") String servicePreamble,@Param("serviceTypeId") Long serviceTypeId, Class<T> type);
	
	SdServiceAccess findOneBySourceIdIgnoreCaseAndDestinationIdIgnoreCase(String sourceId, String destinationId);
	
	//SdServiceAccess findOneBySourceIdIgnoreCaseAndDestinationIdIgnoreCaseAndServiceCategoryDefAndServicePreambleAndSdServiceType(String sourceId, String destinationId, SdServiceCategoryDef serviceCategoryDef, String servicePreamble, SdServiceType serviceType);
}
