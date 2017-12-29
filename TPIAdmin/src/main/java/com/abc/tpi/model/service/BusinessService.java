package com.abc.tpi.model.service;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedEntityGraphs;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.Table;

import org.hibernate.envers.Audited;

import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TpiMap;
import com.abc.tpi.model.master.Version;
import com.abc.tpi.model.tpp.LightWellPartner;
import com.abc.tpi.service.TpiManagedEntityListener;

@Audited
@Entity
@Table(name="ABC_TPI_BUSINESS_SERVICE")
@NamedQueries
(
	{
	@NamedQuery(name="BusinessService.namedFindBusinessServicesByServiceId", 
			query="select bs from Service sc join sc.businessServices bs where sc.id = :serviceId"),
	@NamedQuery(name="BusinessService.namedFindBusinessServicesVersionNumByServiceId", 
	query="select bs.versionNum from Service sc join sc.businessServices bs where sc.id = :serviceId"),	
	@NamedQuery(name="BusinessService.namedFindBusinessServicesByServiceSubscriptionAndServiceId", 
			query="select bs from ServiceSubscription ss join ss.services sc join sc.businessServices bs where ss.id = :subscriptionId and sc.id = :serviceId"),
	@NamedQuery(name="BusinessService.namedFindBusinessServicesByServiceSubscriptionAndServiceIdAndId", 
		query="select bs from ServiceSubscription ss join ss.services sc join sc.businessServices bs where ss.id = :subscriptionId and sc.id = :serviceId and bs.id = :bsId"),
	@NamedQuery(name="BusinessService.namedInEnvelopeBySrId", 
			query="select NEW com.abc.tpi.model.reporting.PartnerSubscriptionRecord("
					+ " ss.partner.partnerName,"
					+ " bs.serviceType.document.documentType, "
					+ " bs.serviceType.direction.directionCode, "
					+ " sc.lightWellPartner.testIsaQualifier, "
					+ " sc.lightWellPartner.testIsaID, "
					+ " sc.lightWellPartner.testGsId, "
					+ " sc.lightWellPartner.productionIsaQualifier, "
					+ " sc.lightWellPartner.productionIsaID, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.segmentDelimiter.delimiter, "
					+ " sc.elementDelimiter.delimiter, "
					+ " sc.compositeElementDelimiter.delimiter, " 
					+ " rd.delimiter, "
					+ " bs.version.versionNumber, "
					+ " bs.lightWellPartner.testIsaQualifier,"
					+ " bs.lightWellPartner.testIsaID, "
					+ " bs.lightWellPartner.testGsId, "
					+ " bs.lightWellPartner.productionIsaQualifier, "
					+ " bs.lightWellPartner.productionIsaID, "
					+ " bs.lightWellPartner.productionGsId, "
					+ " bs.ackPeriod, "
					+ " bs.ack, "
					+ " bs.complianceCheck,"
					+ " bs.stControlNum,"
					+ " bs.isaControlNum,"
					+ " bs.stAcceptorLookUpAlias,"
					+ " bs.gsIdVersion,"
					+ " m.mapName, "
					+ " bs.serviceType.company,"
					+ " bs.serviceType.partnerCategory ) " 
					+ " from ServiceSubscription ss join ss.services sc left join sc.repeatDelimiter rd join sc.businessServices bs left join bs.map m"
					+ " where bs.srId = :srId "
					+ " order by ss.partner.partnerName"),
	@NamedQuery(name="BusinessService.namedInEnvelopeByServiceSubscriptionId", 
			query="select NEW com.abc.tpi.model.reporting.PartnerSubscriptionRecord("
					+ " ss.partner.partnerName,"
					+ " bs.serviceType.document.documentType, "
					+ " bs.serviceType.direction.directionCode, "
					+ " sc.lightWellPartner.testIsaQualifier, "
					+ " sc.lightWellPartner.testIsaID, "
					+ " sc.lightWellPartner.testGsId, "
					+ " sc.lightWellPartner.productionIsaQualifier, "
					+ " sc.lightWellPartner.productionIsaID, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.segmentDelimiter.delimiter, "
					+ " sc.elementDelimiter.delimiter, "
					+ " sc.compositeElementDelimiter.delimiter, " 
					+ " rd.delimiter, "
					+ " bs.version.versionNumber, "
					+ " bs.lightWellPartner.testIsaQualifier,"
					+ " bs.lightWellPartner.testIsaID, "
					+ " bs.lightWellPartner.testGsId, "
					+ " bs.lightWellPartner.productionIsaQualifier, "
					+ " bs.lightWellPartner.productionIsaID, "
					+ " bs.lightWellPartner.productionGsId, "
					+ " bs.ackPeriod, "
					+ " bs.ack, "
					+ " bs.complianceCheck,"
					+ " bs.stControlNum,"
					+ " bs.isaControlNum,"
					+ " bs.stAcceptorLookUpAlias,"
					+ " bs.gsIdVersion,"
					+ " m.mapName, "
					+ " bs.serviceType.company,"
					+ " bs.serviceType.partnerCategory ) " 
					+ " from ServiceSubscription ss join ss.services sc left join sc.repeatDelimiter rd join sc.businessServices bs left join bs.map m"
					+ " where ss.id = :subscriptionId"),
	@NamedQuery(name="BusinessService.namedGenericInEnvelopeByServiceSubscriptionId", 
			query="select "
					+ " ss.partner.partnerName,"
					+ " bs.serviceType.document.documentType, "
					+ " bs.serviceType.direction.directionCode, "
					+ " sc.lightWellPartner.testIsaQualifier, "
					+ " sc.lightWellPartner.testIsaID, "
					+ " sc.lightWellPartner.testGsId, "
					+ " sc.lightWellPartner.productionIsaQualifier, "
					+ " sc.lightWellPartner.productionIsaID, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.segmentDelimiter.delimiter, "
					+ " sc.elementDelimiter.delimiter, "
					+ " sc.compositeElementDelimiter.delimiter, " 
					+ " rd.delimiter, "
					+ " bs.version.versionNumber, "
					+ " bs.lightWellPartner.testIsaQualifier,"
					+ " bs.lightWellPartner.testIsaID, "
					+ " bs.lightWellPartner.testGsId, "
					+ " bs.lightWellPartner.productionIsaQualifier, "
					+ " bs.lightWellPartner.productionIsaID, "
					+ " bs.lightWellPartner.productionGsId, "
					+ " bs.ackPeriod, "
					+ " bs.ack "
					+ " from ServiceSubscription ss join ss.services sc left join sc.repeatDelimiter rd join sc.businessServices bs "
					+ " where ss.id = :subscriptionId"),
	@NamedQuery(name="BusinessService.namedInEnvelopeBySrIdBusinessServiceIds", 
					query="select NEW com.abc.tpi.model.reporting.PartnerSubscriptionRecord("
					+ " ss.partner.partnerName,"
					+ " bs.serviceType.document.documentType, "
					+ " bs.serviceType.direction.directionCode, "
					+ " sc.lightWellPartner.testIsaQualifier, "
					+ " sc.lightWellPartner.testIsaID, "
					+ " sc.lightWellPartner.testGsId, "
					+ " sc.lightWellPartner.productionIsaQualifier, "
					+ " sc.lightWellPartner.productionIsaID, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.segmentDelimiter.delimiter, "
					+ " sc.elementDelimiter.delimiter, "
					+ " sc.compositeElementDelimiter.delimiter, " 
					+ " rd.delimiter, "
					+ " bs.version.versionNumber, "
					+ " bs.lightWellPartner.testIsaQualifier,"
					+ " bs.lightWellPartner.testIsaID, "
					+ " bs.lightWellPartner.testGsId, "
					+ " bs.lightWellPartner.productionIsaQualifier, "
					+ " bs.lightWellPartner.productionIsaID, "
					+ " bs.lightWellPartner.productionGsId, "
					+ " bs.ackPeriod, "
					+ " bs.ack, "
					+ " bs.complianceCheck,"
					+ " bs.stControlNum,"
					+ " bs.isaControlNum,"
					+ " bs.stAcceptorLookUpAlias,"
					+ " bs.gsIdVersion,"
					+ " m.mapName, "
					+ " bs.serviceType.company,"
					+ " bs.serviceType.partnerCategory ) " 
					+ " from ServiceSubscription ss join ss.services sc left join sc.repeatDelimiter rd join sc.businessServices bs left join bs.map m "
					+ " where bs.srId = :srId "
					+ " and bs.id IN :ids"
					+ " order by ss.partner.partnerName")
					,
	@NamedQuery(name="BusinessService.namedInEnvelopeByPartnerName", 
					query="select NEW com.abc.tpi.model.reporting.PartnerSubscriptionRecord("
					+ " ss.partner.partnerName,"
					+ " bs.serviceType.document.documentType, "
					+ " bs.serviceType.direction.directionCode, "
					+ " sc.lightWellPartner.testIsaQualifier, "
					+ " sc.lightWellPartner.testIsaID, "
					+ " sc.lightWellPartner.testGsId, "
					+ " sc.lightWellPartner.productionIsaQualifier, "
					+ " sc.lightWellPartner.productionIsaID, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.segmentDelimiter.delimiter, "
					+ " sc.elementDelimiter.delimiter, "
					+ " sc.compositeElementDelimiter.delimiter, " 
					+ " rd.delimiter, "
					+ " bs.version.versionNumber, "
					+ " bs.lightWellPartner.testIsaQualifier,"
					+ " bs.lightWellPartner.testIsaID, "
					+ " bs.lightWellPartner.testGsId, "
					+ " bs.lightWellPartner.productionIsaQualifier, "
					+ " bs.lightWellPartner.productionIsaID, "
					+ " bs.lightWellPartner.productionGsId, "
					+ " bs.ackPeriod, "
					+ " bs.ack, "
					+ " bs.complianceCheck,"
					+ " bs.stControlNum,"
					+ " bs.stAcceptorLookUpAlias,"
					+ " bs.gsIdVersion,"
					+ " bs.isaControlNum,"
					+ " m.mapName, "
					+ " bs.serviceType.company,"
					+ " bs.serviceType.partnerCategory ) " 
					+ " from ServiceSubscription ss join ss.services sc left join sc.repeatDelimiter rd join sc.businessServices bs left join bs.map m"
					+ " where UPPER(ss.partner.partnerName) = UPPER(:partnerName) "
					+ " order by ss.partner.partnerName")
					,
					@NamedQuery(name="BusinessService.namedFindAllNotMigratedBusinessServices",
					query="select s.id " 
							+ " from BusinessService s "
							+ " where NOT EXISTS "
							+ "	(select 1 from ObjectTracker ot "
							+ "		where s.id = ot.sourceId "
							+ "		and s.versionNum = ot.sourceVersionNum "
							+ "		and ot.className = 'com.abc.tpi.model.service.com.abc.tpi.model.service.BusinessService')"
			),
					@NamedQuery(name="BusinessService.namedFindNotMigratedBusinessServicesBySrId",
					query="select s.id " 
							+ " from BusinessService s "
							+ " where NOT EXISTS "
							+ "	(select 1 from ObjectTracker ot "
							+ "		where s.id = ot.sourceId "
							+ "		and s.versionNum = ot.sourceVersionNum "
							+ "		and ot.className = 'com.abc.tpi.model.service.com.abc.tpi.model.service.BusinessService')"
							+ "	and s.srId = :srId"
			)									
	}
	
)
@NamedEntityGraphs
({
	@NamedEntityGraph(name="BusinessServiceEntity.graphServiceId",
			attributeNodes=
			{
				@NamedAttributeNode(value="service",subgraph="service")
			},
			subgraphs={
				@NamedSubgraph(name="service",
						attributeNodes={
								@NamedAttributeNode("id")
						})
			}
	),
	
	@NamedEntityGraph(name="BusinessServiceEntity.graphMigrationReadyData",
	attributeNodes=
	{
			@NamedAttributeNode(value="serviceType",subgraph="serviceType"),
			@NamedAttributeNode(value="map"),
			@NamedAttributeNode(value="lightWellPartner"),
			@NamedAttributeNode(value="version"),
			@NamedAttributeNode(value="protocol")
	},
	subgraphs=
	{
			@NamedSubgraph(name="serviceType",
					attributeNodes={
							@NamedAttributeNode("businessServiceName"),
							@NamedAttributeNode("company"),
							@NamedAttributeNode("partnerCategory")
				})
	}
	)
})
@EntityListeners({TpiManagedEntityListener.class})
public class BusinessService extends BusinessServiceEntity 
{
	public static BusinessService newInstance(BusinessService businessService)
	{
		if (businessService==null)
		{
			return null;
		}
		BusinessService clone = new BusinessService();
		
		clone.setAck(businessService.isAck());
		clone.setAckPeriod(businessService.getAckPeriod());
		clone.setChangeType(businessService.getChangeType());
		clone.setComplianceCheck(businessService.isComplianceCheck());
		clone.setLightWellPartner(LightWellPartner.newInstance(businessService.getLightWellPartner()));
		clone.setMap(TpiMap.newInstance(businessService.getMap()));
		clone.setNotes(businessService.getNotes());
		clone.setProtocol(Protocol.newInstance(businessService.getProtocol()));
		clone.setServiceType(ServiceType.newInstance(businessService.getServiceType()));
		clone.setSrId(businessService.getSrId());
		clone.setVersion(Version.newInstance(businessService.getVersion()));
		
		return clone;
	}

