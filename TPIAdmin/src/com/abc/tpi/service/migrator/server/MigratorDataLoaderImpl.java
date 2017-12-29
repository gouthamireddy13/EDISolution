package com.abc.tpi.service.migrator.server;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.migrator.ObjectTracker;
import com.abc.tpi.model.migrator.SubscriptionServiceObjectMigration;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.PartnerService;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.service.TppService;
@Service
public class MigratorDataLoaderImpl implements MigratorDataLoader {

	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	TppService tppService;
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	public MigratorDataLoaderImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public ObjectTracker importServiceSybscription(SubscriptionServiceObjectMigration serviceSubscripitnData) throws Exception {

		if (serviceSubscripitnData==null)
		{
			return null;
		}
		
		validateData(serviceSubscripitnData);
		ServiceSubscription serviceSubscription = reconstructServiceSubscriptionObject(serviceSubscripitnData);
		serviceSubscriptionService.saveServiceSubscription(serviceSubscription);
		return null;
	}

	
	private void validateData(SubscriptionServiceObjectMigration serviceSubscriptionData) throws Exception
	{
		ObjectTracker manifest = serviceSubscriptionData.getMeta();
		
		validateServiceSubscriptionLevel(manifest);
		
		Collection<ObjectTracker> serviceManifests = manifest.getObjectTrackers();
		
		if (serviceManifests==null)
		{
			return;
		}
						
		for (ObjectTracker serviceManfiest:serviceManifests)
		{
			validateServiceLevel(serviceManfiest);
			
			Collection<ObjectTracker> bsManfiests = serviceManfiest.getObjectTrackers();
			
			if (bsManfiests!=null)
			{
				for (ObjectTracker bsManifest:bsManfiests)
				{
					validateBusinessServiceLevel(bsManifest);
				}
			}
		}

	}
	
	private ServiceSubscription reconstructServiceSubscriptionObject(SubscriptionServiceObjectMigration serviceSubscripitnData) throws Exception
	{

		ObjectTracker ssManifest = serviceSubscripitnData.getMeta();
		ServiceSubscription originalSS = serviceSubscripitnData.getData();
		ServiceSubscription destinationSS = null;
		
		if (ssManifest.getDestId()!=0)
		{
			destinationSS = serviceSubscriptionService.findServiceSubscriptionById(ssManifest.getDestId());
			if (destinationSS==null)
			{
				throw new Exception("ServiceSubscription with id " + ssManifest.getDestId() + " not found");
			}
			else if (ssManifest.isToBeExported())			
			{
				destinationSS = this.createNewServiceSubscription(originalSS);
			}
			else
			{
				return null;
			}
		}
		
		if (ssManifest.getObjectTrackers()!=null)
		{
			for (ObjectTracker serviceManifest:ssManifest.getObjectTrackers())
			{
				if (serviceManifest.isToBeExported())
				{
					com.abc.tpi.model.service.Service destService = null;
					
					com.abc.tpi.model.service.Service originalService = this.findServiceFromCollection(originalSS, serviceManifest.getSourceId());
					
					if (originalService==null)
					{
						throw new Exception ("Original Service with ID : " + serviceManifest.getSourceId() + "not found in the data for migration");
					}
					
					if (serviceManifest.getDestId()!=0)
					{
						destService = serviceSubscriptionService.findServiceByServiceIdForSubscription(ssManifest.getDestId(), serviceManifest.getDestId());
						if (destService==null)
						{
							throw new Exception ("Destination Service with ID : " + ssManifest.getDestId() + "not found for ServiceSubscription " + serviceManifest.getDestId());
						}
					}
					else
					{
						destService = new com.abc.tpi.model.service.Service();
						destinationSS.addService(destService);
					}
					destService = this.updateService(originalService,destService);
					
					//add business services to this service
					if (serviceManifest.getObjectTrackers()!=null)
					{
						for (ObjectTracker businessServiceManifest :serviceManifest.getObjectTrackers())
						{

							if (businessServiceManifest.isToBeExported())
							{
							BusinessService originalBS = findBusinessServiceFromCollection(originalService,businessServiceManifest.getSourceId());
							
								if (originalBS==null)
								{
									throw new Exception ("Original Business Service with ID : " + businessServiceManifest.getSourceId() + "not found in the data for migration");
								}
								
								BusinessService destinationBS = null;
								
								if (businessServiceManifest.getDestId()!=0)
								{
									destinationBS = serviceSubscriptionService.findBusinessServiceByIdForSubscription(ssManifest.getDestId(), serviceManifest.getDestId(), businessServiceManifest.getDestId());
									if (destinationBS==null)
									{
										throw new Exception ("Destination Business Service with ID : " + businessServiceManifest.getDestId() + "not found for ServiceSubscription " + ssManifest.getDestId());
									}
									
								}
								else
								{
									destinationBS = new BusinessService();
									destService.addBusinessService(destinationBS);
								}
								
								destinationBS = updateBusinessService(originalBS,destinationBS);
							}

						}
					}					
				}
			}
		}
		return null;
	}
	
