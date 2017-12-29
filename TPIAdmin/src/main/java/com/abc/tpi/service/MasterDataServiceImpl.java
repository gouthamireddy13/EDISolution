package com.abc.tpi.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.abc.environment.model.EnvironmentInfo;
import com.abc.environment.service.EnvironmentService;
import com.abc.tpi.model.master.Ack;
import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.MapForDropDownProjection;
import com.abc.tpi.model.master.PartnerType;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceType;
import com.abc.tpi.model.service.ServiceTypeProjectionIdName;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.repository.AckRepository;
import com.abc.tpi.repository.DelimiterRepository;
import com.abc.tpi.repository.DirectionRepository;
import com.abc.tpi.repository.DocumentRepository;
import com.abc.tpi.repository.LightWellPartnerRepository;
import com.abc.tpi.repository.PartnerTypeRepository;
import com.abc.tpi.repository.ProtocolRepository;
import com.abc.tpi.repository.ServiceTypeRepository;
import com.abc.tpi.repository.TpiMapRepository;
import com.abc.tpi.repository.TppTypeRepository;
import com.abc.tpi.repository.VersionRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class MasterDataServiceImpl implements MasterDataService {

	public static final int DELIMITER_SEGMENT = 1;  	// Binary 00001
	public static final int DELIMITER_ELEMENT = 2;  	// Binary 00010
	public static final int DELIMITER_COMPOSITE = 4;  	// Binary 00100
	
	@Autowired
	private DocumentRepository documentRepository;

	@Autowired
	private ProtocolRepository protocolRepository;

	@Autowired
	private TppTypeRepository tppTypeRepository;

	@Autowired
	private VersionRepository versionRepository;
	
	@Autowired
	private DirectionRepository directionRepository;
		
	@Autowired
	private LightWellPartnerRepository lightWellPartnerRepository;
	
	@Autowired
	private DelimiterRepository delimiterRepository;
	
	@Autowired 
	AckRepository ackRepository;
	
	@Autowired
	TpiMapRepository mapRepository;
	
	@Autowired
	ServiceTypeRepository serviceTypeRepository;
	
	@Autowired
	PartnerTypeRepository partnerTypeRepository;
	
	@Autowired
	EnvironmentService environmentService;
	
	private static final Logger logger = LogManager.getLogger(MasterDataServiceImpl.class);
	
	@Transactional
	@Override
	public PartnerType savePartnerType(PartnerType partnerType) {
		
		return partnerTypeRepository.save(partnerType);
	}
	
	@Transactional
	@Override
	public void deletePartnerType(PartnerType partnerType) {		
		partnerTypeRepository.delete(partnerType);
	}
	
	@Override
	public PartnerType findPartnerTypeById(long id) {
		
		return partnerTypeRepository.findOne(id);
	}

	@Override
	public PartnerType findPartnerTypeByTypeCode(String code) {
		return partnerTypeRepository.findOneByTypeCodeIgnoreCase(code);
	}


	@Transactional
	public TppType saveTppType(TppType tppType) {

		return tppTypeRepository.save(tppType);
	}

	@Transactional
	public Protocol saveProtocol(Protocol protocol) {

		return protocolRepository.save(protocol);
	}

	@Transactional
	public void deleteTppType(TppType tppType) {
		
		tppTypeRepository.delete(tppType);
	}

	@Transactional
	public void deleteDocumentType(Document docType) {
		
		documentRepository.delete(docType);
	}

	@Transactional
	public void deleteVersion(Version version) {
		
		versionRepository.delete(version);
	}

	@Transactional
	public void deleteProtocol(Protocol protocol) {

		protocolRepository.delete(protocol);
	}

	public List<TppType> findAllTppTypes() {
		
		return tppTypeRepository.findAll(new Sort(Sort.Direction.ASC,"typeCode"));
	}

	public List<Document> findAllDocumentTypes() {

		return documentRepository.findAll(new Sort(Sort.Direction.ASC,"documentType"));
	}

	public List<Version> findAllVersions() {

		return versionRepository.findAll(new Sort(Sort.Direction.ASC, "versionNumber"));
	}

	public List<Protocol> findAllProtocols() {

		return protocolRepository.findAll(new Sort(Sort.Direction.ASC, "protocolType"));
	}

	public TppType findTppTypeById(long Id) {

		return tppTypeRepository.findOne(Id);
	}

	public Document findDocumentTypeById(long id) {
		
		return documentRepository.findOne(id);
	}

	public Version findVersionById(long id) {
		
		return versionRepository.findOne(id);
	}

	public Protocol findProtocolById(long id) {

		return protocolRepository.findOne(id);
	}

	@Override
	public TppType findTppTypeByTypeCode(short typeCode) {
		TppType result = tppTypeRepository.findOneByTypeCode((Short) typeCode);
		return result;
	}

	@Override
	public Document findDocumentTypeByDocumentType(String documentType) {
		Document result = documentRepository.findOneByDocumentType(documentType);
		return result;
	}

	@Override
	public Version findVersionByVersionNumber(int versionNumber) {
		Version version = versionRepository.findOneByVersionNumber((Integer) versionNumber);
		return version;
	}

	@Override
	public Protocol findProtocolByProtocolType(String protocolType) {
		Protocol protocol = protocolRepository.findOneByProtocolTypeIgnoreCase(protocolType);
		return protocol;
	}

	@Override
	public Direction findDirectionById(long id) {
		
		return directionRepository.findOne(id);
	}

	@Override
	public Direction findDirectionByName(String name) {
		return directionRepository.findOneByDirectionCodeIgnoreCase(name);
	}

	@Transactional
	public Direction saveDirection(Direction direction) {
		return directionRepository.save(direction);
	}

	@Transactional
	public void deleteDirection(Direction direction) {
		directionRepository.delete(direction);
	}

	@Override
	public LightWellPartner findLWPartnerById(long id) {
		return lightWellPartnerRepository.findOne(id);
	}

	@Override
	public Delimiter saveDelimiter(Delimiter delimiter) {
		return delimiterRepository.save(delimiter);
	}

	@Override
	public List<Delimiter> findAllDelimiters() {
		return delimiterRepository.findAll();
	}

	@Override
	public List<Direction> findAllDirections() {		
		return directionRepository.findAll(new Sort(Sort.Direction.ASC,"directionCode"));
	}

	@Override
	public Delimiter findDelimiterByCode(String code) {
		
		return delimiterRepository.findOneByDelimiter(code);
	}

	@Override
	public Ack findAckByCode(String ackValue) {
		return ackRepository.findOneByAckValue(ackValue);
	}

	@Override
	public Ack saveAck(Ack ack) {
		return ackRepository.save(ack);
	}

	@Override
	public Document saveDocument(Document document) {
		return documentRepository.save(document);
	}

	@Override
	public Version saveVersion(Version version) {
		return versionRepository.save(version);
	}


	@Override
	public List<Delimiter> getSegmentDelimiters() {		
		return delimiterRepository.findAllByIsSegmentTrueOrderByDelimiter();
	}

	@Override
	public List<Delimiter> getElementDelimiters() {		
		return delimiterRepository.findAllByIsElementTrueOrderByDelimiter();
	}

	@Override
	public List<Delimiter> getCompositeDelimiters() {
		return delimiterRepository.findAllByIsCompositeTrueOrderByDelimiter();
	}

	@Override

	public List<Delimiter> getRepeatDeleimiters() {
		return delimiterRepository.findAllByIsRepeatTrueOrderByDelimiter();
	}


	@Override
	public TpiMap saveMap(TpiMap map) {
		return mapRepository.save(map);
	}

	@Override
	public List<MapForDropDownProjection> findMapForServiceType(long serviceTypeId) {
		return mapRepository.namedFindMapByServiceTypeId(serviceTypeId);
	}

	@Override
	public List<MapForDropDownProjection> findMapForServiceType(ServiceType serviceType) {
		
		List<MapForDropDownProjection> result = null;
		
		if (serviceType!=null)
		{
			result=findMapForServiceType(serviceType.getId());
		}
		
		return result;
	}


	@Override
	public List<TpiMap> getAllMaps() {		
		return mapRepository.findAll(new Sort(Sort.Direction.ASC,"mapName"));
	}

	@Override
	public List<MapForDropDownProjection> getAllMapsProjections() {
		return mapRepository.findAllByOrderByMapName();
	}

	@Override
	public TpiMap findMapById(long id) {
		return mapRepository.findOneById(id);
	}

	@Override
	public MapForDropDownProjection findMapByIdProjection(long id) {
		return mapRepository.findAllById(id);
	}

	@Override
	public TpiMap findMapByName(String name) {
		return mapRepository.findOneByMapName(name);
	}




	@Override
	public ServiceType findServiceTypeByName(String name) {

		return serviceTypeRepository.findServiceTypeByBusinessServiceNameIgnoreCase(name);
	}

	@Override
	public ServiceType findServiceTypeById(Long id) {
		return serviceTypeRepository.findOne(id);
	}

	@Override
	public List<ServiceTypeProjectionIdName> findAllServiceTypeProjectionsIdName() {
		return serviceTypeRepository.findAllByOrderByBusinessServiceName();
	}

	@Override
	public List<ServiceType> findAllServiceTypes() {
		return serviceTypeRepository.findAll();
	}

	@Override
	@Transactional
	public ServiceType addMapToServiceType(Long serviceTypeId, TpiMap map) {
		if (map==null)
		{
			return null;
		}
		
		if (map.getId()==null)
		{
			TpiMap newMap = new TpiMap();
			{
				newMap.setMapName(map.getMapName());
				if (map.getMapDescription()!=null || map.getMapDescription().isEmpty())
				{
					newMap.setMapDescription(map.getMapName());
				}
				else
				{
					newMap.setMapDescription(map.getMapDescription());
				}
			}
			map = mapRepository.save(newMap);
		}
		
		ServiceType serviceType = serviceTypeRepository.findOne(serviceTypeId);
		
		if (serviceType!=null)
		{
			serviceType.addMap(map);
		}
		return serviceTypeRepository.save(serviceType);
	}
	
	@Override
	@Transactional
	public void addMapToServiceTypeInProd(ServiceType serviceType, TpiMap map) {
	
		logger.debug("Invoked addMapToServiceTypeInProd().");
		EnvironmentInfo envInfoForServiceAccess = environmentService.findEnvironmentInfoByParamName("HIGHER_ENV_MAP_API");
		serviceType = serviceTypeRepository.findOne(serviceType.getId());
		if(envInfoForServiceAccess != null && envInfoForServiceAccess.getParamVal().length() != 0) {
			logger.debug("Url for Service Type search in higher env: " + envInfoForServiceAccess.getParamVal());
			String urlStr = envInfoForServiceAccess.getParamVal();
			URI urlPost;
			try {
				urlPost = new URI(urlStr);

			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
			headers.add("Accept", "application/json");
			headers.add("Content-Type", "application/json");
			restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
			HttpEntity<String> requestProcStatus = new HttpEntity<String>("parameters", headers);
			ResponseEntity<String> response = restTemplate.exchange(urlPost, HttpMethod.GET, requestProcStatus, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			List<ServiceType> serviceTypes;
			ServiceType existingST = null;
			
			serviceTypes = objectMapper.readValue(response.getBody(), new TypeReference<List<ServiceType>>(){});
			ServiceCategory result = null;
			for(ServiceType st : serviceTypes){
				if(st.getServiceCategory().getName().equalsIgnoreCase(serviceType.getServiceCategory().getName()) && st.getBusinessServiceName().equalsIgnoreCase(serviceType.getBusinessServiceName())){
					existingST = st;
					logger.debug("Got match for Service Type against API - "+urlPost);
				}
			}
			if(existingST != null) {
				String urlPost1= urlStr+existingST.getId().intValue()+"/maps";
				logger.debug("Making API call for Compliance Map. API - "+ urlPost1);
				urlPost = new URI(urlPost1);
				HttpEntity<TpiMap> requestProcStatusPOST = new HttpEntity<TpiMap>(map, headers);
				ResponseEntity<String> responsePOST = restTemplate.exchange(urlPost, HttpMethod.POST, requestProcStatusPOST, String.class);
			}
			
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}

	@Override
	@Transactional
	public void addMapToServiceType(Long serviceTypeId, Long mapId) {
		ServiceType serviceType = serviceTypeRepository.findOne(serviceTypeId);
		TpiMap map = mapRepository.findOne(mapId);
		if (serviceType!=null && map!=null)
		{
			serviceType.addMap(map);
		}
		serviceTypeRepository.save(serviceType);
	}

	@Override
	@Transactional
	public void deleteMapForServiceType(Long serviceTypeId, TpiMap map) {
		boolean changeMade = false;
		if (map==null)
		{
			return;
		}		
		ServiceType serviceType = serviceTypeRepository.findOne(serviceTypeId);
		
		if (serviceType.getMaps()!=null)
		{
			for (Iterator<TpiMap>iter = serviceType.getMaps().iterator();iter.hasNext();)
			{
				TpiMap nextMap = iter.next();
				if (nextMap.getId()==map.getId())
				{
					iter.remove();
					changeMade = true;
					break;
				}
			}
		}
		if (changeMade)
		{
			serviceTypeRepository.save(serviceType);
		}
		
	}

	@Override
	public <T> List<T> findAllServiceTypes(Class<T> projection) {
		return serviceTypeRepository.findAllByOrderByBusinessServiceName(projection);
	}

	@Override
	public List<ServiceType> getServiceTypesForServiceCategory(Long serviceCategoryId) {
		return serviceTypeRepository.namedFindAllServiceTypesByServiceCategoryId(serviceCategoryId);
	}

	@Override
	public <T> List<T> getServiceTypesForServiceCategory(Long serviceCategoryId, Class<T> projection) {
		return serviceTypeRepository.findServiceTypeByServiceCategoryIdOrderByBusinessServiceName(serviceCategoryId,projection);
	}
/*
	@Override
	public List<SdServiceAccess> findAllBusinessUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdServiceAccess saveBussinessUnit(SdServiceAccess businessUnit) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<SdServiceCategoryLookup> findAllSDServiceCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdServiceCategoryLookup saveServiceCategories(SdServiceCategoryLookup serviceCategory) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteServiceCategoriesById(String id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<SdServiceCategoryDef> findAllSDServiceCategoryDef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SdServiceCategoryDef saveServiceCategoriesDef(SdServiceCategoryDef serviceCategoryDef) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteServiceCategoryDefById(String id) {
		// TODO Auto-generated method stub
		
	}*/


}
