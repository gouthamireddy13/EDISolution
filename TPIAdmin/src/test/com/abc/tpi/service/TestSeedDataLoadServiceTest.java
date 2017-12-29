/**
 * 
 */
package com.abc.tpi.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.tpi.utils.AppConstants;

/**
 * @author ARINDAMSIKDAR
 *
 */
@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class TestSeedDataLoadServiceTest {
	
	private static final Logger logger = LogManager.getLogger(TestSeedDataLoadServiceTest.class);
	
	@Autowired
	SeedDataLoadService seedDataLoadService;
	
	@Test
	public void testValidatePartnerStringValidDataCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("ABBOTT NUTRITION (WAS ROSS LABS) [9251210008],Partner  Group One,Sub Group One,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		assertTrue(validationStatus.length() == 0);		
		
	}
	
	@Test
	public void testValidatePartnerStringBlankCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode03,validationStatus);
		
		
	}
	
	@Test
	public void testValidatePartnerStrinCommaSeparatedBlankCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString(",,,,,,,,,,,,,,,,,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStrinCommaSeparatedBlankCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode03,validationStatus);
			
		
	}
	
	@Test
	public void testValidatePartnerStringBlankPartnerNameCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString(",Partner  Group One,Sub Group One,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankPartnerNameCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode04,validationStatus);
				
		
	}
	
	@Test
	public void testValidatePartnerStringBlankPartnerGroupCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,,Sub Group One,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankPartnerGroupCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode05,validationStatus);
			
		
	}

	@Test
	public void testValidatePartnerStringBlankPartnerSubGroupCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,Partner  Group One,,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankPartnerSubGroupCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode06,validationStatus);
			
		
	}
	
	@Test
	public void testValidatePartnerStringBlankPartnerContactNameCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,Partner  Group One,Sub Group One,,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankPartnerContactNameCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode07,validationStatus);
			
		
	}
	
	@Test
	public void testValidatePartnerStringBlankPartnerPrimaryEmailCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,Partner  Group One,Sub Group One,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankPartnerPrimaryEmailCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode08,validationStatus);
				
		
	}
	
	@Test
	public void testValidatePartnerStringBlankTransactionTypeCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,Partner  Group One,Sub Group One,Kathan Daker,Kathan Daker,US,847-935-9718,,USA,,,USA,,,kahtan.daker@abbvie.com,,,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankTransactionTypeCheck() : " + validationStatus);
		assertEquals(AppConstants.errorCode09,validationStatus);
				
		
	}
	
	@Test
	public void testValidatePartnerStringBlankNonMandatoryFieldsCheck() {
		String validationStatus = seedDataLoadService.validatePartnerString("PARTNER NAME,Partner  Group One,Sub Group One,Kathan Daker,,,,,,,,,,,kahtan.daker@abbvie.com,,845,");
		assertNotNull(validationStatus);
		System.out.println("testValidatePartnerStringBlankNonMandatoryFieldsCheck() : " + validationStatus);
		assertTrue(validationStatus.length() == 0);		
				
		
	}
}
