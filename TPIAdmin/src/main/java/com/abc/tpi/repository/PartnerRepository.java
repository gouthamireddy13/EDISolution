package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.model.partner.PartnerNameProjection;

@Repository("partnerRepsitory")
public interface PartnerRepository extends JpaRepository<Partner, Long> {
	List<Partner> findByPartnerNameContainingIgnoreCase(String name);
	Partner findFirstByPartnerNameIgnoreCase(String name);
	List<PartnerNameProjection> findAllByOrderByPartnerName();
}