	private void validateServiceSubscriptionLevel(ObjectTracker manifest) throws Exception
	{
		if (manifest==null)
		{
			throw new Exception("No migration metadat was provided");
		}
		
		if (manifest.isToBeExported()==false && manifest.getDestId()==0)
		{
			throw new Exception("ServiceSubscription must be exported first");
		}

		if (manifest.getClassName()!=ServiceSubscription.class.getName())
		{
			throw new Exception("ServiceSubscription type data expected. Received: " + manifest.getClassName());			
		}		
		
		if (manifest.getDestId()!=0)
		{
			Integer ss = serviceSubscriptionService.findServiceSubscriptionVersionById(manifest.getDestId());
			if (ss==null)
			{
				throw new Exception("ServiceSubscription with id " + manifest.getDestId() + " not found");				
			}
			
			if ((long)manifest.getDestVersionNum()!=ss)
			{
				throw new Exception("ServiceSubscription Version did not match expected " + ss + ". Received: " + manifest.getDestVersionNum());
			}			
		}
	}
	
	private void validateServiceLevel(ObjectTracker manifest) throws Exception
	{
		if (manifest==null)
		{
			return;
		}
		
		if (manifest.getClassName()!=com.abc.tpi.model.service.Service.class.getName())
		{
			throw new Exception("Service type data expected. Received: " + manifest.getClassName());			
		}		
		
		if (manifest.getDestId()!=0)
		{
			Integer ss = serviceSubscriptionService.findServiceVersionById(manifest.getDestId());
			if (ss==null)
			{
				throw new Exception("Service for Service id " + manifest.getDestId() + " not found");				
			}
			
			if ((long)manifest.getDestVersionNum()!=ss)
				
			{
				throw new Exception("Service Version did not match expected " + ss + ". Received: " + manifest.getDestVersionNum());
			}			
		}
	}
	
	private void validateBusinessServiceLevel(ObjectTracker manifest) throws Exception
	{
		if (manifest==null)
		{
			return;
		}
		
		if (manifest.getClassName()!=BusinessService.class.getName())
		{
			throw new Exception("Business Service type data expected. Received: " + manifest.getClassName());			
		}		
		
		if (manifest.getDestId()!=0)
		{
			Integer ss = serviceSubscriptionService.findBusinessServiceVersionById(manifest.getDestId());
			if (ss==null)
			{
				throw new Exception("Business Service for Service id " + manifest.getDestId() + " not found");				
			}
			
			if ((long)manifest.getDestVersionNum()!=ss)
				
			{
				throw new Exception("Business Service Version did not match expected " + ss + ". Received: " + manifest.getDestVersionNum());
			}			
		}
	}
	
