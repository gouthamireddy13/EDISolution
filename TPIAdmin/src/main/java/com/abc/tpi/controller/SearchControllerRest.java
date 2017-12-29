package com.abc.tpi.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.service.SdServiceCategoryService;
import com.abc.tpi.common.exceptions.TpiRepositoryException;

import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.repository.LightWellPartnerRepository;
import com.abc.tpi.service.PartnerService;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.service.TppService;



@RestController
@RequestMapping("/search")
public class SearchControllerRest {

	private static final Logger logger = LogManager.getLogger(SearchControllerRest.class);
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	TppService tppService;
	
	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;
	
	@Autowired
	SdServiceCategoryService serviceCategoryService;

	@RequestMapping(method = {RequestMethod.GET})
	public ResponseEntity<?> getSearchResults(@RequestParam(name="q",required=true) String queryName, 
											  @RequestParam(name="filter",required=false) String queryFilter) 
											  throws TpiRepositoryException 
	{

		Object result = null;
		logger.debug("Executing getSearchResults() for " + queryName);
		
		switch (queryName)
		{
			case "serviceSubscriptionsWithLW":
			{
				result = serviceSubscriptionService.getAllServiceSubscriptionsWithGsIdIsId();
				break;
			}
			
			case "businessServiceForSrId":
			{				
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);
					result = serviceSubscriptionService.findBusinessServiceWithParentBySrIdContainsIgnoreCase(queryFilter);
				}
				else
				{
					logger.warn("filter is REQUIRED for 'businessServiceForSrId' ");
				}
				break;
			}
			case "businessServiceForPartnerName":
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);
					result = serviceSubscriptionService.findBusinessServiceWithParentByPartnerNameContainsIgnoreCase(queryFilter);
				}
				else
				{
					logger.warn("filter is REQUIRED for 'businessServiceForPartnerName' ");
				}
				break;
			}
			case "lightWellIdentityByPartnerName":
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);					
					result = lightWellPartnerRepository.namedFindLightWellPartnerByPartnerNameIgnoreCase("%" + queryFilter + "%");
				}
				else
				{
					logger.warn("filter is REQUIRED for 'lightWellIdentityByPartnerName' ");
				}
				break;
			}
			
			case "lightWellIdentityBySrId":
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);					
					result = lightWellPartnerRepository.namedFindLightWellPartnerBySrIdIgnoreCase("%" + queryFilter + "%");
				}
				else
				{
					logger.warn("filter is REQUIRED for 'lightWellIdentityBySrId' ");
				}
				break;
			}
			case "partnerName":
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);
					result = partnerService.findPartnersByNameContains(queryFilter);
				}
				else
				{
					logger.warn("filter is REQUIRED for 'partnerName' ");
				}
				break;
			}
			
			case "tppName":	// Added by pappu
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);
					result = tppService.findTppByName(queryFilter);  
					logger.debug("result for " + result.toString());
				}
				else
				{
					logger.warn("filter is REQUIRED for 'tppName' ");
				}
				break;
			}
			case "SendToBU":	// Added by pappu
			{
				if (queryFilter!=null)
				{
					logger.debug("Filter for " + queryFilter);
					java.util.List<SdServiceCategoryDef> scdList = null;
					scdList = serviceCategoryService.getAllSdServiceCategoryDefs(SdServiceCategoryDef.class);
					logger.debug("result for AllSdServiceCategoryDefs  :" + scdList);
					if(scdList != null){
					List<LightWellPartner> lwID =   new ArrayList<LightWellPartner>();
						for (SdServiceCategoryDef scd : scdList)
						{
							if(scd.getBusinessSubUnit().getSubUnitName().equalsIgnoreCase(queryFilter)){
								ServiceCategory sc = scd.getServiceCategory();
								if(sc != null){
									logger.debug("ServiceCategory object:"+sc.getName());
									Set<LightWellPartner>  lwplist = sc.getLightWellPartners();
									//logger.debug("LightWellPartner set:"+lwplist);
									if(lwplist != null){
										for (LightWellPartner ptnr : lwplist)
										{
								lwID.add(ptnr);
								

										}
									}
								}
							}
						}
						result = lwID;
						logger.debug("result for " + result.toString());
					} //result = "TEST GS ID and PROD GS ID not Found for Business Sub Unit:"+queryFilter;
				}
				else
				{
					logger.warn("filter is REQUIRED for 'SendToBU' ");
				}
				break;
			}
			default:
				logger.warn(queryName + " is not a valid search");
				result = "Invalid search option";
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	

	
}
