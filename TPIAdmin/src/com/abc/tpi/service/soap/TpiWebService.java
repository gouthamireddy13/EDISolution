package com.abc.tpi.service.soap;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

import com.abc.tpi.domain.soap.PartnerWS;
import com.abc.tpi.domain.soap.TppLoadResult;
import com.abc.tpi.domain.soap.TppWS;
@WebService
public interface TpiWebService {
	List<TppLoadResult> uploadTpps(@WebParam(name="TPP") List<TppWS> tpps);
	List<TppLoadResult> uploadPartners(@WebParam(name="PARTNER") List<PartnerWS> partners);
	
}
