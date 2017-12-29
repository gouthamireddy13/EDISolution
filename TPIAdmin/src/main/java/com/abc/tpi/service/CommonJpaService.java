package com.abc.tpi.service;

import java.util.List;

public interface CommonJpaService {

	<T> T findGenericEntityUsingEntityGrpah(Class<T> entityCls, Long id, String entityGraphName);
	<T> List<T> findGenericEntitiesUsingEntityGraph(Class<T> entityCls,String entityGraphName, String namedQueryName);
}
