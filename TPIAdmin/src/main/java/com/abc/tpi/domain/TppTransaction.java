package com.abc.tpi.domain;

public class TppTransaction {

	private int direction;
	private int docType;
	private int version;
	public int getDirection() {
		return direction;
	}
	public int getDocType() {
		return docType;
	}
	public int getVersion() {
		return version;
	}
	public void setDirection(int direction) {
		this.direction = direction;
	}
	public void setDocType(int docType) {
		this.docType = docType;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
