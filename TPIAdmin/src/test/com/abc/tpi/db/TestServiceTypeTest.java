package com.abc.tpi.db;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.dashboard.model.SdServiceAccess;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceTypeProjectionIdName;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.service.CommonJpaService;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestServiceTypeTest {

	public TestServiceTypeTest() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	CommonJpaService commonJpaService;
	
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
	public void findSdServiceAccessUsingGraph()
	{

		SdServiceAccess sc = commonJpaService.findGenericEntityUsingEntityGrpah(SdServiceAccess.class, new Long(1012), "SdServiceAccess.graphWebView");
		System.out.println(sc.getId());
	}

}
