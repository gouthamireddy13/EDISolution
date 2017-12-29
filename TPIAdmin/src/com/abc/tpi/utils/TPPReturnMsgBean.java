package com.abc.tpi.utils;

import com.abc.tpi.model.tpp.Tpp;

public class TPPReturnMsgBean {
	String returnMsg;
	int returnFlag;
	Tpp tpp;
	public String getReturnMsg() {
		return returnMsg;
	}
	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}
	public Tpp getTpp() {
		return tpp;
	}
	public void setTpp(Tpp tpp) {
		this.tpp = tpp;
	}
	public int getReturnFlag() {
		return returnFlag;
	}
	public void setReturnFlag(int returnFlag) {
		this.returnFlag = returnFlag;
	}

	
}
