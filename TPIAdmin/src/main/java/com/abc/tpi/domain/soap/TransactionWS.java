package com.abc.tpi.domain.soap;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType( XmlAccessType.FIELD )
public class TransactionWS {
	
	@XmlElement(name="DIRECTION",required=true)
	String direction;
	
	@XmlElement(name="TYPE",required=true,type=String.class)
	String type;
	
	@XmlElement(name="VERSION",required=true,type=Integer.class)
	int version;
	
	public String getDirection() {
		return direction;
	}
	public String getType() {
		return type;
	}
	public int getVersion() {
		return version;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setVersion(int version) {
		this.version = version;
	}
}
