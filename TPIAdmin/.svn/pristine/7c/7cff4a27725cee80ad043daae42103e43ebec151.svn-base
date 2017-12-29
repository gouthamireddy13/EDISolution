package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.TppListViewProjection;

@Repository("tppRepository")

public interface TppRepository extends  JpaRepository<Tpp,Long> 
{
	List<Tpp> findByNameContainingIgnoreCase(String name);
	
	Tpp findByNameIgnoreCase(String name);
	
	List<TppListViewProjection> findAllByOrderByName();
	
	List<Long> namedFindByAbcId(@Param("testIsaId") String testIsaId, 
								@Param("testGsId") String testGsId, 
								@Param("prodIsaId") String prodIsaId, 
								@Param("prodGsId") String prodGsId);
	
	List<Long> namedFindByAbcIdAndTppId(@Param("testIsaId") String testIsaId, 
			@Param("testGsId") String testGsId, 
			@Param("prodIsaId") String prodIsaId, 
			@Param("prodGsId") String prodGsId,
			@Param("tppId") long tppId);
}
