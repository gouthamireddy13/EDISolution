package com.abc.tpi.service;

import java.util.List;

import com.abc.tpi.common.exceptions.TpiRepositoryException;
import com.abc.tpi.model.partner.ContactDetail;
import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.partner.PartnerGroup;
import com.abc.tpi.model.partner.PartnerNameProjection;

public interface PartnerService {
	
	Partner createPartner(Partner partner) throws TpiRepositoryException;
	Partner updatePartner(Partner partner);
	void deletePartner(Partner partner);
	void deletePartnerById(long id);
	Partner addContactDetail(Partner partner, ContactDetail contactDetail);
	Partner addContactDetailByPartnerId(long id, ContactDetail contactDetail);
	Partner removeContactDetail(Partner partner, ContactDetail contactDetail);
	Partner removeContactDetailByPartnerId(long id, ContactDetail contactDetail);
	Partner removeContactDetailByPartnerIdContactDetailId(long partnerId, long contactDetailId);
	List<Partner> findAllPartners();
	List<Partner> findPartnersByName(String name);
	Partner findPartnerByUniqueName(String name);
	List<Partner> findPartnersByNameContains(String name);
	Partner findPartnerById(long id);
	boolean isPartnerExist(String partnerName);
	Partner addPartnerGroup(Partner partner,PartnerGroup partnerGroup);
	Partner removePartnerGroup(Partner partner,PartnerGroup partnerGroup);
	Partner removePartnerGroupByPartnerGroupID(Partner partner, long id);
	List<String> getDistinctGroupNames();
	List<PartnerGroup> getSubGroupsForGroup(String groupName);
	List<PartnerNameProjection> getAllPartnerNames();
	
	//Added by Arindam Sikdar for Dynamic Data Load process
	ContactDetail insertContactDetail(ContactDetail contactDetail);
	
}
