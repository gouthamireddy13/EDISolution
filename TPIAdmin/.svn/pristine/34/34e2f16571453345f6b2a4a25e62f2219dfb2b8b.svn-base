package com.abc.tpi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dashboard.service.SdServiceCategoryService;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.service.ServiceSubscriptionService;

@RestController
@RequestMapping("/lightWellPartners")
public class LightWellPartnerController {
	
	private static final Logger logger = LogManager.getLogger(LightWellPartnerController.class);
	
	@Autowired
	SdServiceCategoryService sdServiceCategoryService;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@RequestMapping(method={RequestMethod.GET},value = { "/withScMembership" })
	ResponseEntity<List<LightWellPartner>> getAllLightWellPartners()
	{
		logger.debug("LightWellPartnerController::getAllLightWellPartners()");
		List<LightWellPartner> result = sdServiceCategoryService.getLightWellPartnersWithSdServiceCategoryMembership();
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	@RequestMapping(method={RequestMethod.GET},value = { "/exists" })
	ResponseEntity<Boolean> lightWellPartnerExistsForAttribute (@RequestParam(name="attribute",required=true) String attribute, @RequestParam(name="value",required=true) String value)
	{
		boolean result = false;
		
		LightWellPartner lw = serviceSubscriptionService.findOneLightWellPartnerByAttribute(attribute, value);
		
		if (lw!=null)
		{
			result = true;
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
	
	
	@RequestMapping(method={RequestMethod.GET},value = { "/existsfor" })
	ResponseEntity<Boolean> lightWellPartnerExistsForAnyAttribute (
			@RequestParam(name="testIsaId",required=false) String testIsaId,
			@RequestParam(name="prodIsaId",required=false) String prodIsaId,
			@RequestParam(name="testGsId",required=false) String testGsId,
			@RequestParam(name="prodGsId",required=false) String prodGsId,
			@RequestParam(name="lwId",required=false) Long lwId)
	{
		boolean result = false;
		
		LightWellPartner lw = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(testIsaId, testGsId, prodIsaId, prodGsId,lwId);
		
		if (lw!=null)
		{
			result = true;
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);	
	}
}
