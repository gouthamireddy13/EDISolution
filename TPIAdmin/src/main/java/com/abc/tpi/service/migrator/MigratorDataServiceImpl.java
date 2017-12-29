package com.abc.tpi.service.migrator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.tpi.controller.MigrationControllerRest;
import com.abc.tpi.model.migrator.BusinessServiceApprovalReview;
import com.abc.tpi.model.migrator.ChangeType;
import com.abc.tpi.model.migrator.ObjectTracker;
import com.abc.tpi.model.migrator.ServiceApprovalReview;
import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;
import com.abc.tpi.model.poc.TpiManagedEntity;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.repository.BusinessServiceRepository;
import com.abc.tpi.repository.ObjectTrackerRepository;
import com.abc.tpi.repository.ServiceRepository;
import com.abc.tpi.repository.ServiceSubscriptionRepository;
import com.abc.tpi.service.ServiceSubscriptionService;

@Service
public class MigratorDataServiceImpl implements MigratorDataService {

	@Autowired
	ServiceSubscriptionRepository serviceSubscriptionRepository;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	BusinessServiceRepository businessServiceRepository;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;	

	@Autowired
	ObjectTrackerRepository objectTrackerRepository;
	
	@Override
	public List<ObjectTracker> getDataForSrId(String srId) {
		return getDataForSrIds(srId);		
	}
	
	private static final Logger logger = LogManager.getLogger(MigratorDataServiceImpl.class);
	
