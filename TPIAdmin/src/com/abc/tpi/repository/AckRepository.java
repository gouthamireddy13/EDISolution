package com.abc.tpi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abc.tpi.model.master.Ack;

public interface AckRepository extends JpaRepository<Ack, Long> {
	Ack findOneByAckValue(String ackValue);

}
