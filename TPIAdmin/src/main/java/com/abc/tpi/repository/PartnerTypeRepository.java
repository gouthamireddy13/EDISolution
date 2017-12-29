/**
 * 
 */
package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Direction;
import com.abc.tpi.model.master.PartnerType;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Repository("partnerType")
public interface PartnerTypeRepository extends JpaRepository<PartnerType, Long> {

	PartnerType findOneByTypeCodeIgnoreCase(String code);
}
