package com.abc.dashboard.service;



import java.util.List;

import com.abc.dashboard.model.PcceAbcBusinessService;
import com.abc.dashboard.model.PcceAbcBusinessUnitLookup;



/**
 * @author Manoj Kumar Dwari
 *
 */


public interface PcceAbcBusinessLookupService {
	
	List<PcceAbcBusinessService> getAllPcceAbcBusinessServiceLookups();
	PcceAbcBusinessService savePcceAbcBusinessService(PcceAbcBusinessService pcceAbcBusinessService);

}
