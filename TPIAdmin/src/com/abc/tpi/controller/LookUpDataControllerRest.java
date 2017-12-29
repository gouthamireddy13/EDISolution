package com.abc.tpi.controller;

import java.net.URLDecoder;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abc.dashboard.model.SdServiceAccess;
import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.ServiceSubscriptionService;

@RestController
public class LookUpDataControllerRest {

	private static final Logger logger = LogManager.getLogger(LookUpDataControllerRest.class);

	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	
/*	@RequestMapping(value = { "/saveSdBU" }, method = { RequestMethod.POST })
	public ResponseEntity<SdServiceAccess> BussinesUnController(HttpServletRequest request, HttpServletResponse response,
			@RequestBody SdServiceAccess businessUnit) throws TpiRepositoryException {

		Logger logger = LogManager.getLogger(PartnerControllerRest.class);
		logger.debug("Invoked LookUpDataControllerRest.BussinesUnController()");

		SdServiceAccess result = null;
		try {
			result = masterDataService.saveBussinessUnit(businessUnit);
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception ex) {
			logger.error("Failed to Save SdBusinnesUnit. ", ex);
			throw new TpiRepositoryException("Failed to Save SdBusinnesUnit.");
		}
	}
*/
	
	@RequestMapping(value = { "/documents"}, method = { RequestMethod.GET })
	Collection<Document> getDocuments() throws TpiRepositoryException
	{
		logger.debug("Invoked getDocuments()");
		
		Collection<Document> result = null;
		
		try
		{
			result = masterDataService.findAllDocumentTypes();
		}		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of Documents. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Documents.");
		}		
		return result;	
	}
	
	@RequestMapping(value = { "/protocols"}, method = { RequestMethod.GET })
	Collection<Protocol> getProtocols() throws TpiRepositoryException
	{
		logger.debug("Invoked getProtocols()");
		
		Collection<Protocol> result = null;
		
		try
		{
			result =  masterDataService.findAllProtocols();
		}		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of Protocols. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Protocols.");
		}
		
		return result;	
	}
	
/*	@RequestMapping(value = { "/findBURecords"}, method = { RequestMethod.GET })
	Collection<SdServiceAccess> getProtocols1() throws TpiRepositoryException
	{
		logger.debug("Invoked getProtocols()");
		
		Collection<Protocol> result = null;
		
		Collection<SdServiceAccess> businessUnitsList=null;
		try
		{
			businessUnitsList=masterDataService.findAllBusinessUnit();
		}
		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of Protocols. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Protocols.");
		}
		
		return businessUnitsList;
	}*/
	
	@RequestMapping(value = { "/tppTypes"}, method = { RequestMethod.GET })
	Collection<TppType> getTppTypes() throws TpiRepositoryException
	{
		logger.debug("Invoked getTppTypes()");
		
		Collection<TppType> result = null;
		
		try
		{
			result =  masterDataService.findAllTppTypes();
		}		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of TppType. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of TppType.");
		}
		
		return result;	
	}
	
	@RequestMapping(value = { "/versions"}, method = { RequestMethod.GET })
	Collection<Version> getVersions() throws TpiRepositoryException
	{
		logger.debug("Invoked getVersions()");
		
		Collection<Version> result = null;
		
		try
		{
			result = masterDataService.findAllVersions();
		}		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of Versions. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Versions.");
		}
		
		return result;	
	}
	
	@RequestMapping(value = { "/directions"}, method = { RequestMethod.GET })
	Collection<Direction> getDirections() throws TpiRepositoryException
	{
		logger.debug("Invoked getDirections()");
		
		Collection<Direction> result = null;
		
		try
		{
			result = masterDataService.findAllDirections();
		}		
		catch (Exception ex) 
		{
			logger.error("Failed to Retrieve List of Directions. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Directions.");
		}
		
		return result;	
	}
	
	@RequestMapping(value = { "/delimiters"}, method = { RequestMethod.GET })
	Collection<Delimiter> getDelimiters(HttpServletRequest request, HttpServletResponse response) throws TpiRepositoryException
	{
		logger.debug("Invoked getDelimiters()");
		Collection<Delimiter> result = null;

		try {
			if (request.getParameter("type") != null) {
				logger.debug("Querty string: " + request.getQueryString());
				String type = URLDecoder.decode(request.getParameter("type"), "UTF-8");

				switch (type) {
				case "segment":
					result = masterDataService.getSegmentDelimiters();
					break;
				case "element":
					result = masterDataService.getElementDelimiters();
					break;
				case "composite":
					result = masterDataService.getCompositeDelimiters();
					break;
				case "repeat":
					result = masterDataService.getRepeatDeleimiters();
					break;
				default:
					result = null;
					break;
				}
			} else {
				result = masterDataService.findAllDelimiters();
			}
		} catch (Exception ex) {
			logger.error("Failed to Retrieve List of Directions. ", ex);
			throw new TpiRepositoryException("Failed to Retrieve List of Delimiters.");
		}
		return result;
	}
	
	@RequestMapping(value={"/maps"},method={RequestMethod.GET})
	List<?> getEdiMaps(@RequestParam(name="projection",required=false) String projection, 
			  					@RequestParam(name="serviceTypeId",required=false) Long serviceTypeId)
	{
	
		List<?> result = null;
		
		if (serviceTypeId==null)
		{
			if (projection!=null && projection.compareTo("mapForDropDownProjection")==0)
			{
				result = masterDataService.getAllMapsProjections();
			}
			else
			{
				result = masterDataService.getAllMaps();
			}
		}
		else
		{
				result = masterDataService.findMapForServiceType(serviceTypeId);
		}
		return result;		
	}
	
	@RequestMapping(value={"/maps/{id}"},method={RequestMethod.GET})
	Object getEdiMap(@RequestParam(name="projection",required=false) String projection,@PathVariable(name="id") long id)
	
	{
		Object result = null;
		
		if (projection!=null && projection.compareTo("mapForDropDownProjection")==0)
		{
			result = masterDataService.findMapByIdProjection(id);
		}
		else
		{			
			result = masterDataService.findMapById(id);
		}
		return result;		
	}
}