	private ServiceSubscription createNewServiceSubscription(ServiceSubscription source) throws Exception
	{
		ServiceSubscription destinationSS = new ServiceSubscription();
		
		String partnerName = source.getPartner().getPartnerName();
		if (partnerName==null)
		{
			throw new Exception("Partner Name must not be NULL");
		}
		
		String serviceCategory = source.getServiceCategory().getName();
		if (serviceCategory==null)
		{
			throw new Exception("Service Category Name must not be NULL");
		}
		
		Partner partner = partnerService.findPartnerByUniqueName(partnerName);		
		if (partner==null)
		{
			throw new Exception("Partner with Name= " + partnerName + " not found");
		}
		
		ServiceCategory serviceCat = serviceSubscriptionService.findServiceCategoryByNameIgnoreCase(serviceCategory);
		if (serviceCat==null)
		{
			throw new Exception("Service Category with Name= " + serviceCategory + " not found");
		}
		
		destinationSS.setPartner(partner);
		destinationSS.setServiceCategory(serviceCat);
		
		return destinationSS;
	}
	
	private com.abc.tpi.model.service.Service findServiceFromCollection(ServiceSubscription serviceSubscription, long id)
	{
		if (serviceSubscription==null || serviceSubscription.getServices()==null || serviceSubscription.getServices().size()==0)
		{
			return null;
		}
		
		for (com.abc.tpi.model.service.Service service:serviceSubscription.getServices())
		{
			if (service.getId().equals(id))
				return service;
		}
		
		return null;
	}
	private Delimiter findDeilimiterByName(String name) throws Exception
	{
		if (name!=null)
		{
			Delimiter result = masterDataService.findDelimiterByCode(name);
			if (result==null)
			{
				throw new Exception("Delimiter with code= " + name + " not found");
			}
			return result;
		}
		
		return null;
	}
	
	private Version findVersionByName(int number) throws Exception
	{
		Version result = masterDataService.findVersionByVersionNumber(number);
		if (result==null)
		{
			throw new Exception("Version with code= " + number + " not found");
		}
		return result;
	}
	
	private com.abc.tpi.model.service.Service updateService(com.abc.tpi.model.service.Service originalService, com.abc.tpi.model.service.Service destinationService) throws Exception
	{
		
		
		if (originalService.getCompositeElementDelimiter()!=null)
		{
			Delimiter compositeElementDelimiter = findDeilimiterByName(originalService.getCompositeElementDelimiter().getDelimiter());
			if (compositeElementDelimiter.isComposite()==false)
			{
				throw new Exception("Invalid Composite Delimiter " + originalService.getCompositeElementDelimiter().getDelimiter());
			}
			destinationService.setCompositeElementDelimiter(compositeElementDelimiter);
		}
		
		if (originalService.getElementDelimiter()!=null)
		{
			Delimiter elementDelimiter = findDeilimiterByName(originalService.getElementDelimiter().getDelimiter());
			if (elementDelimiter.isElement()==false)
			{
				throw new Exception("Invalid Element Delimiter " + originalService.getElementDelimiter().getDelimiter());
			}
			destinationService.setElementDelimiter(elementDelimiter);
		}
		
		if (originalService.getSegmentDelimiter()!=null)
		{
			Delimiter segmentDelimiter = findDeilimiterByName(originalService.getSegmentDelimiter().getDelimiter());
			if (segmentDelimiter.isSegment()==false)
			{
				throw new Exception("Invalid Segement Delimiter " + originalService.getSegmentDelimiter().getDelimiter());
			}
			destinationService.setSegmentDelimiter(segmentDelimiter);
		}
		
		if (originalService.getRepeatDelimiter()!=null)
		{
			Delimiter repeatDelimiter = findDeilimiterByName(originalService.getRepeatDelimiter().getDelimiter());
			if (repeatDelimiter.isRepeat()==false)
			{
				throw new Exception("Invalid Repeat Delimiter " + originalService.getRepeatDelimiter().getDelimiter());
			}
			destinationService.setRepeatDelimiter(repeatDelimiter);
		}
		
		destinationService.setNotes(originalService.getNotes());
		destinationService.setSrId(originalService.getSrId());
		destinationService.setLightWellPartner(LightWellPartner.newInstance(originalService.getLightWellPartner()));

		if (originalService.getTpp()!=null)
		{
			Tpp tpp = tppService.findByNameFullStringMatchIgnoreCase(originalService.getTpp().getName());
			if (tpp==null)
			{
				throw new Exception("Tpp with name = " + originalService.getTpp().getName() + " not found");
			}
			destinationService.setTpp(tpp);	
		}
		return destinationService;
	}
	
