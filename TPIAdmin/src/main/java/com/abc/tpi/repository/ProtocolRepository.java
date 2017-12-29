package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Protocol;

@Repository("protocolRepository")
public interface ProtocolRepository extends JpaRepository<Protocol, Long> {
	Protocol findOneByProtocolTypeIgnoreCase(String protocolType);
	List<Protocol> namedFindAllProtocolsForTpp(@Param("tppId") long tppId);
}