	/**
	 * Returns List of data structures that describe ServiceSubscription/Service/BusinessService objects that
	 * are candidates for migration based on SR ID and their migration history
	 */
	@Transactional(value=TxType.NOT_SUPPORTED)
	public List<ServiceSubscriptionApprovalReview> getApprovalReviewForSriId(String srId)
	{
		List<ServiceSubscriptionApprovalReview> result = new ArrayList<ServiceSubscriptionApprovalReview>();
		
		logger.debug("Invoked getApprovalReviewForSriId() for SRID " + srId);
		
		List<ObjectTracker> objectTrackers =  getDataForSrId(srId);
		
		
		for (ObjectTracker serviceSubscription: objectTrackers)
		{
			ServiceSubscriptionApprovalReview ssReview = new ServiceSubscriptionApprovalReview();
			ssReview.setDeleted(false);			
			
			ServiceSubscription ss = serviceSubscriptionService.findEntityUsingEntityGrpah(ServiceSubscription.class, serviceSubscription.getSourceId(), "ServiceSubscriptionEntity.graphPartnerServiceCat");
			
			ssReview.setName(ss.getPartner().getPartnerName() + "-" + ss.getServiceCategory().getName());
			ssReview.setObjectId(serviceSubscription.getSourceId());
			
			ssReview.setSrId("");
			
			ssReview.setToBeExported(serviceSubscription.isToBeExported());

			ObjectTracker trackHistory = objectTrackerRepository.findOneByClassNameAndSourceId(ServiceSubscription.class.getName(), serviceSubscription.getSourceId());
			
			if (trackHistory==null)
			{
				ssReview.setNew(true);
			}
			
			if (serviceSubscription.getObjectTrackers()!=null)
			{
				for (ObjectTracker serviceTracker: serviceSubscription.getObjectTrackers())
				{
					ServiceApprovalReview serviceReview = new ServiceApprovalReview();
					serviceReview.setDeleted(false);
					serviceReview.setObjectId(serviceTracker.getSourceId());
					serviceReview.setSrId(srId);
				
					com.abc.tpi.model.service.Service srvc = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.Service.class, serviceTracker.getSourceId(), "ServiceEntity.graphServiceId");			
				
					serviceReview.setName(srvc.getLightWellPartner().getProductionIsaID() + " - " + srvc.getLightWellPartner().getProductionGsId());
				
					serviceReview.setToBeExported(serviceTracker.isToBeExported());
				
					trackHistory = objectTrackerRepository.findOneByClassNameAndSourceId(com.abc.tpi.model.service.Service.class.getName(), serviceTracker.getSourceId());
				
					if (trackHistory==null)
					{
						serviceReview.setNew(true);
					}
					if (serviceTracker.getObjectTrackers()!=null)
					{		
					for (ObjectTracker businessServiceTracker: serviceTracker.getObjectTrackers())
					{
						BusinessServiceApprovalReview businessServiceReview = new BusinessServiceApprovalReview();
						businessServiceReview.setDeleted(false);
						businessServiceReview.setObjectId(businessServiceTracker.getSourceId());
						businessServiceReview.setSrId(srId);
						BusinessService businessServiceEnt = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.BusinessService.class,(long) businessServiceTracker.getSourceId(), "BusinessServiceEntity.graphServiceId");
						businessServiceReview.setName(businessServiceEnt.getServiceType().getBusinessServiceName());
						businessServiceReview.setToBeExported(businessServiceTracker.isToBeExported());
						
						trackHistory = objectTrackerRepository.findOneByClassNameAndSourceId(BusinessService.class.getName(), businessServiceTracker.getSourceId());
					
						if (trackHistory==null)
						{
							businessServiceReview.setNew(true);
						}
					
						serviceReview.addBussinessServiceReview(businessServiceReview);
					}
					}
					ssReview.addServiceReview(serviceReview);
				}
				result.add(ssReview);
			}
		}
		
		return result;
	}

	private List<ObjectTracker> getDataForSrIds(String srId)
	{
		//1. Get list of ServiceSubscriptions that need to be migrated;
		//2. Get list of Services that might need to be migrated
		//3. Get list of BusinessServices that might need to be migrated.
		//4. Determine if the object has EVER been migrated (Note: on the receiving side, migration will be validated first)
		//5. Put it all together in a structure.
		
		logger.debug("Invoked getDataForSrIds() for SRID " + srId);
		
		List<Long> serviceSubscriptionIds = serviceSubscriptionRepository.namedFindAllNotMigratedServiceSubscription();

		List<Long> serviceIds = serviceRepository.namedFindNotMigratedServicesBySrId(srId);
		
		List<Long> businessServicesIds = businessServiceRepository.namedFindNotMigratedBusinessServicesBySrId(srId);
		
		Map<Long,ObjectTracker> serviceSubscrptionMap = new HashMap<Long,ObjectTracker>();
		Map<Long,Map<Long,ObjectTracker>> serviceMap = new HashMap<Long,Map<Long,ObjectTracker>>();
		Map<Long,Map<Long,ObjectTracker>> businessServiceMap = new HashMap<Long,Map<Long,ObjectTracker>>();
		
		//populate service subscription map		

		for (Long id: serviceSubscriptionIds)
		{
			logger.debug("Adding new SubscriptionService entity to map. SS ID: " + id);
			ServiceSubscription ss = serviceSubscriptionService.findServiceSubscriptionById(id);						
			addServiceSubscriptionToMap(serviceSubscrptionMap,ss,true);
		}

		//populate service map
		for (Long id: serviceIds)
		{
			logger.debug("Processing Service entity : " + id);
			com.abc.tpi.model.service.Service service = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.Service.class, id, "ServiceEntity.graphServiceId");
			
			if (service !=null)
			{
				Long ssId = service.getServiceSubscription().getId();				

				ObjectTracker ssOt = serviceSubscrptionMap.get(ssId);
				
				if (ssOt==null)
				{
					logger.debug("Adding PARENT SubscriptionService entity to map. SS ID: " + ssId);
					
					ServiceSubscription ss = serviceSubscriptionService.findServiceSubscriptionById(ssId);
					addServiceSubscriptionToMap(serviceSubscrptionMap,ss,false);
				}
				
				if (serviceMap.get(ssId)==null || serviceMap.get(ssId).get(id)==null)
				{
					logger.debug("Adding Service entity to map. SS ID: " + id);
					addServiceEntityToMap(serviceMap,ssId,ServiceSubscription.class.getName(),service,true);
				}
			}			
		}
		
		//populate business service map
		for (Long bsId : businessServicesIds)
		{
			logger.debug("Processing Business Service entity : " + bsId);
			BusinessService businessService = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.BusinessService.class, bsId, "BusinessServiceEntity.graphServiceId");
			Long ssId = null;
			Long serviceId = null;
			
			if (businessService != null)
			{
				serviceId = businessService.getService().getId();
				
				ssId = businessService.getService().getServiceSubscription().getId();
				
				ObjectTracker ssOt = serviceSubscrptionMap.get(ssId);
				
				if (ssOt==null)
				{
					logger.debug("Adding PARENT SubscriptionService entity to map. SS ID: " + ssId);
					ServiceSubscription ss = serviceSubscriptionService.findServiceSubscriptionById(ssId);
					addServiceSubscriptionToMap(serviceSubscrptionMap,ss,false);
				}
				
				if (serviceMap.get(ssId)==null || serviceMap.get(ssId).get(serviceId)==null)
				{
					logger.debug("Adding PARENT Service entity to map. SS ID: " + serviceId);
					com.abc.tpi.model.service.Service service = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.Service.class, serviceId, "ServiceEntity.graphServiceId");
					addServiceEntityToMap(serviceMap,ssId,ServiceSubscription.class.getName(),service,false);
				}
				
				if (businessServiceMap.get(serviceId)==null || businessServiceMap.get(bsId)==null)
				{
					logger.debug("Adding Business Service entity to map. ID: " + bsId);
					addServiceEntityToMap(businessServiceMap,serviceId,com.abc.tpi.model.service.Service.class.getName(),businessService,true);
				}
			}
		}
		
		logger.debug("Building object graph for output");
		
		List<ObjectTracker> result = getObjectGraph(serviceSubscrptionMap,serviceMap,businessServiceMap);
		
		logger.debug("Exiting getDataForSrIds()");
		return result;
	}
	
	private void addServiceSubscriptionToMap(Map<Long,ObjectTracker> ssMap, ServiceSubscription ss, boolean toBeExported)
	{
		if (ssMap==null)
		{
			ssMap = new HashMap<Long,ObjectTracker>();			
		}
								
		ObjectTracker ot = new ObjectTracker();
		ot.setClassName(ServiceSubscription.class.getName());
		ot.setParentClassName(null);
		ot.setSourceId(ss.getId());
		ot.setSourceVersionNum(ss.getVersionNum());		
		ot.setDestId(0);
		ot.setDestVersionNum(0);
		ot.setToBeExported(toBeExported);
		ssMap.put(ss.getId(), ot);
		
	}
	
	private <T extends TpiManagedEntity> void addServiceEntityToMap(Map<Long,Map<Long,ObjectTracker>> sMap, long parentId, String parentClassName, T serviceEntity,  boolean toBeExported)
	{
		if (sMap==null)
		{
			sMap = new HashMap<Long,Map<Long,ObjectTracker>>();		
		}
		
		ObjectTracker oServiceTracker = new ObjectTracker();
		
		oServiceTracker.setClassName(BusinessService.class.getName());
		oServiceTracker.setSourceId(serviceEntity.getId());
		oServiceTracker.setParentClassName(parentClassName);
		oServiceTracker.setParentId(parentId);
		oServiceTracker.setSourceVersionNum(serviceEntity.getVersionNum());
		oServiceTracker.setToBeExported(toBeExported);
		
		Map<Long,ObjectTracker> serviceMap = sMap.get(parentId);
		if (serviceMap==null)
		{
			serviceMap = new HashMap<Long,ObjectTracker>();			
			sMap.put(parentId, serviceMap);
		}
		
		serviceMap.put(serviceEntity.getId(), oServiceTracker);
	}
	
	private List<ObjectTracker> getObjectGraph(Map<Long,ObjectTracker> ssMap,Map<Long,Map<Long,ObjectTracker>> serviceMap,Map<Long,Map<Long,ObjectTracker>> bsMap)
	{
		List<ObjectTracker> result = new ArrayList<ObjectTracker>();
		
		for (Long ssId: ssMap.keySet())
		{
			ObjectTracker ssTracker = ssMap.get(ssId);			
			Map<Long,ObjectTracker> services = serviceMap.get(ssId);
			
			if (services!=null)
			{
				for (Long sId: services.keySet())
				{
					ObjectTracker service = services.get(sId);
					ssTracker.addObjectTracker(service);
					Map<Long,ObjectTracker> businessServices = bsMap.get(service.getSourceId());
					
					if (businessServices!=null)
					{
						for (Long bsId: businessServices.keySet())
						{
							ObjectTracker businessService = businessServices.get(bsId);
							service.addObjectTracker(businessService);
						}
					}
				}
			}
			result.add(ssTracker);
		}		
		return result;
	}
}
