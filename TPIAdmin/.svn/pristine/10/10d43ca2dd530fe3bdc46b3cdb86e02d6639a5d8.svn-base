package com.abc.tpi.db;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.tpi.domain.json.PartnerLightWellData;
import com.abc.tpi.model.reporting.LightWellPartnerRecord;
import com.abc.tpi.repository.LightWellPartnerRepository;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestTpiReportingTest {
	
	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;
	
	
	@Test
	public void testFindLightWellByPartnerName()
	{
		String partnerName = "Hillestad Pharmaceuticals";
		List<PartnerLightWellData> lwRecords = lightWellPartnerRepository.namedFindLightWellPartnerByPartnerName(partnerName);
		
		for (PartnerLightWellData lw:lwRecords)
		{
			System.out.println(lw.getGsId() + " " + lw.getLwId() + " " + lw.getPartnerId());
		}
	}
	
	@Test
	public void testFindLightWellBySrId()
	{
		String srId = "123";
		List<PartnerLightWellData> lwRecords = lightWellPartnerRepository.namedFindLightWellPartnerBySrId(srId);
		
		for (PartnerLightWellData lw:lwRecords)
		{
			System.out.println(lw.getGsId() + " " + lw.getLwId() + " " + lw.getPartnerId());
		}
	}
	
	@Test
	public void testFindLightWellByPartnerIdLwIds()
	{
		Long partnerId = (long)3628;
		List<Long> lwIds = new ArrayList<Long>();
		lwIds.add((long) 272);
		lwIds.add((long) 273);
		
		List<LightWellPartnerRecord> lwRecords = lightWellPartnerRepository.namedFindLightWellIdentityByPartnerIdLwIds(partnerId, lwIds);
		
		for (LightWellPartnerRecord lw:lwRecords)
		{
			System.out.println(lw.getB2bIdentifier() + " " + lw.getLwId() + " " + lw.getOrgName());
		}
	}
}