	public static BusinessService newMigrationInstnace(BusinessService original)
	{
		if (original==null)
		{
			return null;
		}
		
		BusinessService shallowCopy = new BusinessService();
		shallowCopy.setAck(original.isAck());
		shallowCopy.setAckPeriod(original.getAckPeriod());
		shallowCopy.setComplianceCheck(original.isComplianceCheck());
		shallowCopy.setLightWellPartner(LightWellPartner.newInstance(original.getLightWellPartner()));
		shallowCopy.setMap(TpiMap.newInstance(original.getMap()));
		shallowCopy.setNotes(original.getNotes());
		shallowCopy.setProtocol(Protocol.newInstance(original.getProtocol()));
		
		if (original.getServiceType()!=null)
		{
			ServiceType serviceTypeCopy = new ServiceType();
			serviceTypeCopy.setCompany(original.getServiceType().getCompany());
			serviceTypeCopy.setPartnerCategory(original.getServiceType().getPartnerCategory());
			serviceTypeCopy.setBusinessServiceName(original.getServiceType().getBusinessServiceName());
			shallowCopy.setServiceType(serviceTypeCopy);
		}
		
		shallowCopy.setSrId(original.getSrId());
		shallowCopy.setVersion(Version.newInstance(original.getVersion()));
		
		shallowCopy.setStControlNum(original.getStControlNum());
		shallowCopy.setIsaControlNum(original.getIsaControlNum());
		shallowCopy.setStAcceptorLookUpAlias(original.getStAcceptorLookUpAlias());
		shallowCopy.setGsIdVersion(original.getGsIdVersion());

		return shallowCopy;	
	}
}
