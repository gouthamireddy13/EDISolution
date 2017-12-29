package com.abc.tpi.service.soap;

import java.util.ArrayList;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.tpi.common.exceptions.TpiValidationException;
import com.abc.tpi.domain.soap.ContactWS;
import com.abc.tpi.domain.soap.PartnerWS;
import com.abc.tpi.domain.soap.TppLoadResult;
import com.abc.tpi.domain.soap.TppWS;
import com.abc.tpi.domain.soap.TransactionWS;
import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.partner.PartnerGroup;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.service.MasterDataService;
import com.abc.tpi.service.PartnerGroupService;
import com.abc.tpi.service.PartnerService;
import com.abc.tpi.service.TppService;

@Component
@WebService(serviceName = "tpi", endpointInterface = "com.abc.tpi.service.soap.TpiWebService")
public class TpiWebServiceImpl implements TpiWebService {

	private static final Logger logger = LogManager.getLogger(TpiWebServiceImpl.class);
	
	@Autowired 
	TppService tppService;
	
	@Autowired 
	MasterDataService masterDataService;
	
	@Autowired
	PartnerService partnerService;

	@Autowired
	PartnerGroupService partnerGroupService;
	
	@Override
	@WebMethod(operationName = "uploadTpps")
	public List<TppLoadResult> uploadTpps(List<TppWS> tpps) {

		List<TppLoadResult> result = new ArrayList<TppLoadResult>();

		for (TppWS tpp : tpps) {
			logger.debug("Name: " + tpp.getName() + " Type: " + tpp.getType());
			TppLoadResult tppObject = new TppLoadResult();
			tppObject.setName(tpp.getName());

			try 
			{
				com.abc.tpi.model.tpp.Tpp tppEntity = tppService.findByNameFullStringMatchIgnoreCase(tpp.getName());

				if (tppEntity == null) {
					tppEntity = new com.abc.tpi.model.tpp.Tpp();
					tppEntity.setName(tpp.getName());

					TppType tppType = masterDataService.findTppTypeByTypeCode(tpp.getType());
					
					if (tppType == null)
					{
						throw new TpiValidationException("Invalid TPP Type specified " + tpp.getType());
					}
					
					if (tpp.getProtocols()==null || tpp.getProtocols().size()<1)
					{
						throw new TpiValidationException("Missing Protocols information");
					}
					
					if (tpp.getTransactions()==null || tpp.getTransactions().size()<1)
					{
						throw new TpiValidationException("Missing Transaction information");
					}
					
					if  (tpp.getContacts()==null || tpp.getContacts().size()<1)
					{
						throw new TpiValidationException("Missing Contacts information");
					}
					
					tppEntity.setType(tppType);
					
					if (tpp.getLw()!=null)
					{
						LightWellPartner lwPartner = new LightWellPartner();
						lwPartner.setActive(true);
						lwPartner.setNotes(tpp.getLw().getNotes());
						lwPartner.setOrganizationName(tpp.getLw().getOrganization());
						lwPartner.setProductionGsId(tpp.getLw().getGsa_prod_id());
						lwPartner.setProductionIsaID(tpp.getLw().getIsa_prod_id());
						lwPartner.setProductionIsaQualifier(tpp.getLw().getIsa_prod_qual());
						lwPartner.setTestGsId(tpp.getLw().getGsa_test_id());
						lwPartner.setTestIsaID(tpp.getLw().getIsi_test_id());
						lwPartner.setTestIsaQualifier(tpp.getLw().getIsa_test_qual());
						tppEntity.setLightWellPartner(lwPartner);
					}

					for (TransactionWS trans : tpp.getTransactions())
					{
						Direction direction = masterDataService.findDirectionByName(trans.getDirection());
						if (direction==null)
						{
							throw new TpiValidationException("Invlaid DIRECTION value: " + trans.getDirection());
						}
						
						Document document = masterDataService.findDocumentTypeByDocumentType(trans.getType());
						if (document==null)
						{
							throw new TpiValidationException("Invlaid Type value: " + trans.getType());
						}
						
						Version version = masterDataService.findVersionByVersionNumber(trans.getVersion());
						if (version==null)
						{
							throw new TpiValidationException("Invlaid Version value: " + trans.getVersion());
						}
						
						com.abc.tpi.model.tpp.Transaction transaction = new com.abc.tpi.model.tpp.Transaction();
						transaction.setDirection(direction);
						transaction.setDocument(document);
						transaction.setVersion(version);
						tppEntity.addTransaction(transaction);
					}
					
					for (ContactWS contact : tpp.getContacts())
					{
						Document transactionType = masterDataService.findDocumentTypeByDocumentType(contact.getTransactionType());
						
						if (transactionType==null)
						{
							throw new TpiValidationException("Invlaid Transaction Type value: " + contact.getTransactionType());
						}
						
						ContactDetail tppContactDetail = new ContactDetail();

						tppContactDetail.setContactName(contact.getName());
						tppContactDetail.setContactEmail(contact.getEmail());
						tppContactDetail.setContactTitle(contact.getTitle());
						tppContactDetail.setBusinessPhone(contact.getPhone());
						tppContactDetail.setTransactionType(transactionType);
						
						tppEntity.addContact(tppContactDetail);
					}
					
					for (String protocolName: tpp.getProtocols())
					{
						Protocol protocol = masterDataService.findProtocolByProtocolType(protocolName);
						if (protocol==null)
						{
							throw new TpiValidationException("Invlaid Protocol value: " + protocolName);
						}
						
						tppEntity.addProtocol(protocol);
					}
					
					com.abc.tpi.model.tpp.Tpp newTpp = tppService.saveTpp(tppEntity);
					tppObject.setId(newTpp.getId());
					tppObject.setMessage("success");
				}
			} 
			catch (NumberFormatException numException) 
			{
				tppObject.setMessage("Error converting TPP/Document Type of " + tpp.getType() + " to a valid numeric value");
			}
			
			catch (TpiValidationException validationException)
			{
				tppObject.setMessage("Data Validation Error: " + validationException);
			}

			catch (Exception ex) 
			{
				tppObject.setMessage("Unexpected error: " + ex.getMessage());
			}
			
			finally
			{
				result.add(tppObject);
			}
		}

		return result;
	}

