package com.abc.tpi.model.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@NamedQueries
(
	{
		@NamedQuery(name="Protocol.namedFindAllProtocolsForTpp", query="select distinct p from Tpp t Join t.protocols p where t.id=:tppId")
	}
)
@Table(name="ABC_TPI_PROTOCOL")
public class Protocol {
	
	@Id
	@SequenceGenerator(name="ABC_TPI_PROTOCOL_GEN", sequenceName="ABC_TPI_PROTOCOL_SEQ",allocationSize=1)
	@GeneratedValue(generator="ABC_TPI_PROTOCOL_GEN")
	@Column(name="PROTOCOL_ID")
	private Long id;
	
	@Column(name="PROTOCOL_TYPE",nullable=false,unique=true)
	private String protocolType;

	@Column(name="PROTOCOL_DESC")
	private String protocolDescription;
	
	public Long getId() {
		return id;
	}

	public String getProtocolType() {
		return protocolType;
	}

	public void setProtocolType(String protocolType) {
		this.protocolType = protocolType;
	}

	public String getProtocolDescription() {
		return protocolDescription;
	}

	public void setProtocolDescription(String protocolDescription) {
		this.protocolDescription = protocolDescription;
	}
	
	public static Protocol newInstance(Protocol protocol)
	{
		if (protocol==null)
		{
			return null;
		}
		Protocol clonedProtocol = new Protocol();
		clonedProtocol.setProtocolDescription(protocol.getProtocolDescription());
		clonedProtocol.setProtocolType(protocol.getProtocolType());
		return clonedProtocol;
	}
}
