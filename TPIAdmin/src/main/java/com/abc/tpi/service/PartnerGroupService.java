package com.abc.tpi.service;

import java.util.List;

import com.abc.tpi.model.partner.PartnerGroup;

public interface PartnerGroupService {
	List<String> getDistinctPartnerGroupNames();
	List<String> getDistinctPartnerGroupNamesForDropDown();	
	List<PartnerGroup> getPartnerGroupsByGroupName(String groupName);
	List<PartnerGroup> getPartnerGroupsByGroupNameForDropDown(String groupName);
	List<PartnerGroup> getAllPartnerGroups();
	PartnerGroup getPartnerGroupById(long id);
	PartnerGroup getPartnerGroupByGroupSubGroupName(String groupName, String subGroupName);
	PartnerGroup savePartnerGroup(PartnerGroup partnerGroup);
	void deletePartnerGroup(PartnerGroup partnerGroup);
	void deletePartnerGroupByID(long id);	
	boolean isPartnerGroupExists(String groupName, String subGroupName);
}
