package com.abc.tpi.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

@Service("commonJpaService")
public class CommonJpaServiceImpl implements CommonJpaService
{
	@PersistenceContext	
	EntityManager em;
	
	@Override
	public <T> T findGenericEntityUsingEntityGrpah(Class<T> entityCls, Long id, String entityGraphName)
	{
		Map<String,Object> props = new HashMap<String,Object>();
		props.put("javax.persistence.fetchgraph",em.getEntityGraph(entityGraphName));
		return em.find	(entityCls,id, props);	 
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> List<T> findGenericEntitiesUsingEntityGraph(Class<T> entityCls, String entityGraphName, String namedQuery) {
		EntityGraph<T> eg = (EntityGraph<T>) em.getEntityGraph(entityGraphName);
		List<T> result = null;
		if (eg!=null)
		{
			result = em.createNamedQuery(namedQuery)
			        .setHint("javax.persistence.loadgraph", eg)
			        .getResultList();
		}
		return result;
	}
}
