package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.tpi.domain.json.PartnerLightWellData;
import com.abc.tpi.model.reporting.LightWellPartnerRecord;
import com.abc.tpi.model.tpp.LightWellPartner;
@Repository("lightWellPartnerRepository")
public interface LightWellPartnerRepository extends JpaRepository<LightWellPartner, Long> {
	LightWellPartner namedFindLightWellPartnerByServiceCategoryIdAndLwId(@Param("scId") long scId, @Param("lwId") long lwId);
	LightWellPartner namedFindLightWellPartner(@Param("testIsaID") String testIsaID, 
												@Param("testIsaQualifier") String testIsaQualifier, 
												@Param("testGsId") String testGsId,
												@Param("productionIsaID") String productionIsaID, 
												@Param("productionIsaQualifier") String productionIsaQualifier, 
												@Param("productionGsId") String productionGsId, 
												@Param("isActive") boolean isActive);
	List<PartnerLightWellData>  namedFindLightWellPartnerByPartnerName(@Param("partnerName") String partnerName);
	List<PartnerLightWellData>  namedFindLightWellPartnerByPartnerNameIgnoreCase(@Param("partnerName") String partnerName);
	List<PartnerLightWellData>  namedFindLightWellPartnerBySrId(@Param("srId") String srId);
	List<PartnerLightWellData>  namedFindLightWellPartnerBySrIdIgnoreCase(@Param("srId") String srId);
	List<LightWellPartnerRecord>  namedFindLightWellIdentityByPartnerIdLwIds(@Param("partnerId") long partnerId, @Param("lwIds") List<Long> lwIds);
	List<LightWellPartner> namedFindLightWellWithSdServiceCategoryDefMembership();
	
	LightWellPartner findFirstByTestIsaIDLikeIgnoreCase(String value);
	LightWellPartner findFirstByTestGsIdLikeIgnoreCase(String value);
	LightWellPartner findFirstByProductionIsaIDLikeIgnoreCase(String value);
	LightWellPartner findFirstByProductionGsIdLikeIgnoreCase(String value);
	
}
