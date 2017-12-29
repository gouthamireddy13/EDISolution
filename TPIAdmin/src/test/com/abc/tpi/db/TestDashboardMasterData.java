package com.abc.tpi.db;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.dashboard.model.SdServiceType;
import com.abc.dashboard.service.SdMasterDataService;
import com.abc.tpi.model.service.ServiceTypeProjectionIdName;
import com.abc.tpi.repository.ServiceTypeRepository;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestDashboardMasterData {

	public TestDashboardMasterData() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	SdMasterDataService masterDataService;
	
	
	@Test
	public void testAddNewSdServiceyType()
	{
		SdServiceType sdServiceType = new SdServiceType();
		sdServiceType.setName("Standard Inbound");
		sdServiceType.setDescription("Standard Inbound");
		masterDataService.addServiceTypeIfNotExists(sdServiceType);
		
	}
	
	@Test
	public void findServiceTestRepositoryByServiceCategoryWithProjection()
	{
		List<ServiceTypeProjectionIdName> result =  serviceTypeRepository.findServiceTypeByServiceCategoryIdOrderByBusinessServiceName((long)4,ServiceTypeProjectionIdName.class);
		
		for (ServiceTypeProjectionIdName o:result)
		{
			System.out.println(o.getBusinessServiceName());
		}
		
		
	}
	
	@Test
	public void findSubUnitsByName()
	{
		String name = "567";
		List<String> result = masterDataService.findSubUnitForABCid(name);
		
		for (String o: result)
		{
			System.out.println(o);
		}
	}
	
	@Test
	public void findSubUnitByGsId()
	{
		long abcId = 1;
		System.out.println("Calling service");
		String result = masterDataService.findSubUnitForLwId(abcId);
		System.out.println("Result " +  result);
	}

}
