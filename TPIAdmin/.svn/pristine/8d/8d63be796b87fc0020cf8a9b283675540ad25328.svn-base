package com.abc.tpi.db;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;
import com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo;
import com.abc.tpi.model.search.projections.ServiceSubscription.ServiceSubscriptionWithGsIdIsaId;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.Service;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.repository.BusinessServiceRepository;
import com.abc.tpi.repository.DocumentRepository;
import com.abc.tpi.repository.LightWellPartnerRepository;
import com.abc.tpi.repository.ObjectTrackerRepository;
import com.abc.tpi.repository.ProtocolRepository;
import com.abc.tpi.repository.ServiceRepository;
import com.abc.tpi.repository.ServiceSubscriptionRepository;
import com.abc.tpi.repository.TppTypeRepository;
import com.abc.tpi.repository.VersionRepository;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.PartnerService;
import com.abc.tpi.service.ReportingService;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.service.TppService;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestTpiServiceSubscriptionTest 
{

	@Autowired
	EntityManager em;
	
	@Autowired
	TppService tppService;
	
	@Autowired 
	ProtocolRepository protocolRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	VersionRepository versionRepository;
	
	@Autowired
	TppTypeRepository tppTypeRepository;
	
	@Autowired
	ServiceSubscriptionService subscriptionService;
	
	@Autowired
	PartnerService partnerService;

	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	BusinessServiceRepository businessServiceRepository;
	
	@Autowired
	ServiceSubscriptionRepository serviceSubscriptionRepository;
	
	@Autowired
	ReportingService reportingService;
	
	@Autowired
	ObjectTrackerRepository objectTrackerRepository;
	
	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;
	
	@Autowired
	ServiceRepository serviceRepository;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testSaveServiceSubscriptionEntity() throws TpiRepositoryException
	{
		System.out.println(objectTrackerRepository.getClass().getSimpleName()); 
		tppService.addProtocol(null);
		
		ServiceCategory serviceCategory = subscriptionService.findServiceCategoryByNameIgnoreCase("ABDC - Customer");
		Partner partner = partnerService.findAllPartners().get(0);		
		
		Tpp tpp = tppService.findAllTpps().get(0);
		
		Delimiter del1 = masterDataService.findDelimiterByCode("#");
		Delimiter del2 = masterDataService.findDelimiterByCode("$");
		Delimiter del3 = masterDataService.findDelimiterByCode("%");
		Delimiter del4 = masterDataService.findDelimiterByCode("+");
		
		ServiceType serviceType  = subscriptionService.getAllServiceTypes().get(0);
		LightWellPartner lwPartner = serviceCategory.getLightWellPartners().iterator().next();
		
		Version version = masterDataService.findAllVersions().iterator().next();
		
		ServiceSubscription serviceSubscription = new ServiceSubscription();
		
		serviceSubscription.setPartner(partner);
		serviceSubscription.setServiceCategory(serviceCategory);
		
		Service service = new Service();
		service.setTpp(tpp);
		service.setCompositeElementDelimiter(del1);
		service.setElementDelimiter(del2);
		service.setSegmentDelimiter(del3);
		service.setRepeatDelimiter(del4);
		
		BusinessService businessService = new BusinessService();
		businessService.setServiceType(serviceType);
		businessService.setVersion(version);
		businessService.setAck(true);
		businessService.setAckPeriod(90);
		businessService.setLightWellPartner(lwPartner);
		businessService.setProtocol(masterDataService.findAllProtocols().get(0));
				
		service.addBusinessService(businessService);
		
		businessService = new BusinessService();
		businessService.setServiceType(serviceType);
		businessService.setVersion(version);
		businessService.setAck(true);
		businessService.setAckPeriod(120);
		businessService.setLightWellPartner(lwPartner);
		businessService.setProtocol(masterDataService.findAllProtocols().get(0));
		service.getBusinessServices().add(businessService);				
		serviceSubscription.addService(service);		
		subscriptionService.saveServiceSubscription(serviceSubscription);
		
	}
	
	@Test
	@Transactional()
	//@Rollback(false)
	public void testDeleteServiceSubscriptions() throws TpiRepositoryException
	{
		List<ServiceSubscription> serviceSubscriptions = subscriptionService.getAllServiceSubscriptions();
		
		for (ServiceSubscription sc:serviceSubscriptions)
		{
			
			subscriptionService.deleteServiceSubscription(sc.getId());
		}
	}
	
	@Test
	public void testFindBusinessSerivcesForService()
	{
		Collection<BusinessService> businessServices = subscriptionService.findBusinessServicesByServiceId(6);
		for (BusinessService bs: businessServices)
		{
			System.out.println(bs.getId());
		}
	}
	
	@Test
	public void testFindBusinessSerivcesForSubscriptionService()
	{
		Collection<BusinessService> businessServices = subscriptionService.findBusinessServicesByServiceSubscriptionAndServiceId(1, 6);
		for (BusinessService bs: businessServices)
		{
			System.out.println(bs.getId());
		}
	}
	
	@Test
	public void testFindBusinessSerivcesForPartnerAndServiceCategory()
	{
		long partnerId = 1;
		long serviceCategoryId = 1;
		Collection<Long> ids = subscriptionService.findServiceSubscriptionByPartnerAndServiceCategory(partnerId, serviceCategoryId);
		
		for (Long id: ids)
		{
			System.out.println(id.longValue());
		}
	}
	
	@Test
	public void testNamedGenericInEnvelopeByServiceSubscriptionId() {

		long subscriptionId = 62;
		List<Object> result = businessServiceRepository.namedGenericInEnvelopeByServiceSubscriptionId(subscriptionId);

		int count = 0;
		for (Object o : result) {
			Object[] values = (Object[]) o;
			System.out.println(++count + ": " + values[0] + ", " + values[1] + ", " + values[2] + ", " + values[3]
					+ ", " + values[4] + ", " + values[5] + ", " + values[6] + ", " + values[7] + ", " + values[8]
				    + ", "+ values[9] + ", " + values[10] + ", " + values[11] + ", " + values[12] + ", " + values[13]
				    + ", "+ values[14] + ", " + values[15] + ", " + values[16] + ", " + values[17] + ", " + values[18]
				    + ", " + values[19] + ", " + values[20] + ", " + values[21]);

		}
	}
	
	@Test
	public void testNamedInEnvelopeByServiceSubscriptionId() {

		long subscriptionId = 62;
		List<PartnerSubscriptionRecord> result = businessServiceRepository.namedInEnvelopeByServiceSubscriptionId(subscriptionId);
		
		for (PartnerSubscriptionRecord o : result) 
		{
			System.out.println
			(o.getPartnerName() + "; " + o.getDirectionCode());

		}
	}
	
	@Test
	public void testReportingService() {

		long subscriptionId = 2;
		List<PartnerSubscriptionRecord> result = reportingService.getPartnerSubscriptionRecordsByServiceSubscriptionId(subscriptionId);
		
		for (PartnerSubscriptionRecord o : result) 
		{
			System.out.println
			(o.getPartnerName() + "; " + o.getDirectionCode());

		}
	}
	
	@Test
	public void testServiceSubscriptionWithGSIds()
	{
		
		List<ServiceSubscriptionWithGsIdIsaId> result = serviceSubscriptionRepository.namedGetServiceSubscriptionWithGsIdIsaId();
		
		for (ServiceSubscriptionWithGsIdIsaId res: result)
		{
			System.out.println(res.getServiceSubscriptionId() + "; " + res.getServiceId() + "; " + res.getProdGsId() + "; " + res.getProdIsaId());
		}
	}
	
	@Test 
	public void testFindServiceSubscriptionBySrId()
	{
		List<ServiceSubscription> result = serviceSubscriptionRepository.namedFindBySRId("1234");
		
		for (ServiceSubscription ss: result)
		{
			System.out.println(ss.getId());
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentBySrId()
	{
		List<BusinessServiceWithParentInfo> result = serviceSubscriptionRepository.namedFindBusinessServicesForSrId("SR0004");
		
		for (BusinessServiceWithParentInfo ss: result)
		{
			System.out.println(ss.getServiceSubscripionId() + " : " + ss.getServiceId() + " : " + ss.getBusinessServiceId() + " : " + ss.getBusinessServiceName() );
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentBySrIdContainsIgnoreCase()
	{

		List<BusinessServiceWithParentInfo> result = subscriptionService.findBusinessServiceWithParentBySrIdContainsIgnoreCase("rId");
		for (BusinessServiceWithParentInfo ss: result)
		{
			System.out.println(ss.getServiceSubscripionId() + " : " + ss.getServiceId() + " : " + ss.getBusinessServiceId() + " : " + ss.getBusinessServiceName() );
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentByPartnerId()
	{
		List<BusinessServiceWithParentInfo> result = subscriptionService.findBusinessServiceWithParentByPartnerName("ALEX T");
		
		for (BusinessServiceWithParentInfo ss: result)
		{
			System.out.println(ss.getServiceSubscripionId() + " : " + ss.getServiceId() + " : " + ss.getBusinessServiceId() + " : " + ss.getBusinessServiceName() );
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentByPartnerIdContainsIngoreCase()
	{
		List<BusinessServiceWithParentInfo> result = subscriptionService.findBusinessServiceWithParentByPartnerNameContainsIgnoreCase("ALeX T");
		
		for (BusinessServiceWithParentInfo ss: result)
		{
			System.out.println(ss.getServiceSubscripionId() + " : " + ss.getServiceId() + " : " + ss.getBusinessServiceId() + " : " + ss.getBusinessServiceName() );
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentBySrIdBusinessServiceId()
	{
		List<Long> businessServiceIds = new ArrayList<Long>();
		
		businessServiceIds.add((long) 64);

		List<PartnerSubscriptionRecord> result = reportingService.getPartnerSubscriptionRecordsBySrIdBusinessSrvcId("34534", businessServiceIds);
		
		for (PartnerSubscriptionRecord ss: result)
		{
			System.out.println( ss.getPartnerName() );
		}
	}
	
	@Test 
	public void testFindBusinessServiceWithParentByPartnerName()
	{
		String partnerName = "Alex Test Partner 2";

		List<PartnerSubscriptionRecord> result = reportingService.getPartnerSubscriptionRecordsByPartnerName(partnerName);
		
		for (PartnerSubscriptionRecord ss: result)
		{
			System.out.println( ss.getPartnerName() );
		}
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdateBusinessServiceNew() throws TpiRepositoryException
	{

		ServiceSubscription serviceSubscription = subscriptionService.getAllServiceSubscriptions().get(0);
		

		ServiceCategory serviceCategory = subscriptionService.findServiceCategoryByNameIgnoreCase("ABDC - ABDC Customer Transactions");
		Service service = serviceSubscription.getServices().iterator().next();

		ServiceType serviceType  = subscriptionService.getAllServiceTypes().get(0);
		LightWellPartner lwPartner = serviceCategory.getLightWellPartners().iterator().next();
		
		Version version = masterDataService.findVersionById(1);
		
		BusinessService businessService = new BusinessService();
		businessService.setServiceType(serviceType);
		businessService.setVersion(version);
		businessService.setAck(true);
		businessService.setAckPeriod(99);
		businessService.setLightWellPartner(lwPartner);
		businessService.setProtocol(masterDataService.findAllProtocols().get(0));
		
		service.addBusinessService(businessService);
		
		subscriptionService.saveServiceSubscription(serviceSubscription);
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdateBusinessService() throws TpiRepositoryException
	{

		ServiceSubscription serviceSubscription = subscriptionService.getAllServiceSubscriptions().get(0);
		Service service = serviceSubscription.getServices().iterator().next();
		BusinessService businessService = service.getBusinessServices().iterator().next();
		
		businessService.setAckPeriod(14);
		
		subscriptionService.saveServiceSubscription(serviceSubscription);
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testUpdateServiceLightWellId() throws TpiRepositoryException
	{

		ServiceSubscription serviceSubscription = subscriptionService.getAllServiceSubscriptions().get(0);
		Service service = serviceSubscription.getServices().iterator().next();
		service.getTpp().getLightWellPartner().setNotes("test notes");

		
		subscriptionService.saveServiceSubscription(serviceSubscription);
		
	}
	
	@Test
	public void testFindServiceSubscriptionToBeMigrated()
	{
		List<Long> result = serviceSubscriptionRepository.namedFindAllNotMigratedServiceSubscription();

		Service service = new Service();
		BusinessService bs = new BusinessService();
		ServiceSubscription s = new ServiceSubscription();
		
		System.out.println(service.getClass().getName());
		System.out.println(bs.getClass().getName());
		System.out.println(s.getClass().getName());
		
			for (Long ss:result)
			{
				System.out.println(ss);
			}
		
	}
	
	@Test
	public void testFindServicesToBeMigrated()
	{
		List<Long> result = serviceRepository.namedFindAllNotMigratedServices();

		for (Long ss:result)
			{
				System.out.println(ss);
			}		
	}
	
	@Test
	public void testFindBusinessServicesToBeMigrated()
	{
		List<Long> result = businessServiceRepository.namedFindAllNotMigratedBusinessServices();

		for (Long ss:result)
			{
				System.out.println(ss);
			}		
	}
	
	@Test
	public void testFindBusinessServicesToBeMigratedBySrId()
	{
		String srId = "123";
		List<Long> result = businessServiceRepository.namedFindNotMigratedBusinessServicesBySrId(srId);

		for (Long ss:result)
			{
				System.out.println(ss);
			}
	}
	
	@Test
	public void testEntityGraph()
	{
		Long id = (long) 392;
		ServiceSubscription ss = serviceSubscriptionService.findEntityUsingEntityGrpah(ServiceSubscription.class, id, "ServiceSubscriptionEntity.graphPartnerServiceCat");		
		System.out.println(ss);		
	}
	
	@Test
	public void testServiceEntityGraph()
	{
		Long id = (long) 402;
		Service ss = serviceSubscriptionService.findEntityUsingEntityGrpah(Service.class, id, "ServiceEntity.graphServiceId");		
		System.out.println(ss);		
	}
	
	@Test 
	public void testFindServiceSubscriptionVersion()
	{
		Long id = (long) 192;
		Integer result = serviceSubscriptionService.findServiceSubscriptionVersionById(id);
		if (result == null)
			System.out.println("Service Subscription not found");
		else
			System.out.println(result);
		
				
	}
	
	@Test
	public void testFindServiceIdsForServiceSubscription()
	{
		Long id = (long) 192;
		Collection<Long> result = serviceSubscriptionService.getServiceIdsForServiceSubscription(id);
		
		if (result == null)
			System.out.println("Services  not found");
		else
			for (Long ids: result)
			{
				System.out.println(ids);
			}		
	}
	
	@Test
	public void testFindBusinessServiceIdsForService()
	{
		Long id = (long) 202;
		Collection<Long> result = serviceSubscriptionService.getBusinessServiceIdsForService(id);
		
		if (result == null)
			System.out.println("Business Services  not found");
		else
			for (Long ids: result)
			{
				System.out.println(ids);
			}		
	}
	
}
