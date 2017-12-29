package com.abc.tpi.service.migrator.client;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.tpi.model.migrator.BusinessServiceApprovalReview;
import com.abc.tpi.model.migrator.ObjectTracker;
import com.abc.tpi.model.migrator.ServiceApprovalReview;
import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;
import com.abc.tpi.model.migrator.ServiceSubscriptionEntityMigratorWrapper;
import com.abc.tpi.model.migrator.SubscriptionServiceObjectMigration;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.repository.BusinessServiceRepository;
import com.abc.tpi.repository.ObjectTrackerRepository;
import com.abc.tpi.repository.ServiceSubscriptionRepository;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.service.migrator.server.MigratorDataLoader;

@Service
public class MigratorClientServiceImpl implements MigratorClientService {

	@Autowired
	ObjectTrackerRepository objectTrackerRepository;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@Autowired
	ServiceSubscriptionRepository serviceSubcscriptionRepository;
	
	@Autowired
	BusinessServiceRepository businessServiceRepository;
	
	@Autowired
	MigratorDataLoader migratorDataLoader;
	
	public MigratorClientServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Migrates ServiceSubscription structure based on the approvals provided
	 */	
	@Override
	public void migrateServiceSubscription(List<ServiceSubscriptionApprovalReview> serviceApproval) throws Exception {
		
		List<SubscriptionServiceObjectMigration> payLoad = new ArrayList<SubscriptionServiceObjectMigration>(); 
		
		if (serviceApproval == null)
		{
			return;
		}

		for (ServiceSubscriptionApprovalReview serviceSubscriptionMeta : serviceApproval)
		{
			
			SubscriptionServiceObjectMigration meta = new SubscriptionServiceObjectMigration();			
			
			//get tracker for the ServiceSubscription
			ObjectTracker ssTracker = this.getObjectTrackerData(ServiceSubscription.class.getName(), serviceSubscriptionMeta.getObjectId());
			
			if (ssTracker == null)
			{
				ssTracker = new ObjectTracker();
				ssTracker.setClassName(ServiceSubscription.class.getName());
				ssTracker.setSourceId(serviceSubscriptionMeta.getObjectId());
			}

			meta.setMeta(ssTracker);												
						
			if (serviceSubscriptionMeta.isToBeExported())
			{
				meta.getMeta().setToBeExported(true);
			}
			else
			{
				meta.getMeta().setToBeExported(false);				
			}
			
			
			for (ServiceApprovalReview serviceMeta : serviceSubscriptionMeta.getServices())
			{
				ObjectTracker serviceTracker = this.getObjectTrackerData(com.abc.tpi.model.service.Service.class.getName(), serviceMeta.getObjectId());
				
				if (serviceTracker == null)
				{
					serviceTracker = new ObjectTracker();
					serviceTracker.setClassName(com.abc.tpi.model.service.Service.class.getName());
					serviceTracker.setSourceId(serviceMeta.getObjectId());
					serviceTracker.setParentId(serviceSubscriptionMeta.getObjectId());
					serviceTracker.setParentClassName(ServiceSubscription.class.getName());
				}

				if (serviceMeta.isToBeExported())
				{
					serviceTracker.setToBeExported(true);
				}
				else
				{
					serviceTracker.setToBeExported(false);				
				}
				
				meta.getMeta().addObjectTracker(serviceTracker);
				
				
				for (BusinessServiceApprovalReview businessServiceMeta: serviceMeta.getBusinessServices())
				{
					ObjectTracker businessServiceTracker = this.getObjectTrackerData(BusinessService.class.getName(), businessServiceMeta.getObjectId());
					if (businessServiceTracker == null)
					{
						businessServiceTracker = new ObjectTracker();
						businessServiceTracker.setClassName(BusinessService.class.getName());
						businessServiceTracker.setSourceId(businessServiceMeta.getObjectId());
						businessServiceTracker.setParentId(serviceMeta.getObjectId());
						businessServiceTracker.setParentClassName(com.abc.tpi.model.service.Service.class.getName());
					}

					if (serviceMeta.isToBeExported())
					{
						businessServiceTracker.setToBeExported(true);
					}
					else
					{
						businessServiceTracker.setToBeExported(false);				
					}				
					serviceTracker.addObjectTracker(businessServiceTracker);
				}				
			}			
			payLoad.add(populateServiceSubscriptionData(meta));
		}
	}
	
	private ObjectTracker getObjectTrackerData(String className, long objectId)
	{
		
		return objectTrackerRepository.findOneByClassNameAndSourceId(className, objectId);		
	}
	
