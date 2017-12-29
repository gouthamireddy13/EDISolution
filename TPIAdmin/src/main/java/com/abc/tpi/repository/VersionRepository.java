package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Version;
@Repository("versionRepository")
@RepositoryRestResource(path = "versions")
public interface VersionRepository extends JpaRepository<Version, Long> 
{
	Version findOneByVersionNumber(Integer versionNumber);
	List<Version> namedFindAllVersionsForTpp(@Param("tppId") long tppId);
}
