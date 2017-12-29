/**
 * 
 */
package com.abc.tpi.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.common.exceptions.TpiValidationException;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.Service;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.service.ServiceSubscriptionListViewProjection;
import com.abc.tpi.service.ServiceSubscriptionService;

/**
 * @author ARINDAMSIKDAR
 *
 */

@RestController
public class IntercompanySubscriptionControllerRest {

	private static final Logger logger = LogManager.getLogger(IntercompanySubscriptionControllerRest.class);
	
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	

	
	
	@RequestMapping(value = {"/intercompanySubscriptions"}, method= {RequestMethod.GET})
	public ResponseEntity<List<?>> getIntercompanySubscriptions(@RequestParam(name="projection",required=false) String projectionName,
														   @RequestParam(name="srId",required=false) String srId)
	{
		logger.debug("Invoked IntercompanySubscriptionControllerRest.getIntercompanySubscriptions()");
		
		List<ServiceSubscription> serviceSubscriptions=null;
		
		if (projectionName!=null)
		{
			switch (projectionName)
			{
			case "serviceSubscriptionListView":
				List<ServiceSubscriptionListViewProjection>  dropDownValues = serviceSubscriptionService.getServiceSubscriptionsListView();
				return ResponseEntity.status(HttpStatus.OK).body(dropDownValues);				
			default:
				serviceSubscriptions = null;
				break;
			}			
		}
		else
		{
			if (srId==null)
				serviceSubscriptions = serviceSubscriptionService.getAllServiceSubscriptions();
			else
				serviceSubscriptions = (List<ServiceSubscription>) serviceSubscriptionService.findServiceSubscriptionBySrId(srId);
		}
		
		return ResponseEntity.status(HttpStatus.OK).body(serviceSubscriptions);
	}
	
	
	@RequestMapping(value = {"/intercompanySubscriptions/{subscriptionId}"}, method= {RequestMethod.GET})
	public ResponseEntity<ServiceSubscription> getIntercompanySubscription(@PathVariable("subscriptionId") long subscriptionId) throws TpiRepositoryException 
	{
		logger.debug("Invoked ServiceSubscriptionControllerRest.getServiceSubscription()");
		ServiceSubscription result = serviceSubscriptionService.getServiceSubscription(subscriptionId);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	@RequestMapping(value = { "/intercompanySubscriptions/{subscriptionId}" }, method = { RequestMethod.DELETE })
	public ResponseEntity<Void> deleteIntercompanySubscriptionByID(@PathVariable("subscriptionId") long subscriptionId) throws TpiRepositoryException {

		Logger logger = LogManager.getLogger(PartnerGroupControllerRest.class);
		logger.debug("Invoked PartnerController.deleteIntercompanySubscriptionByID()");

		try 
		{
			long id = subscriptionId;
			serviceSubscriptionService.deleteServiceSubscription(id);
			return ResponseEntity.noContent().build();
		} 
		 catch (ResourceNotFoundException e) 
		{
		 	return ResponseEntity.notFound().build();
		}
		catch (Exception ex) {
			logger.error("Failed to Delete Service Subscription with ID " + subscriptionId, ex);
			throw new TpiRepositoryException("Failed to Delete Service Subscription with ID " + subscriptionId);
		}
	}
	
	
	
	@RequestMapping(value = {"/intercompanySubscriptions/{ssID}/services"}, method= {RequestMethod.GET})
	public ResponseEntity<Collection<Service>> getIntercompanySubscriptionServices(@PathVariable("ssID") long ssID ) throws TpiRepositoryException
	{
		
		logger.debug("Invoked ServiceSubscriptionControllerRest.getIntercompanySubscriptionServices()");			
		ServiceSubscription serviceSubscription = serviceSubscriptionService.getServiceSubscription(ssID);		
		return ResponseEntity.status(HttpStatus.OK).body(serviceSubscription.getServices());
	}
	
	@RequestMapping(value = {"/intercompanySubscriptions/{ssID}/services/{serviceID}/businessServices"}, method= {RequestMethod.GET})
	public ResponseEntity<Collection<BusinessService>> getIntercompanySubscriptionBusinessServices(@PathVariable("ssID") long ssID,@PathVariable("serviceID") long serviceId ) throws TpiRepositoryException
	{
		
		logger.debug("Invoked ServiceSubscriptionControllerRest.getIntercompanySubscriptionBusinessServices()");			
		return ResponseEntity.status(HttpStatus.OK).body(serviceSubscriptionService.findBusinessServicesByServiceSubscriptionAndServiceId(ssID,serviceId));		
	}

	@RequestMapping(value = {"/intercompanySubscriptions/{ssID}/services/{serviceID}"}, method= {RequestMethod.GET})
	public ResponseEntity<Collection<Service>> getIntercompanySubscriptionService(@PathVariable("ssID") long ssID,@PathVariable("serviceID") long serviceId ) throws TpiRepositoryException
	{
		
		logger.debug("Invoked ServiceSubscriptionControllerRest.getIntercompanySubscriptionService()");			
		ServiceSubscription serviceSubscription = serviceSubscriptionService.getServiceSubscription(ssID);		
		return ResponseEntity.status(HttpStatus.OK).body(serviceSubscription.getServices());
	}
	
	@RequestMapping(value = {"/intercompanySubscriptions/exists"}, method= {RequestMethod.GET},params = { "partnerId", "serviceCategoryId" })
	public ResponseEntity<Boolean> isIntercompanySubscriptionExists( @RequestParam(name="partnerId",required=true) Long partnerId, @RequestParam(name="serviceCategoryId", required=true) Long serviceCategoryId)
	{
		logger.debug("Invoked ServiceSubscriptionControllerRest.isIntercompanySubscriptionExists()");
		
		boolean result = false;
		
		Collection<Long> subscriptionIds = serviceSubscriptionService.findServiceSubscriptionByPartnerAndServiceCategory(partnerId, serviceCategoryId);
		
		if (subscriptionIds!=null && subscriptionIds.size()>0)
		{
			result = true;
		}
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
