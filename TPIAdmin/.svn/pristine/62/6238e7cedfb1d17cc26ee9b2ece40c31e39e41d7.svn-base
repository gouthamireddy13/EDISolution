package com.abc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dashboard.model.PcceAbcBusinessService;
import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;
import com.abc.dashboard.repository.PcceABCBusinessServiceLookupRepository;
import com.abc.dashboard.repository.PcceABCBusinessUnitLookupRepository;

/**
 * @author Manoj Kumar Dwari
 *
 */

@Service("pcceAbcBusinessLookupService")

public class PcceAbcBusinessLookupServiceImpl implements PcceAbcBusinessLookupService{
	
	/* (non-Javadoc)
	 * @see com.abc.dashboard.service.PcceAbcBusinessUnitLookupService#getAllPcceAbcBusinessUnitLookups()
	 */
	@Autowired
	PcceABCBusinessServiceLookupRepository pcceABCBusinessServiceLookupRepository;
	
	
	@Override
	public List<PcceAbcBusinessService> getAllPcceAbcBusinessServiceLookups() {
		// TODO Auto-generated method stub
		return pcceABCBusinessServiceLookupRepository.findAll();
	}
	
	@Override
	public PcceAbcBusinessService savePcceAbcBusinessService(PcceAbcBusinessService pcceAbcBusinessService) {
		return pcceABCBusinessServiceLookupRepository.save(pcceAbcBusinessService);
	}

}