	private BusinessService findBusinessServiceFromCollection(com.abc.tpi.model.service.Service service, long id)
	{
		if (service==null || service.getBusinessServices()==null || service.getBusinessServices().size()==0)
		{
			return null;
		}
		
		for (BusinessService businessService:service.getBusinessServices())
		{
			if (businessService.getId().equals(id))
				return businessService;
		}
		
		return null;
	}
	
	private BusinessService updateBusinessService(BusinessService originalBS, BusinessService destinationBS) throws Exception
	{
		destinationBS.setAck(originalBS.isAck());
		destinationBS.setAckPeriod(originalBS.getAckPeriod());
		destinationBS.setComplianceCheck(originalBS.isComplianceCheck());
		
		if (originalBS.getLightWellPartner()!=null)
		{
			LightWellPartner lw = serviceSubscriptionService.findLightWellPartner(
					originalBS.getLightWellPartner().getTestIsaID(), 
					originalBS.getLightWellPartner().getTestIsaQualifier(), 
					originalBS.getLightWellPartner().getTestGsId(), 
					originalBS.getLightWellPartner().getProductionIsaID(), 
					originalBS.getLightWellPartner().getProductionIsaQualifier(), 
					originalBS.getLightWellPartner().getProductionGsId(), 
					originalBS.getLightWellPartner().isActive());
			
			if (lw==null)
			{
				StringBuilder sb = new StringBuilder("LightWellPartner with testIsaID = " + originalBS.getLightWellPartner().getTestIsaID());
				sb.append("; testIsaQualifier: " + originalBS.getLightWellPartner().getTestIsaQualifier());
				sb.append("; testGsId: " + originalBS.getLightWellPartner().getTestGsId());
				sb.append("; productionIsaID: " + originalBS.getLightWellPartner().getProductionIsaID());
				sb.append("; productionIsaQualifier: " + originalBS.getLightWellPartner().getProductionIsaQualifier());
				sb.append("; productionGsId: " + originalBS.getLightWellPartner().getProductionGsId());
				sb.append("; isActive: " + originalBS.getLightWellPartner().isActive());
				sb.append(" NOT FOUND.");
				throw new Exception(sb.toString());
			}
			
			destinationBS.setLightWellPartner(lw);
		}
		
		if (originalBS.getMap()!=null)
		{
			TpiMap map = masterDataService.findMapByName(originalBS.getMap().getMapName());
			if (map==null)
			{
				throw new Exception("Map with name " + originalBS.getMap().getMapName() + " not found");
			}
			
			destinationBS.setMap(map);
		}
		
		destinationBS.setNotes(originalBS.getNotes());
		
		if (originalBS.getVersion()!=null)
		{
			Version version = this.findVersionByName(originalBS.getVersion().getVersionNumber());
			destinationBS.setVersion(version);
			
		}
		
		if (originalBS.getServiceType()!=null)
		{
			ServiceType serviceType = serviceTypeRepository.namedFindServiceTypeByNameCompanyPartnerCategory(originalBS.getServiceType().getBusinessServiceName(),
					originalBS.getServiceType().getCompany(), originalBS.getServiceType().getPartnerCategory());
			
			if (serviceType==null)
			{
				StringBuilder sb = new StringBuilder("ServiceType with Business Service Name = " + originalBS.getServiceType().getBusinessServiceName());
				sb.append("; company: " + originalBS.getServiceType().getCompany().toString());
				sb.append("; Partner category: " + originalBS.getServiceType().getPartnerCategory().toString());
				sb.append(" NOT FOUND.");
				throw new Exception(sb.toString());
			}
		}
		destinationBS.setSrId(originalBS.getSrId());
		destinationBS.setStControlNum(originalBS.getStControlNum());
		destinationBS.setIsaControlNum(originalBS.getIsaControlNum());
		destinationBS.setStAcceptorLookUpAlias(originalBS.getStAcceptorLookUpAlias());
		destinationBS.setGsIdVersion(originalBS.getGsIdVersion());
		return destinationBS;
	}
	
	
	
}
