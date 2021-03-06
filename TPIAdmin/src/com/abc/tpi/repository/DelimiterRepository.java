package com.abc.tpi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.abc.tpi.model.master.Delimiter;
@Repository("delimiterRepository")
public interface DelimiterRepository extends JpaRepository<Delimiter, Long> {
	Delimiter findOneByDelimiter(String delimiter);
	List<Delimiter> findAllByIsSegmentTrueOrderByDelimiter();
	List<Delimiter> findAllByIsCompositeTrueOrderByDelimiter();
	List<Delimiter> findAllByIsElementTrueOrderByDelimiter();
	List<Delimiter> findAllByIsRepeatTrueOrderByDelimiter();
}
