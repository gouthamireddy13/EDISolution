package com.abc.tpi.db;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.TppDetail;
import com.abc.tpi.model.tpp.Transaction;
import com.abc.tpi.repository.TppRepository;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestTpiPersistanceTest {

	
	@Autowired
	private TppRepository tppRepository;
	
	@Test
	public void testTppDetailSave()
	{
		TppDetail tppDetail = new TppDetail();
		tppDetail.setContactName("Alex Test");
		tppDetail.setContactPhoneNumber("2158221224");
		tppDetail.setEmailAddress("somethin@something.com");
		tppDetail.setTitle("Mr.");			
		//tppDetail = tppDetailRepository.save(tppDetail);
		assertNotNull(tppDetail);
	}
	
	@Test
	public void testTppSave()
	{
		TppDetail tppDetail = new TppDetail();
		tppDetail.setContactName("Alex Test2");
		tppDetail.setContactPhoneNumber("2158221224");
		tppDetail.setEmailAddress("somethin@something.com");
		tppDetail.setTitle("Mr.");			

		TppType tppType = new TppType();
		tppType.setTypeCode((short) 12345);
		tppType.setDescription("12345");
		
		
		Protocol protocol = new Protocol();
		protocol.setProtocolType("test type");
		
		
		Transaction transaction = new Transaction();
		
		//transaction.setDirection(TpiDirection.INBOUND);
		
		Document doc = new Document();
		doc.setDocumentDescription("some code");
		doc.setDocumentType("1");
		transaction.setDocument(doc);
		
		Version ver = new Version();
		ver.setVersionNumber(0);
		
		transaction.setVersion(ver);
		
		LightWellPartner lp = new LightWellPartner();
		lp.setActive(true);
		lp.setNotes("some notes");
		lp.setOrganizationName("Organization name");
		lp.setProductionGsId("GS ID");
		lp.setTestGsId("something");
		
		Tpp tpp = new Tpp();
		//tpp.setDetails(tppDetail);
		tpp.setLightWellPartner(lp);
		tpp.setName("Some name");
		
		tpp.setType(tppType);
		
		tppRepository.save(tpp);
		
		assertNotNull(tppDetail);
	}
}