	public SubscriptionServiceObjectMigration populateServiceSubscriptionData(SubscriptionServiceObjectMigration meta) throws Exception
	{
		ObjectTracker migrationMeta = meta.getMeta();
		
		Integer versionNum = serviceSubscriptionService.findServiceSubscriptionVersionById(meta.getMeta().getSourceId());
		
		ServiceSubscription ssCopy= null;
		
		if (versionNum!=null)
		{
			if (migrationMeta.isToBeExported())
			{
				ssCopy = cloneServiceSubscription(meta.getMeta().getSourceId());
			}
			else
			{
				ssCopy = new ServiceSubscription();				
			}
			
			migrationMeta.setSourceVersionNum(versionNum.intValue());
			
			for (ObjectTracker service : meta.getMeta().getObjectTrackers())
			{
				//find Service that need to be migrated
				com.abc.tpi.model.service.Service srvc = serviceSubscriptionService.findServiceByServiceIdForSubscription(meta.getMeta().getSourceId(),service.getSourceId());
				if (srvc==null)
				{
					throw new Exception("Service with SubscriptionService ID: " + meta.getMeta().getSourceId() + " and Service ID " + service.getSourceId() + " not found" );
				}
				service.setSourceVersionNum(srvc.getVersionNum());
				
				com.abc.tpi.model.service.Service copyService = null;
				
				if (!service.isToBeExported())
				{
					copyService = new com.abc.tpi.model.service.Service();
					ssCopy.addService(copyService);
				}
				else
				{
					copyService = cloneService(srvc);				
					ssCopy.addService(copyService);
				}

				for (ObjectTracker businessService:service.getObjectTrackers())
				{
					if (businessService.isToBeExported())
					{
						BusinessService bSrvc = serviceSubscriptionService.findBusinessServiceByIdForSubscription(meta.getMeta().getSourceId(),service.getSourceId(),businessService.getSourceId());
						businessService.setSourceVersionNum(bSrvc.getVersionNum());
						BusinessService bsCopy = getBusinessServiceCopy(bSrvc);
						copyService.addBusinessService(bsCopy);
					}
				}
			}
		}
		else
		{
			throw new Exception("Service Subscription with ID: " + meta.getMeta().getSourceId() + " not found" );
		}
		meta.setData(ssCopy);
		migratorDataLoader.importServiceSybscription(meta);
		return meta;
	}
	
	private ServiceSubscription cloneServiceSubscription(long ssId)
	{
		ServiceSubscription ss = serviceSubscriptionService.findEntityUsingEntityGrpah(ServiceSubscription.class,ssId, "ServiceSubscriptionEntity.graphPartnerServiceCat");
		return getServiceSubscriptionShallowCopy(ss);
	}
	
	//@Transactional
	private com.abc.tpi.model.service.Service cloneService(com.abc.tpi.model.service.Service original)
	{
		com.abc.tpi.model.service.Service s = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.Service.class,original.getId(), "ServiceEntity.graphMigrationReadyData");
		return  com.abc.tpi.model.service.Service.newMigrationInstance(s);
	}
	
	//@Transactional
	private ServiceSubscription getServiceSubscriptionShallowCopy(ServiceSubscription serviceSubscription)
	{
		return ServiceSubscription.newMigrationInstance(serviceSubscription);
	}
	
	//@Transactional
	private BusinessService getBusinessServiceCopy(BusinessService original)
	{
		BusinessService bs = serviceSubscriptionService.findEntityUsingEntityGrpah(com.abc.tpi.model.service.BusinessService.class,original.getId(), "BusinessServiceEntity.graphMigrationReadyData");
		//BusinessService bs = businessServiceRepository.findOne(original.getId());
		return BusinessService.newMigrationInstnace(bs);
	}
	
	
	public void migrateServiceSubscription2(List<ServiceSubscriptionApprovalReview> serviceApproval) throws Exception {
		
		List<SubscriptionServiceObjectMigration> payLoad = new ArrayList<SubscriptionServiceObjectMigration>(); 
		
		if (serviceApproval == null)
		{
			return;
		}

		removeNonExportableEntities(serviceApproval);
		
		
		for (ServiceSubscriptionApprovalReview serviceSubscriptionMeta : serviceApproval)
		{
			ServiceSubscription ss = new ServiceSubscription();
			ServiceSubscriptionEntityMigratorWrapper serviceSubscriptionWrapper;
			if (serviceSubscriptionMeta.isToBeExported())
			{
				ss = serviceSubscriptionService.findEntityUsingEntityGrpah(ServiceSubscription.class,serviceSubscriptionMeta.getObjectId(), "ServiceSubscriptionEntity.graphPartnerServiceCat");	
			}
			
			if (ss != null)
			{
				serviceSubscriptionWrapper = new ServiceSubscriptionEntityMigratorWrapper(ss);
				serviceSubscriptionWrapper.getTracker().setToBeExported(serviceSubscriptionMeta.isToBeExported());
			}
			
			
			//ServiceSubscriptionEntityMigratorWrapper serviceSubscription = new ServiceSubscriptionEntityMigratorWrapper();			
		}
	}
	
	public void removeNonExportableEntities(List<ServiceSubscriptionApprovalReview> originalList) 
	{
		
		if (originalList!=null)
		{
			for (Iterator<ServiceSubscriptionApprovalReview> ssIterator = originalList.iterator();ssIterator.hasNext();)
			{
				ServiceSubscriptionApprovalReview serviceSubscription = ssIterator.next();
			
				if (serviceSubscription.getServices()!=null)
				{
					for (Iterator<ServiceApprovalReview> serviceIterator = serviceSubscription.getServices().iterator();serviceIterator.hasNext();)
					{				
						ServiceApprovalReview service = serviceIterator.next();				
						//remove business services that are not to be exported
						if (service.getBusinessServices() !=null)
						{
							for (Iterator<BusinessServiceApprovalReview> businessServiceIterator = service.getBusinessServices().iterator();businessServiceIterator.hasNext();)
							{				
								BusinessServiceApprovalReview businessService = businessServiceIterator.next();
								if (!businessService.isToBeExported())
								{
									businessServiceIterator.remove();
								}
							}
						}
						
						if (service.isToBeExported()==false && (service.getBusinessServices()==null || service.getBusinessServices().isEmpty() ))
						{
							serviceIterator.remove();
						}
					}
				}
				if (serviceSubscription.isToBeExported()==false && (serviceSubscription.getServices()==null || serviceSubscription.getServices().isEmpty() ))
				{
					ssIterator.remove();
				}
			}
		}		
	}

}
