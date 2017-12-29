package com.abc.dashboard.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.dashboard.model.SdServiceCategoryDef;

@Repository("sdServiceCategoryDefRepository")
public interface SdServiceCategoryDefRepository extends JpaRepository<SdServiceCategoryDef, Long> {
	
	<T> T namedFindServiceCategoryByName(@Param ("name") String name,Class<T> type);
	<T> T namedFindServiceCategoryByCategoryId(@Param ("categoryID") int categoryID,Class<T> type);
	<T> List<T> findAllByOrderByServiceCategory(Class<T> type);
	<T> List<T> namedFindServiceCategoriesWithPartnerSubscription(Class<T> type);
	
	SdServiceCategoryDef findFirstByCategoryID(int categoryId);
	SdServiceCategoryDef findFirstByServiceCategoryNameIgnoreCase(String name);
	
}
