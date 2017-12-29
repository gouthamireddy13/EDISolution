package com.abc.tpi.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.poc.TpiManagedEntity;
import com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo;
import com.abc.tpi.model.search.projections.ServiceSubscription.ServiceSubscriptionWithGsIdIsaId;
import com.abc.tpi.model.service.BusinessService;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceCategoryForDropDownProjection;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.service.ServiceSubscriptionListViewProjection;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.Transaction;
import com.abc.tpi.repository.BusinessServiceRepository;
import com.abc.tpi.repository.LightWellPartnerRepository;
import com.abc.tpi.repository.ServiceCategoryRepository;
import com.abc.tpi.repository.ServiceRepository;
import com.abc.tpi.repository.ServiceSubscriptionRepository;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.repository.TppRepository;
import com.abc.tpi.utils.SeedDataInsertStatMsg;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@Service
public class ServiceSubscriptionServiceImpl implements ServiceSubscriptionService {

	@Autowired
	ServiceCategoryRepository serviceCategoryRepository;

	@Autowired
	ServiceTypeRepository servivceTypeRepository;

	@Autowired
	TppRepository tppRepository;

	@Autowired
	TppService	tppService;

	@Autowired
	ServiceSubscriptionRepository serviceSubscriptionRepository;

	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;

	@Autowired
	BusinessServiceRepository businessServiceRepository;

	@Autowired
	ServiceRepository serviceRepository;

	@Autowired
	SeedDataLoadService seedDataLoadService;

	@Autowired
	EnvironmentService environmentService;

	@PersistenceContext	
	EntityManager em;

	private static final Logger logger = LogManager.getLogger(ServiceSubscriptionServiceImpl.class);

	public ServiceSubscriptionServiceImpl()
	{
		super();
		logger.debug("ServiceSubscriptionServiceImpl initialized");
	}

