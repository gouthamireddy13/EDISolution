package com.abc.tpi.db;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.service.CompanyEnum;
import com.abc.tpi.model.service.PartnerCategoryEnum;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceCategoryForDropDownProjection;
import com.abc.tpi.model.service.ServiceSubscriptionListViewProjection;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.repository.DirectionRepository;
import com.abc.tpi.repository.DocumentRepository;
import com.abc.tpi.repository.ServiceCategoryRepository;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.repository.TpiMapRepository;
import com.abc.tpi.repository.VersionRepository;
import com.abc.tpi.service.ServiceSubscriptionService;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTpiServiceCategoryTest {
	
	@Autowired 
	ServiceCategoryRepository serviceCategoryRepository;
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	VersionRepository versionRepository;
	
	@Autowired
	DirectionRepository directionRepository;
	
	@Autowired
	ServiceSubscriptionService subscriptionService;
	
	@Autowired
	TpiMapRepository tpiMapRepository;
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAddServiceCategory()
	{
		
		
		String serviceCategoryName = "ABSG - ABSG Customer Transactions";

		ServiceCategory serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);

		
		LightWellPartner lw = new LightWellPartner();
		lw.setActive(true);
		lw.setNotes("test notes");
		lw.setOrganizationName(serviceCategoryName);
		lw.setProductionGsId("11111");
		lw.setProductionIsaID("11111");
		lw.setProductionIsaQualifier("PROD");
		lw.setTestGsId("11111");
		lw.setTestIsaID("11111");
		lw.setTestIsaQualifier("TEST");
		
		serviceCategory.addLightWellPartner(lw);
		
		lw = new LightWellPartner();
		lw.setActive(true);
		lw.setNotes("test notes2");
		lw.setOrganizationName(serviceCategoryName);
		lw.setProductionGsId("22222");
		lw.setProductionIsaID("22222");
		lw.setProductionIsaQualifier("PROD");
		lw.setTestGsId("22222");
		lw.setTestIsaID("22222");
		lw.setTestIsaQualifier("TEST");
		
		serviceCategory.addLightWellPartner(lw);
		

		
		subscriptionService.saveServiceCategory(serviceCategory);
		

		
		serviceCategoryName = "ABDC - ABDC Customer Transactions";

		serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);
		
		lw = new LightWellPartner();
		lw.setActive(true);
		lw.setNotes("test notes");
		lw.setOrganizationName(serviceCategoryName);
		lw.setProductionGsId("11111");
		lw.setProductionIsaID("11111");
		lw.setProductionIsaQualifier("PROD");
		lw.setTestGsId("11111");
		lw.setTestIsaID("11111");
		lw.setTestIsaQualifier("TEST");		
		serviceCategory.addLightWellPartner(lw);

		
		lw = new LightWellPartner();
		lw.setActive(true);
		lw.setNotes("test notes2");
		lw.setOrganizationName(serviceCategoryName);
		lw.setProductionGsId("22222");
		lw.setProductionIsaID("22222");
		lw.setProductionIsaQualifier("PROD");
		lw.setTestGsId("22222");
		lw.setTestIsaID("22222");
		lw.setTestIsaQualifier("TEST");
		
		serviceCategory.addLightWellPartner(lw);
		

		
		subscriptionService.saveServiceCategory(serviceCategory);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAddServiceType()
	{
		ServiceType serviceType = new ServiceType();
		
		serviceType.setCompany(CompanyEnum.ABDC);
		serviceType.setPartnerCategory(PartnerCategoryEnum.Supplier);
		
		//get Service Category
		ServiceCategory serviceCategory = serviceCategoryRepository.findOne((long) 33);
		
		//get Document		
		Document document = documentRepository.findOneByDocumentType("810");
		
		
		//get Version		
		Version version = versionRepository.findOneByVersionNumber(6010);
				
		//get Direction
		Direction direction = directionRepository.findOneByDirectionCodeIgnoreCase("outbound");
		
		serviceType.setServiceCategory(serviceCategory);
		serviceType.setDirection(direction);
		serviceType.setDocument(document);
		serviceType.setBusinessServiceName("ABDC - 810 Out - PO Ack from Suppliers");
		
		serviceTypeRepository.save(serviceType);
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void addMapToServiceType()
	{
		ServiceType serviceType = serviceTypeRepository.findServiceTypeByBusinessServiceNameIgnoreCase("ABSG - 850 Out - PO to Suppliers");
		TpiMap tpiMap = tpiMapRepository.findOneByMapName("No Map");
		if (serviceType!=null && tpiMap!=null)
		{
			serviceType.addMap(tpiMap);
		}
		serviceTypeRepository.save(serviceType);

	}
	
	@Test
	public void testFindServiceTypeByServiceCategory()
	{
		List<ServiceType> serviceType = serviceTypeRepository.namedFindAllServiceTypesByServiceCategoryId(1);
		
		for (ServiceType service : serviceType)
		{
			System.out.println(service.getBusinessServiceName());
		}
	}
	
	
	@Test
	public void testFindServiceTypeByServiceCategoryAndTppAttributes()
	{

		List<ServiceType> serviceType = serviceTypeRepository.namedFindServiceTypesForServiceCategoryAndTransaction(1, 1, 1);
		
		
		for (ServiceType service : serviceType)
		{
			System.out.println(service.getBusinessServiceName());
		}
	}
	
	@Test
	public void testFindServiceTypeByServiceCategoryAndTppObject()
	{

		List<ServiceType> serviceType;
		try {
			serviceType = subscriptionService.getBusinessServiceForTppAndServiceCat(2,192);
			for (ServiceType service : serviceType)
			{
				System.out.println(service.getBusinessServiceName());
			}
		} catch (TpiRepositoryException e) {
			
			e.printStackTrace();
		}
		

	}
	@Test
	public void testFindServiceTypeForDropDown()
	{

		List<ServiceCategoryForDropDownProjection> projection;
		try {
			projection = serviceCategoryRepository.findDistinctByOrderByName();
			for (ServiceCategoryForDropDownProjection item : projection)
			{
				System.out.println(item.getName());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		

	}
	@Test
	public void testFindServiceSubscriptionProjection()
	{

		List<ServiceSubscriptionListViewProjection> projection;
		try {
			projection = subscriptionService.getServiceSubscriptionsListView();
			Assert.assertNotNull(projection);
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		


	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testDeleteAllServiceCategories()
	{

		List<ServiceCategory> sc;
		try {
			sc = subscriptionService.getAllServiceCategories();

			for (ServiceCategory s:sc)
			{
				subscriptionService.deleteServiceCategory(s.getId());
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		

	}
	
	@Test
	public void testFindServiceCategoryLightWellPartner()
	{

		LightWellPartner lw = subscriptionService.findLightWellParnterByServiceCategoryLWid(83, 796);
		System.out.println(lw.getOrganizationName());
		

	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testDeleteAllServiceCategoriesLightWell()
	{

		List<ServiceCategory> sc;
		try {
			sc = subscriptionService.getAllServiceCategories();

			for (ServiceCategory s:sc)
			{
				//s.setLightWellPartners(null);
				subscriptionService.saveServiceCategory(s);
			}
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	@Test
	public void testFindServiceCategoryByName()
	{
		String scName = "";
		ServiceCategory sc = subscriptionService.findServiceCategoryByNameIgnoreCase(scName);
		
		System.out.println(sc.getName());
		

	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAddEmptyServiceCategory()
	{
		String serviceCategoryName = "ABDC - ABDC Supplier Transactions";					
		ServiceCategory serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);		
		subscriptionService.saveServiceCategory(serviceCategory);
		
		serviceCategoryName = "ABSG - ABSG Supplier Transactions";		
		serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);		
		subscriptionService.saveServiceCategory(serviceCategory);
		
		serviceCategoryName = "ABSG - ABSG Customer Transactions";		
		serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);		
		subscriptionService.saveServiceCategory(serviceCategory);
		
		serviceCategoryName = "ABDC - ABDC Customer Transactions";		
		serviceCategory = new ServiceCategory();
		serviceCategory.setName(serviceCategoryName);		
		subscriptionService.saveServiceCategory(serviceCategory);
		
	}
	
}
