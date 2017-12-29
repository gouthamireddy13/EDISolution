package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.partner.PartnerGroup;

@Repository ("partnerGroupRepository")
public interface PartnerGroupRepository extends JpaRepository<PartnerGroup, Long> {
	List<PartnerGroup> findByGroupNameIgnoreCaseOrderBySubGroupNameAsc(String groupName);
	List<PartnerGroup> findByGroupNameLikeOrderByGroupNameAsc(String groupName);
	List<PartnerGroup> findByGroupNameContainsIgnoreCaseOrderBySubGroupNameAsc(String groupName);
	List<String> namedFindDistinctGroupNames();
	PartnerGroup findOneByGroupNameAndSubGroupNameIgnoreCase(String groupName,String subGroupName);
	
}
