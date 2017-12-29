package com.abc.dashboard.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.abc.dashboard.controller.SdServiceAccessController;
import com.abc.dashboard.model.PcceAbcBusinessService;
import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;
import com.abc.dashboard.model.SdBusinessService;
import com.abc.dashboard.model.SdBusinessSubUnit;
import com.abc.dashboard.model.SdBusinessUnit;
import com.abc.dashboard.model.SdServiceAccess;
import com.abc.dashboard.model.SdServiceCategoryDef;
import com.abc.dashboard.model.SdServiceType;
import com.abc.dashboard.repository.SdBusinessServiceRepository;
import com.abc.dashboard.repository.SdServiceAccessRepository;
import com.abc.dashboard.repository.SdServiceCategoryDefRepository;
import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.repository.LightWellPartnerRepository;
import com.abc.tpi.service.CommonJpaService;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.utils.SeedDataInsertStatMsg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("sdServiceCategoryService")
public class SdServiceCategoryServiceImpl implements SdServiceCategoryService {

	private static final Logger logger = LogManager.getLogger(SdServiceCategoryServiceImpl.class);

	@Autowired
	CommonJpaService commonJpaService;

	@Autowired
	SdServiceCategoryDefRepository sdServiceCategoryDefRepository;

	@Autowired
	SdServiceAccessRepository sdServiceAccessRepository;

	@Autowired
	SdBusinessServiceRepository sdBusinessServiceRepository;

	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;

	@Autowired
	PcceAbcBusinessUnitLookupService pcceAbcBusinessUnitLookupService;
	
	@Autowired
	PcceAbcBusinessLookupService pcceAbcBusinessLookupService;
	

	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;

	@Autowired
	EnvironmentService environmentService;
	
	@Autowired
	SdMasterDataService sdMasterDataService;
	
	@Autowired
	MasterDataService masterDataService;
	
	
	
	@PersistenceContext	
	EntityManager em;

	@Override
	public SdServiceCategoryDef saveSdServiceCategoryDef(SdServiceCategoryDef sdServiceCategoryDef) {
		return sdServiceCategoryDefRepository.save(sdServiceCategoryDef);
	}

	@Override
	public <T> T findSdServiceCategoryByName(String name, Class<T> type) {
		return sdServiceCategoryDefRepository.namedFindServiceCategoryByName(name, type);
	}

	@Override
	public  <T> List<T> getAllSdServiceCategoryDefs(Class<T> type) {
		return sdServiceCategoryDefRepository.findAllByOrderByServiceCategory(type);
	}

	@Override
	public void deleteSdServiceCategoryDef(long id) {
		sdServiceCategoryDefRepository.delete(id);		
	}

	@Override
	public void deleteSdServiceCategoryDef(SdServiceCategoryDef sdServiceCategoryDef) {
		sdServiceCategoryDefRepository.delete(sdServiceCategoryDef);		
	}

	@Override
	public SdServiceCategoryDef findSdServiceCategoryById(long id) {
		return sdServiceCategoryDefRepository.findOne(id);
	}

	@Override
	public SdServiceAccess saveSdServiceAccess(SdServiceAccess sdServiceAccess) {
/*		List<SdServiceAccess> sdServAccessList = getAllSdServiceAccess(SdServiceAccess.class);
		for(SdServiceAccess sdServAccess: sdServAccessList) {
			
			if(sdServiceAccess.getServiceCategoryDef().getId().compareTo(sdServAccess.getServiceCategoryDef().getId()) == 0) {
				if(sdServiceAccess.getServicePreamble().equalsIgnoreCase(sdServAccess.getServicePreamble())) {
					if(sdServiceAccess.getDestinationId().equalsIgnoreCase(sdServAccess.getDestinationId())) {
						if(sdServiceAccess.getSourceId().equalsIgnoreCase(sdServAccess.getSourceId())) {
							if(sdServiceAccess.getServiceType().getId().compareTo(sdServAccess.getId()) == 0) {
								return null;
							}
						}
					}
				}
			}
		}*/
		return sdServiceAccessRepository.save(sdServiceAccess);
	}	

