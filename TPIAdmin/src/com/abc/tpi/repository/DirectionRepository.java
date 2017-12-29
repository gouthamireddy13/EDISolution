package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Direction;
@Repository("direction")
public interface DirectionRepository extends JpaRepository<Direction, Long> {

	Direction findOneByDirectionCodeIgnoreCase(String code);
}
