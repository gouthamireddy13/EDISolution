package com.abc.tpi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.model.promotion.ServiceSubscriptionPromotion;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.Service;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.service.ServiceSubscriptionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
public class PromotionControllerRest {

	//'/TPIAdmin/app/partnerSubscriptionReportForSrIdPrtnrId?id='+$scope.Id+'&direction='+$scope.direction+'&prtnrIds='+$scope.bussidrequest
	private static final Logger logger = LogManager.getLogger(PromotionControllerRest.class);

	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	
	@RequestMapping(value = {"/serviceSubscriptionReportForSrId"}, method= {RequestMethod.GET})
	public ResponseEntity<List<?>> getPartnerSubscriptionReportForSrIdPrtnrId(@RequestParam(name="id",required=false) String id)
	{
		logger.debug("Invoked PromotionControllerRest.getPartnerSubscriptionReportForSrIdPrtnrId()");
		
		List<ServiceSubscription> serviceSubscriptions=null;
		List<ServiceSubscriptionPromotion> serviceSubscriptionPromotions = new ArrayList<ServiceSubscriptionPromotion>();
		ServiceSubscriptionPromotion serviceSubscriptionPromotion = null;

		if (id!=null && id.trim().length() != 0)
		{
			serviceSubscriptions = (List<ServiceSubscription>) serviceSubscriptionService.findServiceSubscriptionBySrId(id);
		}
		else
		{
			serviceSubscriptions = serviceSubscriptionService.getAllServiceSubscriptions();
		}
		if(serviceSubscriptions != null && !serviceSubscriptions.isEmpty()) {
			for(ServiceSubscription srvcSubscription : serviceSubscriptions) {
				//serviceSubscriptionPromotion = new ServiceSubscriptionPromotion();
				//serviceSubscriptionPromotion.setPartnerName(srvcSubscription.getPartner().getPartnerName());
				Set<Service> services = srvcSubscription.getServices();
				if(services != null && !services.isEmpty()) {
					for(Service srvc : services) {
						Set<BusinessService> businessServices = srvc.getBusinessServices();
						if(businessServices != null && !businessServices.isEmpty()) {
							for(BusinessService bsSrvc : businessServices) {
								logger.debug("Business Service Obejct: "+bsSrvc);
								logger.debug("Service Obejct: "+ srvc);
								logger.debug("Service Subscription Obejct: "+srvcSubscription);
								serviceSubscriptionPromotion = new ServiceSubscriptionPromotion();
								serviceSubscriptionPromotion.setId(bsSrvc.getId());
								serviceSubscriptionPromotion.setPartnerName(srvcSubscription.getPartner().getPartnerName());
								serviceSubscriptionPromotion.setSegmentDelimiter(srvc.getSegmentDelimiter().getDelimiter());
								serviceSubscriptionPromotion.setElementDelimiter(srvc.getElementDelimiter().getDelimiter());
								serviceSubscriptionPromotion.setCompositeElementDelimiter(srvc.getCompositeElementDelimiter().getDelimiter());
								if(srvc.getRepeatDelimiter() != null && srvc.getRepeatDelimiter().getDelimiter().length() != 0) {
									serviceSubscriptionPromotion.setRepeatDelimiter(srvc.getRepeatDelimiter().getDelimiter());
								} else {
									serviceSubscriptionPromotion.setRepeatDelimiter("");
								}
								
								serviceSubscriptionPromotion.setSrId(srvc.getSrId());
								if(srvc.getNotes() != null) {
									serviceSubscriptionPromotion.setNotes(srvc.getNotes());
								} else {
									serviceSubscriptionPromotion.setNotes("");
								}
								if(srvc.getTpp() != null && srvc.getTpp().getName() != null && srvc.getTpp().getName().length() != 0) {
									serviceSubscriptionPromotion.setTppName(srvc.getTpp().getName());
								} else {
									serviceSubscriptionPromotion.setTppName("");
								}


								serviceSubscriptionPromotion.setTestIsaID(srvc.getLightWellPartner().getTestIsaID());
								serviceSubscriptionPromotion.setTestIsaQualifier(srvc.getLightWellPartner().getTestIsaQualifier());
								serviceSubscriptionPromotion.setTestGsId(srvc.getLightWellPartner().getTestGsId());
								serviceSubscriptionPromotion.setProductionIsaID(srvc.getLightWellPartner().getProductionIsaID());
								serviceSubscriptionPromotion.setProductionIsaQualifier(srvc.getLightWellPartner().getProductionIsaQualifier());
								serviceSubscriptionPromotion.setProductionGsId(srvc.getLightWellPartner().getProductionGsId());

								serviceSubscriptionPromotion.setAbcProductionGsId(bsSrvc.getLightWellPartner().getProductionGsId());
								serviceSubscriptionPromotion.setVersionNumber(bsSrvc.getVersion().getVersionNumber());
								if(bsSrvc.getGsIdVersion() != null) {
									serviceSubscriptionPromotion.setGsIdVersion(bsSrvc.getGsIdVersion());
								} else {
									serviceSubscriptionPromotion.setGsIdVersion("");
								}


								serviceSubscriptionPromotion.setProtocolType(bsSrvc.getProtocol().getProtocolType());
								if(bsSrvc.isAck()) {
									serviceSubscriptionPromotion.setAck("Yes");
									serviceSubscriptionPromotion.setAckPeriod(bsSrvc.getAckPeriod());
								} else {
									serviceSubscriptionPromotion.setAck("No");
									serviceSubscriptionPromotion.setAckPeriod(bsSrvc.getAckPeriod());
								}

								if(bsSrvc.getStControlNum() != null) {
									serviceSubscriptionPromotion.setStControlNum(bsSrvc.getStControlNum());
								} else {
									serviceSubscriptionPromotion.setStControlNum("");
								}

								if(bsSrvc.getIsaControlNum() != null) {
									serviceSubscriptionPromotion.setIsaControlNum(bsSrvc.getIsaControlNum());
								} else {
									serviceSubscriptionPromotion.setIsaControlNum("");
								}

								if(bsSrvc.getStAcceptorLookUpAlias() != null) {
									serviceSubscriptionPromotion.setStAcceptorLookUpAlias(bsSrvc.getStAcceptorLookUpAlias());
								} else {
									serviceSubscriptionPromotion.setStAcceptorLookUpAlias("");
								}


								if(bsSrvc.isComplianceCheck()) {
									serviceSubscriptionPromotion.setComplianceCheck("Yes");
									if(bsSrvc.getMap() != null) {
										serviceSubscriptionPromotion.setMapName(bsSrvc.getMap().getMapName());
									} else {
										serviceSubscriptionPromotion.setMapName("");
									}
								} else {
									serviceSubscriptionPromotion.setComplianceCheck("No");
									serviceSubscriptionPromotion.setMapName("");
								}
								serviceSubscriptionPromotion.setBusinessServiceName(bsSrvc.getServiceType().getBusinessServiceName());
								serviceSubscriptionPromotion.setBsSRId(bsSrvc.getSrId());

								if(bsSrvc.getNotes() != null) {
									serviceSubscriptionPromotion.setBsNotes(bsSrvc.getNotes());
								} else {
									serviceSubscriptionPromotion.setBsNotes("");
								}


								serviceSubscriptionPromotion.setServiceCategoryName(srvcSubscription.getServiceCategory().getName());

								logger.debug("<=============================================================================>");					
								logger.debug(serviceSubscriptionPromotion.getPartnerName());
								logger.debug(serviceSubscriptionPromotion.getSegmentDelimiter());
								logger.debug(serviceSubscriptionPromotion.getElementDelimiter());
								logger.debug(serviceSubscriptionPromotion.getCompositeElementDelimiter());
								logger.debug(serviceSubscriptionPromotion.getRepeatDelimiter());
								logger.debug(serviceSubscriptionPromotion.getSrId());
								logger.debug(serviceSubscriptionPromotion.getNotes());//null
								logger.debug(serviceSubscriptionPromotion.getTppName());					
								logger.debug(serviceSubscriptionPromotion.getTestIsaID());
								logger.debug(serviceSubscriptionPromotion.getTestIsaQualifier());
								logger.debug(serviceSubscriptionPromotion.getTestGsId());
								logger.debug(serviceSubscriptionPromotion.getProductionIsaID());
								logger.debug(serviceSubscriptionPromotion.getProductionIsaQualifier());
								logger.debug(serviceSubscriptionPromotion.getProductionGsId());					
								logger.debug(serviceSubscriptionPromotion.getAbcProductionGsId());
								logger.debug(serviceSubscriptionPromotion.getVersionNumber());
								logger.debug(serviceSubscriptionPromotion.getGsIdVersion());//null
								logger.debug(serviceSubscriptionPromotion.getProtocolType());
								logger.debug(serviceSubscriptionPromotion.getAck());
								logger.debug(serviceSubscriptionPromotion.getAckPeriod());
								logger.debug(serviceSubscriptionPromotion.getStControlNum()); //null
								logger.debug(serviceSubscriptionPromotion.getIsaControlNum());//null
								logger.debug(serviceSubscriptionPromotion.getStAcceptorLookUpAlias());		//null	
								logger.debug(serviceSubscriptionPromotion.getComplianceCheck());
								logger.debug(serviceSubscriptionPromotion.getMapName());
								logger.debug(serviceSubscriptionPromotion.getBusinessServiceName());
								logger.debug(serviceSubscriptionPromotion.getBsSRId());
								logger.debug(serviceSubscriptionPromotion.getBsNotes());	//null				
								logger.debug(serviceSubscriptionPromotion.getServiceCategoryName());
								logger.debug("<=============================================================================>");

								serviceSubscriptionPromotions.add(serviceSubscriptionPromotion);

							}
						}
					}
				}
			}
		}
		return ResponseEntity.status(HttpStatus.OK).body(serviceSubscriptionPromotions);
	}

}