	@Override
	public SdServiceAccess saveSdServiceAccessWithPCCEInsert(SdServiceAccess sdServiceAccess) {
		logger.debug("Invoked saveSdServiceAccessWithPCCEInsert().");
		SdServiceAccess result = saveSdServiceAccess(sdServiceAccess);
		if(result != null) {
			EnvironmentInfo envInfo = environmentService.findEnvironmentInfoByParamName("READONLY_DB");
			//<----------------------------------------START: STEP#1 Insert in PCCE Schema in same environment---------------------------------------->
			if(envInfo != null) {
				PcceAbcBusinessUnitLookup pcceAbcBusinessUnitLookup = new PcceAbcBusinessUnitLookup();
				SdServiceCategoryDef sdServiceCatDef = findSdServiceCategoryById(sdServiceAccess.getServiceCategoryDef().getId());
				pcceAbcBusinessUnitLookup.setBusinessUnit(sdServiceCatDef.getBusinessUnit().getName());
				pcceAbcBusinessUnitLookup.setSubBusinessUnit(sdServiceCatDef.getBusinessSubUnit().getSubUnitName());
				pcceAbcBusinessUnitLookup.setServicePreamble(sdServiceAccess.getServicePreamble());
				if(sdServiceAccess.getDestinationId() != null) {
					LightWellPartner lwp = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,sdServiceAccess.getDestinationId().trim(),null);
					if(lwp != null) {
						if(envInfo != null && envInfo.getParamVal().equalsIgnoreCase("Y")) {
							pcceAbcBusinessUnitLookup.setDestinationId(lwp.getProductionGsId());
						} else {
							pcceAbcBusinessUnitLookup.setDestinationId(lwp.getTestGsId());
						}
					}
				} 
				if(sdServiceAccess.getSourceId() != null) {
					LightWellPartner lwp = serviceSubscriptionService.findOneLightWellPartnerByAttributeValues(null,null,null,sdServiceAccess.getSourceId().trim(),null);
					if(lwp != null) {
						if(envInfo != null && envInfo.getParamVal().equalsIgnoreCase("Y")) {
							pcceAbcBusinessUnitLookup.setSourceId(lwp.getProductionGsId());
						} else {
							pcceAbcBusinessUnitLookup.setSourceId(lwp.getTestGsId());
						}
					}
				} 
				pcceAbcBusinessUnitLookupService.savePcceAbcBusinessUnitLookup(pcceAbcBusinessUnitLookup);

				//<----------------------------------------END: STEP#1---------------------------------------------------------------------->
				//<----------------------------------------START: STEP#2 & 3 Insert in higher environment & insert in PCCE Schema in higher environment---------------------------------------->
				EnvironmentInfo envInfoForServiceAccess = environmentService.findEnvironmentInfoByParamName("TRIGGER_API");
				logger.debug("Url for Save Service Access to higher env: " + envInfoForServiceAccess.getParamVal());
				if(envInfoForServiceAccess != null && envInfoForServiceAccess.getParamVal().length() != 0) {
					try {
						String rowLine = "";
						StringBuffer sb = new StringBuffer();
						sb.append(sdServiceCatDef.getServiceCategory().getName());
						sb.append(",");
						sb.append(sdMasterDataService.findServiceTypeById(sdServiceAccess.getServiceType().getId()).getName());
						//sb.append(getSdServiceAccessById(result.getId(), SdServiceAccess.class).getServiceType().getName());
						sb.append(",");
						//SdServiceAccess newSdServiceAccess = new SdServiceAccess();
						//newSdServiceAccess.setServiceCategoryDef(sdServiceCatDef);

						if(sdServiceAccess.getSourceId() != null) {
							//newSdServiceAccess.setSourceId(sdServiceAccess.getSourceId());
							sb.append(sdServiceAccess.getSourceId());
							sb.append(",");
						} else {
							sb.append(",");
						}
						if(sdServiceAccess.getDestinationId() != null) {
							//newSdServiceAccess.setDestinationId(sdServiceAccess.getDestinationId());
							sb.append(sdServiceAccess.getDestinationId());
							sb.append(",");
						} else {
							sb.append(",");
						}
						sb.append(sdServiceCatDef.getBusinessUnit().getName());
						sb.append(",");
						sb.append(sdServiceCatDef.getBusinessSubUnit().getSubUnitName());
						sb.append(",");
						sb.append(sdServiceAccess.getServicePreamble());
						sb.append(",");
						rowLine = sb.toString();
						logger.debug("Request data for Post to higher environment: " + rowLine);
						//newSdServiceAccess.setServicePreamble(sdServiceAccess.getServicePreamble());
						//newSdServiceAccess.setServiceType(sdServiceAccess.getServiceType());
						String urlforPost = envInfoForServiceAccess.getParamVal().trim()+"SDSERVICEACCESS";
						logger.debug("Url For Post: " + urlforPost);
						URI urlPost = new URI(urlforPost);
						RestTemplate restTemplate = new RestTemplate();
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						List<MediaType> accept = new ArrayList();
						accept.add(MediaType.APPLICATION_JSON);
						headers.setAccept(accept);
						MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();  
						restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
						body.add("rowLine", rowLine); 
						HttpEntity<?> requestProcStatus = new HttpEntity(body, headers); 
						String response = restTemplate.postForObject(urlPost, requestProcStatus, String.class);
						logger.debug("Response from REST call: " + response);
						SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
						if(response != null) {
							ObjectMapper objectMapper = new ObjectMapper();					
							try {
								seedDataInsertStatMsg = objectMapper.readValue(response, new TypeReference<SeedDataInsertStatMsg>(){});
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.debug("Value of SeedDataInsertStatMsg: " + seedDataInsertStatMsg);
						}

					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (HttpClientErrorException e2) {
						// TODO Auto-generated catch block
						//seedDataInsertStatMsg.setReturnFlag(2);
						//seedDataInsertStatMsg.setStatusMsg("HttpClientErrorException");
						e2.printStackTrace();
					} /*catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				}
			}
			//<----------------------------------------END: STEP#2 & 3---------------------------------------------------------------------->

		}

		return result;
	}

	@Override
	public <T> List<T> getAllSdServiceAccess(Class<T> entityCls) {
		if (entityCls == SdServiceAccess.class)
		{			
			return commonJpaService.findGenericEntitiesUsingEntityGraph(entityCls, "SdServiceAccess.graphWebView", "SdServiceAccess.namedFindAllServiceAccess");
		}
		else
		{
			return sdServiceAccessRepository.findAllBy(entityCls);
		}
	}

	@Override
	public <T> T getSdServiceAccessById(long id, Class<T> entityCls) {
		if (entityCls == SdServiceAccess.class)
		{
			return commonJpaService.findGenericEntityUsingEntityGrpah(entityCls, new Long(id), "SdServiceAccess.graphWebView");
		}
		else
		{
			return sdServiceAccessRepository.findOneById(entityCls, id);
		}
	}

	@Override
	public void deleteSdServiceAccessDef(SdServiceAccess sdServiceAccess) {
		sdServiceAccessRepository.delete(sdServiceAccess);

	}

	@Override
	public void deleteSdServiceAccessDef(long id) {
		sdServiceAccessRepository.delete(id);

	}

	public <T> List<T> getServiceCategoriesWithPartnerSubscription(Class<T> entityCls) {
		return sdServiceCategoryDefRepository.namedFindServiceCategoriesWithPartnerSubscription(entityCls);
	}

	@Override
	public List<SdBusinessService> getAllSdBusinessServices() {
		return sdBusinessServiceRepository.findAll();
	}

	@Override
	public <T> List<T> getAllSdBusinessServices(Class<T> entityCls) {
		if (entityCls == SdBusinessService.class)
		{
			return commonJpaService.findGenericEntitiesUsingEntityGraph(entityCls,
					"SdBusinessService.graphWebView","SdBusinessService.namedFindAllBusinessServices");
		}
		else
		{
			return sdBusinessServiceRepository.namedFindAllBusinessServices(entityCls);
		}
	}

	@Override
	public <T> T findBusinessServiceById(long id,Class<T> type) {
		return sdBusinessServiceRepository.namedFindSdBusinessServiceById(id, type);
	}

	@Override
	@Transactional
	public SdBusinessService saveSdBusinessService(SdBusinessService sdBusinessService) {
		return sdBusinessServiceRepository.save(sdBusinessService);
	}
	//=============================================
	@Override
	public SdBusinessService saveSdBusinessServiceWithPCCEInsert(SdBusinessService sdBusinessService) {
		logger.debug("Invoked saveSdBusinessServiceWithPCCEInsert().");
		PcceAbcBusinessService businessService = null;
		SdBusinessService result = saveSdBusinessService(sdBusinessService);
		if(result != null) {
			EnvironmentInfo envInfo = environmentService.findEnvironmentInfoByParamName("READONLY_DB");
			//<----------------------------------------START: STEP#1 Insert in PCCE Schema in same environment---------------------------------------->
			if(envInfo != null) {
				businessService = new PcceAbcBusinessService();
				SdServiceCategoryDef sdServiceCatDef = findSdServiceCategoryById(sdBusinessService.getServiceCategory().getId());
				ServiceCategory serviceCategory = sdServiceCatDef.getServiceCategory();
				SdBusinessUnit sdBusinessUnit = sdServiceCatDef.getBusinessUnit();
				SdBusinessSubUnit sdBusinessSubUnit = sdServiceCatDef.getBusinessSubUnit();
				Document document = masterDataService.findDocumentTypeById(sdBusinessService.getDocument().getId());
				Direction direction = masterDataService.findDirectionById(sdBusinessService.getDirection().getId());
				SdServiceType sdServiceType = sdMasterDataService.findServiceTypeById(sdBusinessService.getServiceType().getId());
				businessService.setServiceKey(sdBusinessService.getServiceKey());
				businessService.setServiceCategory(serviceCategory.getName());
				businessService.setCategoryId(Long.toString(serviceCategory.getId()));
				businessService.setServiceLevel(sdBusinessService.getServiceLevel());
				businessService.setServiceLevelId(sdBusinessService.getServiceLevelId());
				businessService.setBusinessUnit(sdBusinessUnit.getName());
				businessService.setBusinessSubUnit(sdBusinessSubUnit.getSubUnitName());
				businessService.setServicePreamble(sdBusinessService.getServicePreamble());
				businessService.setTransactionType(document.getDocumentDescription());
				businessService.setDirection(direction.getDirectionCode());
				businessService.setServiceType(sdServiceType.getName());
				businessService.setIntCompSendBu(sdBusinessSubUnit.getSubUnitName());
				businessService.setServiceDesc(sdBusinessService.getServiceDescription());
				pcceAbcBusinessLookupService.savePcceAbcBusinessService(businessService);
				
				//<----------------------------------------END: STEP#1---------------------------------------------------------------------->
				//<----------------------------------------START: STEP#2 & 3 Insert in higher environment & insert in PCCE Schema in higher environment---------------------------------------->
				
				EnvironmentInfo envInfoForBusinessService = environmentService.findEnvironmentInfoByParamName("TRIGGER_API");
				logger.debug("Url for Save Business Service to higher env: " + envInfoForBusinessService.getParamVal());
				if(envInfoForBusinessService != null && envInfoForBusinessService.getParamVal().length() != 0) {
					try {
						String rowLine = "";
						StringBuffer sb = new StringBuffer();
						sb.append(sdBusinessService.getServiceKey());
						sb.append(",");
						sb.append(sdServiceCatDef.getServiceCategory().getName());
						sb.append(",");
						sb.append(Long.toString(sdServiceCatDef.getServiceCategory().getId()));
						sb.append(",");
						sb.append(sdBusinessService.getServiceLevel());
						sb.append(",");
						sb.append(sdBusinessService.getServiceLevelId());
						sb.append(",");
						sb.append(sdServiceCatDef.getBusinessUnit().getName());
						sb.append(",");
						sb.append(sdServiceCatDef.getBusinessSubUnit().getSubUnitName());
						sb.append(",");
						sb.append(sdBusinessService.getServicePreamble());
						sb.append(",");
						sb.append(document.getDocumentDescription());
						sb.append(",");
						sb.append(direction.getDirectionCode());
						sb.append(",");
						sb.append(sdServiceType.getName());
						sb.append(",");
						sb.append(sdServiceCatDef.getBusinessSubUnit().getSubUnitName());
						sb.append(",");
						sb.append(sdBusinessService.getServiceDescription());
						sb.append(",");
						
						//==============================================================================
						
						rowLine = sb.toString();
						logger.debug("Request data for Post to higher environment: " + rowLine);
						String urlforPost = envInfoForBusinessService.getParamVal().trim()+ "SDBUSINESSSERVICE";
						logger.debug("Url For Post: " + urlforPost);
						URI urlPost = new URI(urlforPost);
						RestTemplate restTemplate = new RestTemplate();
						HttpHeaders headers = new HttpHeaders();
						headers.setContentType(MediaType.MULTIPART_FORM_DATA);
						List<MediaType> accept = new ArrayList();
						accept.add(MediaType.APPLICATION_JSON);
						headers.setAccept(accept);
						MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();  
						restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
						body.add("rowLine", rowLine); 
						HttpEntity<?> requestProcStatus = new HttpEntity(body, headers); 
						String response = restTemplate.postForObject(urlPost, requestProcStatus, String.class);
						
						logger.debug("Response from REST call: " + response);
						SeedDataInsertStatMsg seedDataInsertStatMsg = new SeedDataInsertStatMsg();
						if(response != null) {
							ObjectMapper objectMapper = new ObjectMapper();					
							try {
								seedDataInsertStatMsg = objectMapper.readValue(response, new TypeReference<SeedDataInsertStatMsg>(){});
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							logger.debug("Value of SeedDataInsertStatMsg: " + seedDataInsertStatMsg);
						}

					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (HttpClientErrorException e2) {
						e2.printStackTrace();
					} 
				}
				//<----------------------------------------END: STEP#2 & 3---------------------------------------------------------------------->
			}
		}

			return result;
		}

	//=========================================

	@Override
	@Transactional
	public void deleteSdBusinessService(SdBusinessService sdBusinessService) {
		sdBusinessServiceRepository.delete(sdBusinessService);

	}

	@Override
	@Transactional
	public void deleteSdBusinessService(long id) {
		sdBusinessServiceRepository.delete(id);		
	}

	@Override
	public SdBusinessService findBusinessServiceById(long id) {
		return commonJpaService.findGenericEntityUsingEntityGrpah(SdBusinessService.class, id, "SdBusinessService.graphWebView");
	}

	@Override
	public <T> T findBusinessServiceByServiceKey(String serviceKey, Class<T> type) {
		// TODO Auto-generated method stub
		return sdBusinessServiceRepository.namedFindSdBusinessServiceByServiceKey(serviceKey, type);
	}

	@Override
	public <T> T findSdServiceCategoryByCategoryId(int categoryId, Class<T> entityCls) {
		return sdServiceCategoryDefRepository.namedFindServiceCategoryByCategoryId(categoryId, entityCls);
	}

	@Override
	public List<LightWellPartner> getLightWellPartnersWithSdServiceCategoryMembership() {

		return lightWellPartnerRepository.namedFindLightWellWithSdServiceCategoryDefMembership();
	}
	
	

	@Override
	public SdServiceCategoryDef findOneSdServiceCategoryByCategoryId(int categoryId) {

		SdServiceCategoryDef resultObject = sdServiceCategoryDefRepository.findFirstByCategoryID(categoryId);
		return resultObject;
	}

	@Override
	public SdServiceCategoryDef findOneSdServiceCategoryByServiceCategory(String serviceCategoryName) {

		SdServiceCategoryDef resultObject = sdServiceCategoryDefRepository.findFirstByServiceCategoryNameIgnoreCase(serviceCategoryName.trim());
		return resultObject;
	}

	@Override
	public SdServiceAccess findOneBySourceAndDestinationIdsIgnoreCase(String sourceId, String destinationId) {		
		return sdServiceAccessRepository.findOneBySourceIdIgnoreCaseAndDestinationIdIgnoreCase(sourceId, destinationId);
	}
	
	@Override 
	public SdServiceAccess findOneBySrceAndDestIdsAndSrvcPreambleAndSrvcCatDef(String sourceId, String destinationId, Long serviceCategoryDefId, String servicePreamble, Long serviceTypeId ) {
		SdServiceCategoryDef sdServiceCategoryDef = findSdServiceCategoryById(serviceCategoryDefId);
		SdServiceType serviceType = sdMasterDataService.findServiceTypeById(serviceTypeId);
		//return sdServiceAccessRepository.findOneBySourceIdIgnoreCaseAndDestinationIdIgnoreCaseAndServiceCategoryDefAndServicePreambleAndSdServiceType(sourceId, destinationId, sdServiceCategoryDef, servicePreamble, serviceType);
		
		return sdServiceAccessRepository.namedFindSdServiceAccessBySrceIdAndDestIdAndSrvcCatDefIdAndSrvcPreambleAndSdSrvcType(sourceId, destinationId, serviceCategoryDefId, servicePreamble, serviceTypeId, SdServiceAccess.class);
	}
	
}