	@Override
	public boolean saveServiceCategoryInProd(ServiceCategory serviceCategory, LightWellPartner lightWellPartner) {

		logger.debug("Invoked saveServiceCategoryInProd().");
		EnvironmentInfo envInfoForServiceAccess = environmentService.findEnvironmentInfoByParamName("HIGHER_ENV_SC_API");
		if(envInfoForServiceAccess != null && envInfoForServiceAccess.getParamVal().length() != 0) {
			try {
				logger.debug("Url for Service Category search in higher env: " + envInfoForServiceAccess.getParamVal());
				String urlStr = envInfoForServiceAccess.getParamVal();
				URI urlPost = new URI(urlStr);
				RestTemplate restTemplate = new RestTemplate();
				MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
				headers.add("Accept", "application/json");
				headers.add("Content-Type", "application/json");
				restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
				HttpEntity<String> requestProcStatus = new HttpEntity<String>("parameters", headers);
				ResponseEntity<String> response = restTemplate.exchange(urlPost, HttpMethod.GET, requestProcStatus, String.class);
				ObjectMapper objectMapper = new ObjectMapper();
				List<ServiceCategory> serviceCategories;
				ServiceCategory existingSC = null;
				try {
					serviceCategories = objectMapper.readValue(response.getBody(), new TypeReference<List<ServiceCategory>>(){});
					ServiceCategory result = null;
					for(ServiceCategory sc : serviceCategories){
						if(sc.getName().equalsIgnoreCase(serviceCategory.getName())){
							existingSC = sc;
							logger.debug("Got match for Service Category against API - "+urlPost);
						}
					}
					if(existingSC != null) {
						String urlPost1= urlStr+existingSC.getId().intValue()+"/lightWellPartners";
						logger.debug("Making API call for ABC ID Maintenance. API - "+ urlPost1);
						urlPost = new URI(urlPost1);
						HttpEntity<LightWellPartner> requestProcStatusPOST = new HttpEntity<LightWellPartner>(lightWellPartner, headers);
						ResponseEntity<String> responsePOST = restTemplate.exchange(urlPost, HttpMethod.POST, requestProcStatusPOST, String.class);
						ObjectMapper objectMapperPOST = new ObjectMapper();
						ServiceCategory resultPOST = null;
						resultPOST = objectMapper.readValue(response.getBody(), new TypeReference<ServiceCategory>(){});
						if(resultPOST != null) {
							ServiceCategory scHigherEnv = objectMapper.readValue(response.getBody(), new TypeReference<ServiceCategory>(){});
							return true;
						} else {
							return false;
						}
					}
					/*				Set<LightWellPartner> lwpTestList = serviceCategory.getLightWellPartners();

				if(existingSC != null) {

					Set<LightWellPartner> lwpProdList = existingSC.getLightWellPartners();
					boolean isExist = false;
					for(LightWellPartner lwp : lwpTestList) {
						for(LightWellPartner lwpInner : lwpProdList) {							
							if(lwp.getTestGsId().equalsIgnoreCase(lwpInner.getTestGsId()) || lwp.getTestIsaID().equalsIgnoreCase(lwpInner.getTestIsaID()) || lwp.getTestIsaQualifier().equalsIgnoreCase(lwpInner.getTestIsaQualifier())) {
								isExist = true;
							}
							if(lwp.getProductionGsId().equalsIgnoreCase(lwpInner.getProductionGsId()) || lwp.getProductionIsaID().equalsIgnoreCase(lwpInner.getProductionIsaID()) || lwp.getProductionIsaQualifier().equalsIgnoreCase(lwpInner.getProductionIsaQualifier())) {
								isExist = true;
							}						

						}
						if(!isExist) {
							existingSC.addLightWellPartner(lwp);
						} else {
							isExist = false;
						}

					}

					HttpEntity<ServiceCategory> requestProcStatusPOST = new HttpEntity<ServiceCategory>(existingSC, headers);
					ResponseEntity<String> responsePOST = restTemplate.exchange(urlPost, HttpMethod.POST, requestProcStatusPOST, String.class);
					ObjectMapper objectMapperPOST = new ObjectMapper();

					ServiceCategory resultPOST = null;
					resultPOST = objectMapper.readValue(response.getBody(), new TypeReference<ServiceCategory>(){});
					if(resultPOST != null) {
						return true;
					} else {
						return false;
					}

				} else {
					ServiceCategory scProd = findServiceCategoryById(serviceCategory.getId());
					StringBuffer sb = null;
					for(LightWellPartner lw : lwpTestList) {
						sb = new StringBuffer();
						sb.append(scProd.getName());
						sb.append(",");
						sb.append(lw.getTestIsaID());
						sb.append(",");
						sb.append(lw.getTestIsaQualifier());
						sb.append(",");
						sb.append(lw.getTestGsId());
						sb.append(",");
						sb.append(lw.getProductionIsaID());
						sb.append(",");
						sb.append(lw.getProductionIsaQualifier());
						sb.append(",");
						sb.append(lw.getProductionGsId());
						sb.append(",");

						SeedDataInsertStatMsg seedDataInsertStatMsg = seedDataLoadService.makeApiCallForUploadToHigherEnv(sb.toString(), "ABCIDMAINTENANCE");

						if(seedDataInsertStatMsg != null) {
							return true;
						} else {
							return false;
						}

					}

				}*/
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return false;
	}

	@Transactional
	public List<ServiceType> getBusinessServiceForTppAndServiceCat(long serviceCategoryId, long tppId) throws TpiRepositoryException {

		List<ServiceType> result = null;
		if (tppId<=0) 
		{
			result = servivceTypeRepository.namedFindAllServiceTypesByServiceCategoryId(serviceCategoryId);
		}
		else
		{
			//get Transactions associated with the TPP
			Tpp tpp = tppService.findTppById(tppId);

			if (tpp != null)
			{
				for (Transaction transaction : tpp.getTransactions()) {
					List<ServiceType> serviceTypes = null;
					if (transaction.getDocument().getDocumentType().equalsIgnoreCase("ALL"))
					{
						serviceTypes = servivceTypeRepository.namedFindServiceTypesForServiceCategoryAndTransactionDocumentAll
								(serviceCategoryId,
										transaction.getDirection().getId());
					}
					else
					{
						serviceTypes = servivceTypeRepository
								.namedFindServiceTypesForServiceCategoryAndTransaction(
										serviceCategoryId,
										transaction.getDirection().getId(), 
										transaction.getDocument().getId());
					}

					if (serviceTypes!=null)
					{
						if (result==null)
						{
							result = serviceTypes;
						}
						else
						{
							result.addAll(serviceTypes);
						}						
					}
				}

				if (result!=null)
				{
					Set<ServiceType> distinctSet = new HashSet<ServiceType>(result);
					result.clear();
					result.addAll(distinctSet);
				}
			}
			else
			{
				throw new TpiRepositoryException("Tpp with id : " + tppId + " not found");
			}
		}

		return result;
	}

	@Transactional
	public ServiceSubscription saveServiceSubscription(ServiceSubscription serviceSubscription) throws TpiRepositoryException
	{
		for (com.abc.tpi.model.service.Service service: serviceSubscription.getServices())
		{

			if (service.getTpp() !=null)
			{
				Tpp tpp = tppRepository.getOne(service.getTpp().getId());
				LightWellPartner tppLwPartner = tpp.getLightWellPartner();

				if (tppLwPartner!=null)
				{
					if (service.getLightWellPartner()!=null)
					{
						if (service.getLightWellPartner().getId()!=null && service.getLightWellPartner().getId().compareTo(tppLwPartner.getId())!=0)
						{
							service.setLightWellPartner(null);
							service.setLightWellPartner(tppLwPartner);
						}
					}
					else
					{
						service.setLightWellPartner(tppLwPartner);
					}
				}		
			}
		}

		return serviceSubscriptionRepository.save(serviceSubscription);
	}

	@Override
	public List<ServiceSubscription> getAllServiceSubscriptions() 
	{
		return serviceSubscriptionRepository.findAll();
	}

	@Override
	public List<ServiceType> getAllServiceTypes() {
		return servivceTypeRepository.findAll();
	}

	@Override
	public List<ServiceCategory> getAllServiceCategories() {
		return serviceCategoryRepository.findAll();
	}

	@Override
	public List<ServiceCategory> getAllServiceCategoriesOrderByName() {		
		return serviceCategoryRepository.findAll(new Sort(Direction.ASC,"name"));
	}

	@Override
	@Transactional
	public ServiceCategory saveServiceCategory(ServiceCategory serviceCategory) {
		return serviceCategoryRepository.save(serviceCategory);
	}

	@Override
	public ServiceCategory findServiceCategoryById(long id) {
		return serviceCategoryRepository.findOne(id);
	}

	@Override
	public ServiceType saveServiceType(ServiceType serviceType) {
		return servivceTypeRepository.save(serviceType);
	}

	@Override
	public List<ServiceCategoryForDropDownProjection> getServiceCategoriesForDropDown() {		
		return serviceCategoryRepository.findDistinctByOrderByName();
	}

	@Override
	public List<ServiceSubscriptionListViewProjection> getServiceSubscriptionsListView() {		
		return serviceSubscriptionRepository.findAllByOrderByPartnerPartnerName();
	}

	@Override
	public ServiceSubscription getServiceSubscription(long id) {
		return serviceSubscriptionRepository.findOne(id);
	}

	@Override
	@Transactional
	public void deleteServiceSubscription(long id) {
		serviceSubscriptionRepository.delete(id);		
	}

	@Override
	@Transactional
	public void deleteServiceSubscription(ServiceSubscription serviceSubscription) {
		serviceSubscriptionRepository.delete(serviceSubscription);

	}

	@Override
	public List<ServiceCategory> findAllByCategoryName(String name) {

		return serviceCategoryRepository.findAllByName(name);
	}

	@Override
	public void deleteServiceCategory(long id) {
		serviceCategoryRepository.delete(id);

	}

	@Override
	public LightWellPartner findLightWellParnterByServiceCategoryLWid(long serviceCategoryId, long lightWellId) {
		return lightWellPartnerRepository.namedFindLightWellPartnerByServiceCategoryIdAndLwId(serviceCategoryId, lightWellId);
	}

	@Override
	public ServiceCategory findServiceCategoryByNameIgnoreCase(String name) {
		return serviceCategoryRepository.findByNameIgnoreCase(name);
	}

	@Override
	public Collection<BusinessService> findBusinessServicesByServiceId(long serviceId) {
		return businessServiceRepository.namedFindBusinessServicesByServiceId(serviceId);
	}

	@Override
	public Collection<BusinessService> findBusinessServicesByServiceSubscriptionAndServiceId(long serviceSubscription,
			long serviceId) {
		return businessServiceRepository.namedFindBusinessServicesByServiceSubscriptionAndServiceId(serviceSubscription, serviceId);
	}

	@Override
	public com.abc.tpi.model.service.Service findServiceByServiceIdForSubscription(long serviceSubscription, long serviceId) {
		return serviceRepository.namedFindByServiceSubscriptionIdAndId(serviceSubscription, serviceId);
	}

	@Override
	public BusinessService findBusinessServiceByIdForSubscription(long serviceSubscription, long serviceId,
			long businessServiceId) {

		return businessServiceRepository.namedFindBusinessServicesByServiceSubscriptionAndServiceIdAndId(serviceSubscription,serviceId,businessServiceId);
	}

	@Override
	public Collection<Long> findServiceSubscriptionByPartnerAndServiceCategory(long partnerId, long serviceCategoryId) {

		return serviceSubscriptionRepository.namedFindByParnterIdAndServiceCategoryId(partnerId, serviceCategoryId);
	}

	@Override
	public Collection<ServiceSubscriptionWithGsIdIsaId> getAllServiceSubscriptionsWithGsIdIsId() {
		return serviceSubscriptionRepository.namedGetServiceSubscriptionWithGsIdIsaId();
	}

	@Override
	public Collection<ServiceSubscription> findServiceSubscriptionBySrId(String srId) {
		return serviceSubscriptionRepository.namedFindBySRId(srId);
	}

	@Override
	public List<BusinessServiceWithParentInfo> findBusinessServiceWithParentBySrId(String srId) {		
		return serviceSubscriptionRepository.namedFindBusinessServicesForSrId(srId);
	}

	@Override
	public List<BusinessServiceWithParentInfo> findBusinessServiceWithParentBySrIdContainsIgnoreCase(String srId) {
		String param = "%" + srId + "%";
		return serviceSubscriptionRepository.namedFindBusinessServicesForSrIdIgnoreCase(param);
	}

	@Override
	public ServiceSubscription findServiceSubscriptionById(Long id) { 
		return serviceSubscriptionRepository.findOne(id);
	}

	//Added by Arindam Sikdar
	@Override
	public BusinessService findBusinessServiceById(Long id) { 
		return businessServiceRepository.findOne(id);
	}

	@Override
	public <T extends TpiManagedEntity> T findEntityUsingEntityGrpah(Class<T> entityCls, Long id,
			String entityGraphName) 
	{
		Map<String,Object> props = new HashMap<String,Object>();
		props.put("javax.persistence.fetchgraph",em.getEntityGraph(entityGraphName));

		return em.find	(entityCls,id, props);
	}

	@Override
	public List<BusinessServiceWithParentInfo> findBusinessServiceWithParentByPartnerName(String partnerName) {
		return serviceSubscriptionRepository.namedFindBusinessServicesByPartnerName(partnerName);
	}

	@Override
	public List<BusinessServiceWithParentInfo> findBusinessServiceWithParentByPartnerNameContainsIgnoreCase(String partnerName) {
		String param = "%" + partnerName + "%";
		return serviceSubscriptionRepository.namedFindBusinessServicesByPartnerName(param);
	}

	@Override
	public Integer findServiceSubscriptionVersionById(long id) {		
		return serviceSubscriptionRepository.namedGetVersionNumberById(id);
	}

	@Override
	public Collection<Long> getServiceIdsForServiceSubscription(long id) {
		return serviceSubscriptionRepository.namedGetServicesIdsById(id);
	}
	//Added by Arindam Sikdar
	@Override
	public Collection<Long> getServiceSubscriptionIdsForService(long id) {
		return serviceSubscriptionRepository.namedGetServiceSubscriptionIdsById(id);
	}

	@Override
	public Collection<Long> getBusinessServiceIdsForService(long id) {
		return serviceRepository.namedGetBusinessServicesForServiceById(id);
	}

	@Override
	public Collection<Long> getServiceIdsForBusinessService(long id) {
		return serviceRepository.namedGetServiceForBusinessServiceById(id);
	}

	@Override
	public Integer findServiceVersionById(long serviceId) {

		return serviceRepository.namedFindVersionNumById(serviceId);
	}

	@Override
	public Integer findBusinessServiceVersionById(long serviceId) {
		return businessServiceRepository.namedFindBusinessServicesVersionNumByServiceId(serviceId);
	}

	@Override
	public LightWellPartner findLightWellPartner(String testIsaID, String testIsaQualifier, String testGsId,
			String productionIsaID, String productionIsaQualifier, String productionGsId, boolean isActive) {		
		return lightWellPartnerRepository.namedFindLightWellPartner(testIsaID, testIsaQualifier,testGsId, productionIsaID, productionIsaQualifier,productionGsId, isActive);
	}

	@Override
	public LightWellPartner findOneLightWellPartnerByAttribute(String attName, String value) {

		LightWellPartner result = null;

		switch (attName.toUpperCase())
		{
		case "TESTISAID":
			result = lightWellPartnerRepository.findFirstByTestIsaIDLikeIgnoreCase(value);
			break;		
		case "PRODISAID":
			result = lightWellPartnerRepository.findFirstByProductionIsaIDLikeIgnoreCase(value);
			break;
		case "TESTGSID":
			result = lightWellPartnerRepository.findFirstByTestGsIdLikeIgnoreCase(value);
			break;		
		case "PRODGSID":
			result = lightWellPartnerRepository.findFirstByProductionGsIdLikeIgnoreCase(value);
			break;
		default:
			result = null;
			break;
		}
		return result;
	}

	@Override
	public LightWellPartner findOneLightWellPartnerByAttributeValues(String testIsaId, String testGsId,
			String prodIsaId, String prodGsId, Long lwId) {

		LightWellPartner lwResult = null;
		boolean hasLWID = false;
		boolean hasAbcIds = false;

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<LightWellPartner> c = cb.createQuery(LightWellPartner.class);
		Root<LightWellPartner> lwPartner = c.from(LightWellPartner.class);
		c.select(lwPartner);

		List<Predicate> criteria = new ArrayList<Predicate>();

		if (testIsaId != null && !testIsaId.isEmpty()) {
			ParameterExpression<String> p = cb.parameter(String.class, "testIsaId");
			criteria.add(cb.equal(lwPartner.get("testIsaID"), p));
			hasAbcIds = true;
		}

		if (prodIsaId != null && !prodIsaId.isEmpty()) {
			ParameterExpression<String> p = cb.parameter(String.class, "productionIsaID");
			criteria.add(cb.equal(lwPartner.get("productionIsaID"), p));
			hasAbcIds = true;
		}

		if (testGsId != null && !testGsId.isEmpty()) {
			ParameterExpression<String> p = cb.parameter(String.class, "testGsId");
			criteria.add(cb.equal(lwPartner.get("testGsId"), p));
			hasAbcIds = true;
		}

		if (prodGsId != null && !prodGsId.isEmpty()) {
			ParameterExpression<String> p = cb.parameter(String.class, "productionGsId");
			criteria.add(cb.equal(lwPartner.get("productionGsId"), p));
			hasAbcIds = true;
		}

		if (lwId!=null)
		{
			ParameterExpression <Long> idParam = cb.parameter(Long.class,"lwId");		
			Predicate pId = cb.notEqual(lwPartner.get("id"), idParam);
			hasLWID = true;


			if (criteria.size()==1)
			{
				//Predicate andId = cb.and(criteria.get(0));
				//Predicate andLwId = cb.and(pId);
				criteria.add(pId);
			}
			else if (criteria.size()>0)

			{
				Predicate andId = cb.and(pId);
				Predicate orIds = cb.or(criteria.toArray(new Predicate[0]));
				criteria.clear();
				criteria.add(andId);
				criteria.add(orIds);
			}
			else
			{
				throw new RuntimeException("Invalid criteria. At least one ISA or GS ids are expected");
			}
		}
		else
		{
			if (criteria.size()>0)
			{
				Predicate orIds = cb.or(criteria.toArray(new Predicate[0]));
				criteria.clear();
				criteria.add(orIds);
			}
		}



		if (criteria.size() == 0 ) {
			throw new RuntimeException("Invalid criteria. At least one ISA or GS ids are expected");
		} else if (criteria.size() == 1) {
			c.where(criteria.get(0));
		} else {
			c.where(criteria.toArray(new Predicate[0]));
		}



		TypedQuery<LightWellPartner> q = em.createQuery(c);
		if (testIsaId != null && !testIsaId.isEmpty()) {
			q.setParameter("testIsaId", testIsaId);
		}
		if (prodIsaId != null && !prodIsaId.isEmpty()) {
			q.setParameter("productionIsaID", prodIsaId);
		}
		if (testGsId != null && !testGsId.isEmpty()) {
			q.setParameter("testGsId", testGsId);
		}
		if (prodGsId != null && !prodGsId.isEmpty()) {
			q.setParameter("productionGsId", prodGsId);
		}

		if (lwId!=null)
		{
			q.setParameter("lwId", lwId);
		}

		List<LightWellPartner> lwResults = q.setMaxResults(1).getResultList();

		if (lwResults!=null && lwResults.size()>0)
		{
			lwResult = lwResults.get(0);
		}

		return lwResult;
	}

	@Override
	public void deleteLightWellPartner(long id) {
		lightWellPartnerRepository.delete(id);

	}

}
