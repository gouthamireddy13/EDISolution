package com.abc.tpi.db;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.dashboard.model.SdBusinessUnit;
import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdYesNo;
import com.abc.dashboard.service.SdMasterDataService;
import com.abc.dashboard.service.SdServiceCategoryService;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.tpp.LightWellPartner;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestDashboardServiceData {

	public TestDashboardServiceData() {
		// TODO Auto-generated constructor stub
	}
 
	@Autowired
	SdServiceCategoryService sdServiceCategoryService;
	
	@Autowired
	SdMasterDataService sdMasterDataService;
	
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAddNewSdServiceCategoryDef()
	{
		SdBusinessUnit bu = sdMasterDataService.findSdBusinessUnitByNameAndSubUnitName("ABSG", "Theracom");
		SdServiceCategoryDef result = new SdServiceCategoryDef();
		
		if (bu!=null)
		{
			result.setCategoryID(4);
			result.setPartnerSubscription(SdYesNo.Y);
			result.setServiceCategory(new ServiceCategory());
			result.getServiceCategory().setName("test4");
			
			LightWellPartner lw = new LightWellPartner();
			lw.setActive(true);
			lw.setNotes("test notes");
			lw.setOrganizationName("test 1");
			lw.setProductionGsId("11111");
			lw.setProductionIsaID("11111");
			lw.setProductionIsaQualifier("PROD");
			lw.setTestGsId("11111");
			lw.setTestIsaID("11111");
			lw.setTestIsaQualifier("TEST");
			
			result.getServiceCategory().addLightWellPartner(lw);
			
			lw = new LightWellPartner();
			lw.setActive(true);
			lw.setNotes("test notes2");
			lw.setOrganizationName("test 2");
			lw.setProductionGsId("22222");
			lw.setProductionIsaID("22222");
			lw.setProductionIsaQualifier("PROD");
			lw.setTestGsId("22222");
			lw.setTestIsaID("22222");
			lw.setTestIsaQualifier("TEST");
			
			result.getServiceCategory().addLightWellPartner(lw);
			
			
			result.setBusinessUnit(bu);
			result.setBusinessSubUnit(bu.getSubUnits().iterator().next());
		}
		sdServiceCategoryService.saveSdServiceCategoryDef(result);
		
	}
	
	@Test
	@Transactional
	@Rollback(false)
	public void testAddNewSdServiceAccess()
	{
		
		SdServiceAccess result = new SdServiceAccess();
		
		SdServiceCategoryDef serviceCategoryDef =  sdServiceCategoryService.findSdServiceCategoryById(1);
		
		result.setServiceCategoryDef(serviceCategoryDef);
		result.setServiceType(sdMasterDataService.getAllServiceTypes().iterator().next());
		result.setDestinationId("destination id");
		result.setServicePreamble("service preamble");
		result.setSourceId("source id");
		
		sdServiceCategoryService.saveSdServiceAccess(result);

	}
	
	@Test
	public void testGetLightWellPartnersWithSdServiceCategoryMembership()
	{
		List<LightWellPartner> result = sdServiceCategoryService.getLightWellPartnersWithSdServiceCategoryMembership();
		
		for (LightWellPartner ptnr : result)
		{
			System.out.println(ptnr.getProductionGsId());
		}
		
	}
}