	@Override
	@WebMethod(operationName = "uploadPartners")
	public List<TppLoadResult> uploadPartners(List<PartnerWS> partners) {
		List<TppLoadResult> result = new ArrayList<TppLoadResult>();

		for (PartnerWS partnerWs : partners) {
			logger.debug("Name: " + partnerWs.getName());
			
			TppLoadResult resultObject = new TppLoadResult();
			
			resultObject.setName(partnerWs.getName());

			try 
			{
				Partner partnerEntity = null;
				
				if (!partnerService.isPartnerExist(partnerWs.getName())) {
					
					partnerEntity = new Partner();
					
					partnerEntity.setPartnerName(partnerWs.getName());

					String groupName = partnerWs.getGroup();
					String subGroupName = partnerWs.getSubGroup();
					
					if (groupName==null || subGroupName == null)
					{
						throw new TpiValidationException("Missing Group/SubGroup information");
					}
					
					PartnerGroup partnerGroup = partnerGroupService.getPartnerGroupByGroupSubGroupName(groupName, subGroupName);
					
					if (partnerGroup == null)
					{
						throw new TpiValidationException("Group/Sub Group combination is not found " + groupName + "/" + subGroupName);
					}
					
					if (partnerWs.getContacts()==null || partnerWs.getContacts().size()<1)
					{
						throw new TpiValidationException("Missing Contacts information");
					}

					partnerEntity.setPartnerGroup(partnerGroup);

					for (ContactWS contact : partnerWs.getContacts())
					{
						Document transactionType = masterDataService.findDocumentTypeByDocumentType(contact.getTransactionType());
						
						if (transactionType==null)
						{
							throw new TpiValidationException("Invlaid Transaction Type value: " + contact.getTransactionType());
						}
						
						
						ContactDetail partnerContactDetail = new ContactDetail();

						partnerContactDetail.setContactName(contact.getName());
						partnerContactDetail.setContactEmail(contact.getEmail());
						partnerContactDetail.setContactTitle(contact.getTitle());
						partnerContactDetail.setBusinessPhone(contact.getPhone());					
						partnerContactDetail.setTransactionType(transactionType);
											
						partnerEntity.addContact(partnerContactDetail);
					}
					
					Partner newPartner = partnerService.createPartner(partnerEntity);
					resultObject.setId(newPartner.getId());
					resultObject.setMessage("success");
				}
			} 
			catch (TpiValidationException validationException)
			{
				resultObject.setMessage("Data Validation Error: " + validationException);
			}

			catch (Exception ex) 
			{
				resultObject.setMessage("Unexpected error: " + ex.getMessage());
			}
			
			finally
			{
				result.add(resultObject);
			}
		}

		return result;
	}

}
