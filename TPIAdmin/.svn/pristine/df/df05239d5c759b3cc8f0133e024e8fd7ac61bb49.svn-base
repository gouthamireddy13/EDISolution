package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.service.ServiceCategory;
import com.abc.tpi.model.service.ServiceCategoryForDropDownProjection;
@Repository("serviceCategoryRepository")
public interface ServiceCategoryRepository extends JpaRepository<ServiceCategory, Long> {
	List<ServiceCategory> findAllByOrderByName();
	List<ServiceCategoryForDropDownProjection> findDistinctByOrderByName();
	List<ServiceCategory> findAllByName(String name);
	ServiceCategory findByNameIgnoreCase(String name);
	ServiceCategory findOneByName(String name);
}
