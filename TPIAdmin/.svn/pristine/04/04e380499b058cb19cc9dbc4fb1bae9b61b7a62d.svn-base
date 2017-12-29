package com.abc.tpi.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.tpi.controller.SearchControllerRest;
import com.abc.tpi.model.master.Document;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.partner.PartnerGroup;
import com.abc.tpi.model.promotion.ServiceSubscriptionPromotion;
import com.abc.tpi.model.reporting.LightWellPartnerRecord;
import com.abc.tpi.model.reporting.PartnerRecord;
import com.abc.tpi.model.reporting.PartnerSubscriptionRecord;
import com.abc.tpi.model.reporting.TPPRecord;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.Transaction;
import com.abc.tpi.repository.BusinessServiceRepository;
import com.abc.tpi.repository.LightWellPartnerRepository;

@Service
public class ReportingServiceImpl implements ReportingService 
{	
	private static final Logger logger = LogManager.getLogger(ReportingServiceImpl.class);
	@Autowired
	BusinessServiceRepository businessServiceRepository;
	
	@Autowired
	PartnerService partnerService;
	
	@Autowired
	TppService tppService;
	
	@Autowired
	LightWellPartnerRepository lightWellPartnerRepository;

	@Override
	public List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsByServiceSubscriptionId(long serviceSubscriptionId) 
	{
		return businessServiceRepository.namedInEnvelopeByServiceSubscriptionId(serviceSubscriptionId);
	}
	
	@Override
	public List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsBySrId(String srId) 
	{
		return businessServiceRepository.namedInEnvelopeBySrId(srId);
	}

	@Override
	public List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsBySrIdBusinessSrvcId(String srId,
			List<Long> businessServiceIds) {		
		return businessServiceRepository.namedInEnvelopeBySrIdBusinessServiceIds(srId,businessServiceIds);
	}
	
	//==================================================================
	
	
	public List<PartnerRecord> getPartnerRecordsByPartnerId(List<Long> prId) {
		
		List<PartnerRecord> listprtnrrec = new ArrayList<PartnerRecord>();
		Set<ContactDetail> contdetails = null ;
		
		
		
		for(Long id:prId){
			if(id != null){
				System.out.println("Id is->"+id);
		Partner prtnr = partnerService.findPartnerById(id);
		if(prtnr!= null){
			 contdetails = prtnr.getContactDetails();
		}
		
		PartnerGroup prtnrgrp = prtnr.getPartnerGroup();
		if(contdetails != null){
		for(ContactDetail contactDetail : contdetails) {
			PartnerRecord prtnrrecord = new PartnerRecord();
			prtnrrecord.setPartnerName(prtnr.getPartnerName());
			prtnrrecord.setPartnerGroupName(prtnrgrp.getGroupName());
			prtnrrecord.setPartnerSubGroupName(prtnrgrp.getSubGroupName());
			
			Document doc =contactDetail.getTransactionType();
			prtnrrecord.setContactName(contactDetail.getContactName());
			prtnrrecord.setBusinessCountry(contactDetail.getBusinessPhoneCountry());
			prtnrrecord.setBusinessPhone(contactDetail.getBusinessPhone());
			prtnrrecord.setBusinessExt(contactDetail.getBusinessPhoneExt());
			
			prtnrrecord.setMobileCountry(contactDetail.getMobilePhoneCountry());
			prtnrrecord.setMobilePhone(contactDetail.getMobilePhone());
			prtnrrecord.setMobileExt(contactDetail.getMobilePhoneExt());
			prtnrrecord.setPersonalCountry(contactDetail.getPersonalPhoneCountry());
			prtnrrecord.setPersonalPhone(contactDetail.getPersonalPhone());
			prtnrrecord.setPersonalExt(contactDetail.getPersonalPhoneExt());
			prtnrrecord.setPrimaryEmail(contactDetail.getContactEmail());
			prtnrrecord.setSecondaryEmail(contactDetail.getContactEmail2());
			prtnrrecord.setTitle(contactDetail.getContactTitle());
			prtnrrecord.setTransactionType(doc.getDocumentType());
			listprtnrrec.add(prtnrrecord);
		}}}
	}
		return  listprtnrrec;
	}

	@Override
	public List<PartnerSubscriptionRecord> getPartnerSubscriptionRecordsByPartnerName(String partnerName) {		
		return businessServiceRepository.namedInEnvelopeByPartnerName(partnerName);
	}

