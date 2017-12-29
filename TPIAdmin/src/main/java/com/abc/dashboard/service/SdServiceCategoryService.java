package com.abc.dashboard.service;

import java.util.List;

import com.abc.dashboard.model.SdBusinessService;
import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;

public interface SdServiceCategoryService 
{

	//SdServiceCategoryDef operations
	SdServiceCategoryDef saveSdServiceCategoryDef(SdServiceCategoryDef sdServiceCategoryDef);
	<T> T findSdServiceCategoryByName(String name,Class<T> entityCls);
	<T> T findSdServiceCategoryByCategoryId(int categoryId,Class<T> entityCls);
	SdServiceCategoryDef findSdServiceCategoryById(long id);
	<T> List<T> getAllSdServiceCategoryDefs(Class<T> entityCls);
	void deleteSdServiceCategoryDef(long id);
	void deleteSdServiceCategoryDef(SdServiceCategoryDef sdServiceCategoryDef);
	<T> List<T> getServiceCategoriesWithPartnerSubscription(Class<T> entityCls);
	SdServiceCategoryDef findOneSdServiceCategoryByCategoryId(int categoryId);
	SdServiceCategoryDef findOneSdServiceCategoryByServiceCategory(String serviceCategoryName);
	
	//SdServiceAccess operations
	SdServiceAccess saveSdServiceAccess(SdServiceAccess sdServiceAccess);
	<T> List<T> getAllSdServiceAccess(Class<T> entityCls);
	<T> T getSdServiceAccessById(long id, Class<T> entityCls);
	void deleteSdServiceAccessDef(SdServiceAccess sdServiceAccess);
	void deleteSdServiceAccessDef(long id);
	SdServiceAccess findOneBySourceAndDestinationIdsIgnoreCase(String sourceId, String destinationId);
	SdServiceAccess findOneBySrceAndDestIdsAndSrvcPreambleAndSrvcCatDef(String sourceId, String destinationId, Long serviceCategoryDefId, String servicePreamble, Long serviceTypeId );
	
	SdServiceAccess saveSdServiceAccessWithPCCEInsert(SdServiceAccess sdServiceAccess);
	
	//SdBusinessService operations
	List<SdBusinessService> getAllSdBusinessServices();
	<T> List<T> getAllSdBusinessServices(Class<T> entityCls);
	SdBusinessService findBusinessServiceById(long id);
	<T> T findBusinessServiceById(long id,Class<T> type); 
	<T> T findBusinessServiceByServiceKey(String serviceKey,Class<T> type);
	SdBusinessService saveSdBusinessService(SdBusinessService sdBusinessService);
	
	SdBusinessService saveSdBusinessServiceWithPCCEInsert(SdBusinessService sdBusinessService);
	
	void deleteSdBusinessService(SdBusinessService sdBusinessService);
	void deleteSdBusinessService(long id);
	
	List<LightWellPartner> getLightWellPartnersWithSdServiceCategoryMembership();
	
	

}
