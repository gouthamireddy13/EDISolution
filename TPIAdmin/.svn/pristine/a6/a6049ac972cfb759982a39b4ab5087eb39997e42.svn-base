package com.abc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.dashboard.model.SdBusinessService;

@Repository("sdBusinessServiceRepository")
public interface SdBusinessServiceRepository extends JpaRepository<SdBusinessService, Long> {

	<T> T namedFindSdBusinessServiceById(@Param("id") long id,Class<T> type);	
	<T> List<T> namedFindAllBusinessServices(Class<T> type);
	<T> T namedFindSdBusinessServiceByServiceKey(@Param("serviceKey") String serviceKey ,Class<T> type);
}
