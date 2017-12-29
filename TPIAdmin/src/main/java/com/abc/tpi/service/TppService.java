package com.abc.tpi.service;

import java.util.List;

import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.tpp.Tpp;
import com.abc.tpi.model.tpp.TppListViewProjection;
import com.abc.tpi.model.tpp.Transaction;

public interface TppService {

	Tpp addProtocol(Protocol protocol);

	Tpp addTransaction(Transaction transaction);

	void deleteTpp(Tpp tpp);

	void deleteTppById(long id);

	List<Tpp> findAllTpps();

	Tpp findByNameFullStringMatchIgnoreCase(String name);

	Tpp findTppById(long id);

	List<Tpp> findTppByName(String name);

	List<Protocol> getProtocolsForTppId(long tppId);

	List<Version> getVersionsForTppId(long tppId);

	Tpp removeProtocol(Protocol protocol);

	Tpp removeProtocolByID(long id);

	Tpp removeTransaction(Transaction transaction);

	Tpp removeTransactionById(long id);

	Tpp saveTpp(Tpp tpp);

	boolean tppWithNameExists(String name);
	
	List<TppListViewProjection> getTppProjectionForListView();
	
	List<Long> getTppsForAbcId(String testIsaId,String testGsId, String prodIsaId, String prodGsId);
	
	boolean isDuplicateTppForAbcId(String testIsaId,String testGsId, String prodIsaId, String prodGsId, long tppId);

}
