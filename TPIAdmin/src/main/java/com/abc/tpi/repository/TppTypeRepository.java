package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.TppType;
@Repository("tppTypeRepository")
public interface TppTypeRepository extends JpaRepository<TppType, Long> {
	TppType findOneByTypeCode(Short typeCode);
}
