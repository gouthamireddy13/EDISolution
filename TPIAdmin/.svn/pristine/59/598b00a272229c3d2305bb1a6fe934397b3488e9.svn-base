/**
 * 
 */
package com.abc.dashboard.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;
import com.abc.dashboard.repository.PcceABCBusinessUnitLookupRepository;

/**
 * @author ARINDAMSIKDAR
 *
 */
@Service("pcceAbcBusinessUnitLookupService")
public class PcceAbcBusinessUnitLookupServiceImpl implements PcceAbcBusinessUnitLookupService {

	/* (non-Javadoc)
	 * @see com.abc.dashboard.service.PcceAbcBusinessUnitLookupService#getAllPcceAbcBusinessUnitLookups()
	 */
	@Autowired
	PcceABCBusinessUnitLookupRepository pcceABCBusinessUnitLookupRepository;
	
	
	@Override
	public List<PcceAbcBusinessUnitLookup> getAllPcceAbcBusinessUnitLookups() {
		// TODO Auto-generated method stub
		return pcceABCBusinessUnitLookupRepository.findAll();
	}
	
	@Override
	public PcceAbcBusinessUnitLookup savePcceAbcBusinessUnitLookup(PcceAbcBusinessUnitLookup pcceAbcBusinessUnitLookup) {
		
		return pcceABCBusinessUnitLookupRepository.save(pcceAbcBusinessUnitLookup);
	}

}