	@Override
	public List<LightWellPartnerRecord> getLightWellIdentityForLwIds(long partnerId, List<Long> lwIds) {
		return lightWellPartnerRepository.namedFindLightWellIdentityByPartnerIdLwIds(partnerId, lwIds);
	}

	@Override
	public List<TPPRecord> getTPPRecordsByTPPId(List<Long> tppId) {
		// TODO Auto-generated method stub

		List<TPPRecord> listtpprec = new ArrayList<TPPRecord>();
		for(Long id:tppId){
			if(id != null){
				Tpp  tpp = tppService.findTppById(id);
				logger.debug("TPP  id  :"+tpp.getId());
				if(tpp != null){

					Set<ContactDetail> cset = tpp.getContactDetails();
					if(cset != null){
						for(ContactDetail cd : cset) {	
							Set<Transaction> tset = tpp.getTransactions();
							if(tset != null){
								for(Transaction t : tset) {
									TPPRecord tpprecord = new TPPRecord();
									tpprecord.setTppName(tpp.getName());
									tpprecord.setTppTypeCode(tpp.getType().getTypeCode().toString());
									logger.debug("TPP LightWellPartner  :"+tpp.getLightWellPartner());
									if(tpp.getLightWellPartner() != null){
										tpprecord.setTestISAid(tpp.getLightWellPartner().getTestIsaID());
										tpprecord.setTsetISAQual(tpp.getLightWellPartner().getTestIsaQualifier());
										tpprecord.setTestGSid(tpp.getLightWellPartner().getTestGsId());
										tpprecord.setProdISAid(tpp.getLightWellPartner().getProductionIsaID());
										tpprecord.setProdISAQual(tpp.getLightWellPartner().getProductionIsaQualifier());
										tpprecord.setProdGSid(tpp.getLightWellPartner().getProductionGsId());
									}
									Set<Protocol> pset = tpp.getProtocols();
									Iterator<Protocol> psetItr = pset.iterator();
									while(psetItr.hasNext()){
										Protocol p = psetItr.next();
										if(p.getId()==1)
										{
											tpprecord.setProtocol1(p.getProtocolType());
										}else if(p.getId()==2)
										{
											tpprecord.setProtocol2(p.getProtocolType());
										}else if(p.getId()==3)
										{
											tpprecord.setProtocol3(p.getProtocolType());
										}else if(p.getId()==4)
										{
											tpprecord.setProtocol4(p.getProtocolType());
										}else if(p.getId()==5)
										{
											tpprecord.setProtocol5(p.getProtocolType());
										}

									}

									
									tpprecord.setTransactionDirection(t.getDirection().getDirectionCode());
									tpprecord.setTransactionType(t.getDocument().getDocumentType());
									tpprecord.setTransactionVersion(t.getVersion().getVersionDescription());
										
									tpprecord.setSegmentDelimiter(tpp.getSegmentDelimiter().getDelimiter());
									tpprecord.setElementDelimiter(tpp.getElementDelimiter().getDelimiter());
									tpprecord.setCompositeDelimiter(tpp.getCompositeElementDelimiter().getDelimiter());
									tpprecord.setRepeatDelimiter(tpp.getRepeatDelimiter().getDelimiter());

									
									tpprecord.setContactName(cd.getContactName());
									tpprecord.setTitle(cd.getContactTitle());
									tpprecord.setBusinessCountry(cd.getBusinessPhoneCountry());
									tpprecord.setBusinessPhone(cd.getBusinessPhone());
									tpprecord.setBusinessExt(cd.getBusinessPhoneExt());

									tpprecord.setMobileCountry(cd.getMobilePhoneCountry());
									tpprecord.setMobilePhone(cd.getMobilePhone());
									tpprecord.setMobileExt(cd.getMobilePhoneExt());

									tpprecord.setPersonalCountry(cd.getPersonalPhoneCountry());
									tpprecord.setPersonalPhone(cd.getPersonalPhone());
									tpprecord.setPersonalExt(cd.getPersonalPhoneExt());

									tpprecord.setPrimaryEmail(cd.getContactEmail());
									tpprecord.setSecondaryEmail(cd.getContactEmail2());

									tpprecord.setTransactionType1(cd.getTransactionType().getDocumentType());

									

									listtpprec.add(tpprecord);}}}
					}}}}
		return  listtpprec;
	}
	
		
}
