/**
 * 
 */
package com.abc.dashboard.service;

import java.util.List;

import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;

/**
 * @author ARINDAMSIKDAR
 *
 */
public interface PcceAbcBusinessUnitLookupService {
	
	List<PcceAbcBusinessUnitLookup> getAllPcceAbcBusinessUnitLookups();
	PcceAbcBusinessUnitLookup savePcceAbcBusinessUnitLookup(PcceAbcBusinessUnitLookup pcceAbcBusinessUnitLookup);

}
