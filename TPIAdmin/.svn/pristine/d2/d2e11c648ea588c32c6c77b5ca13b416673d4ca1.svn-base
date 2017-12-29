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

import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;

import com.abc.tpi.model.partner.Partner;
import com.abc.tpi.service.TpiManagedEntityListener;

@Audited
@Entity
@AuditOverride(forClass=com.abc.tpi.model.service.ServiceSubscriptionEntity.class,isAudited=false)
@NamedQueries
(
		{
			@NamedQuery(name="ServiceSubscription.namedGetVersionNumberById", 
					query="select ss.versionNum from ServiceSubscription ss where ss.id = :ssId"),
			@NamedQuery(name="ServiceSubscription.namedGetServicesIdsById", 
			query="select srvc.id from ServiceSubscription ss JOIN ss.services srvc where ss.id = :ssId"),
			@NamedQuery(name="ServiceSubscription.namedFindByParnterIdAndServiceCategoryId", 
			query="select ss.id from ServiceSubscription ss where ss.partner.id = :partnerId and ss.serviceCategory.id = :serviceCategoryId"),
			@NamedQuery(name="ServiceSubscription.namedInEnvelopeByServiceSubscriptionId", 
			query="select bs from ServiceSubscription ss join ss.services sc join sc.businessServices bs where ss.id = :subscriptionId"),
			//updated by Arindam Sikdar
			@NamedQuery(name="ServiceSubscription.namedGetServiceSubscriptionWithGsIdIsaId",
			query="select NEW com.abc.tpi.model.search.projections.ServiceSubscription.ServiceSubscriptionWithGsIdIsaId("
					+ " ss.id,"					
					+ " sc.id, "
					+ " ss.serviceCategory.name,"
					+ " ss.partner.partnerName, "
					+ " sc.lightWellPartner.productionGsId, "
					+ " sc.lightWellPartner.productionIsaID, " 
					+ " ss.intercompanyFlag ) "
					+ " from ServiceSubscription ss join ss.services sc"),
			@NamedQuery(name="ServiceSubscription.namedFindBySRId", 
			query="select distinct(ss) from ServiceSubscription ss join ss.services sc join sc.businessServices bs where UPPER(bs.srId) LIKE UPPER(:srID) "),
			@NamedQuery(name="ServiceSubscription.namedFindBusinessServicesForSrId",
			query="select NEW com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo("
					+ " ss.id,"
					+ " ss.partner.partnerName, "
					+ " ss.serviceCategory.name,"			
					+ " sc.id, "
					+ " bs.id,"
					+ " bs.serviceType.businessServiceName, "
					+ " bs.srId, "
					+ " bs.notes ) " 
					+ " from ServiceSubscription ss join ss.services sc join sc.businessServices bs "
					+ "  where bs.srId LIKE :srID "),
			@NamedQuery(name="ServiceSubscription.namedFindBusinessServicesForSrIdIgnoreCase",
			query="select NEW com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo("
					+ " ss.id,"
					+ " ss.partner.partnerName, "
					+ " ss.serviceCategory.name,"			
					+ " sc.id, "
					+ " bs.id,"
					+ " bs.serviceType.businessServiceName, "
					+ " bs.srId, "
					+ " bs.notes ) " 
					+ " from ServiceSubscription ss join ss.services sc join sc.businessServices bs "
					+ "  where UPPER(bs.srId) LIKE UPPER(:srID) "),
			@NamedQuery(name="ServiceSubscription.namedFindBusinessServicesByPartnerName",
			query="select NEW com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo("
					+ " ss.id,"
					+ " ss.partner.partnerName, "
					+ " ss.serviceCategory.name,"			
					+ " sc.id, "
					+ " bs.id,"
					+ " bs.serviceType.businessServiceName, "
					+ " bs.srId, "
					+ " bs.notes ) " 
					+ " from ServiceSubscription ss join ss.services sc join sc.businessServices bs "
					+ "  where UPPER(ss.partner.partnerName) like UPPER(:partnerName) order by ss.partner.partnerName"),
			@NamedQuery(name="ServiceSubscription.namedFindBusinessServicesForSrIdBusinessServiceId	",
			query="select NEW com.abc.tpi.model.search.projections.ServiceSubscription.BusinessServiceWithParentInfo("
					+ " ss.id,"
					+ " ss.partner.partnerName, "
					+ " ss.serviceCategory.name,"			
					+ " sc.id, "
					+ " bs.id,"
					+ " bs.serviceType.businessServiceName, "
					+ " bs.srId, "
					+ " bs.notes ) " 
					+ " from ServiceSubscription ss join ss.services sc join sc.businessServices bs "
					+ "  where bs.srId LIKE :srID  "
					+ " and bs.id IN :ids"),
			@NamedQuery(name="ServiceSubscription.namedFindAllNotMigratedServiceSubscription",
			query="select ss.id " 
					+ " from ServiceSubscription ss "
					+ "	where NOT EXISTS "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where ss.id = ot.sourceId "
					+ "			and ss.versionNum = ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.ServiceSubscription')"),
			@NamedQuery(name="ServiceSubscription.namedFindAllNotMigratedServiceSubscription2",
			query="select ss  " 
					+ " from ServiceSubscription ss join ss.services sc join sc.businessServices bs  "
					+ "	where NOT EXISTS "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where ss.id = ot.sourceId "
					+ "			and ss.versionNum = ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.ServiceSubscription') "
					+ "	OR NOT EXISTS  "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where sc.id = ot.sourceId "
					+ "			and sc.versionNum = ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.Service')"
					+ "	OR NOT EXISTS  "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where bs.id = ot.sourceId "
					+ "			and bs.versionNum = ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.BusinessService')"
					+ "	OR EXISTS "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where ss.id = ot.sourceId "
					+ "			and ss.versionNum != ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.ServiceSubscription') "
					+ "	OR EXISTS  "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where sc.id != ot.sourceId "
					+ "			and sc.versionNum = ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.Service')"
					+ "	OR EXISTS  "
					+ "		(select 1 from "
					+ "			ObjectTracker ot "
					+ "			where bs.id = ot.sourceId "
					+ "			and bs.versionNum != ot.sourceVersionNum "
					+ "			and ot.className = 'com.abc.tpi.model.service.BusinessService')"
					),
			//Added by Arindam Sikdar
			@NamedQuery(name="ServiceSubscription.namedGetServiceSubscriptionIdsById", 
			query="select ss.id from ServiceSubscription ss JOIN ss.services srvc where srvc.id = :srvcId"),
			@NamedQuery(name="ServiceSubscription.namedGetServiceSubscriptionIdsByIdContainsIgnoreCase", 
			query="select ss.id from ServiceSubscription ss JOIN ss.services srvc where UPPER(srvc.id) LIKE UPPER(:srvcId) ")
		}
		)

@NamedEntityGraphs
({
	@NamedEntityGraph
	(
			name="ServiceSubscriptionEntity.graphPartnerServiceCat",
			attributeNodes=
		{
				@NamedAttributeNode(value="partner",subgraph="partner"),
				@NamedAttributeNode(value="serviceCategory",subgraph="serviceCategory"),
		},
		subgraphs=
	{
			@NamedSubgraph
			(
					name="partner",
					attributeNodes=
				{
						@NamedAttributeNode("partnerName")
				}
					),
			@NamedSubgraph
			(
					name="serviceCategory",
					attributeNodes=
				{
						@NamedAttributeNode("name")
				}
					)
	}
			),
	@NamedEntityGraph
	(
			name="ServiceSubscriptionEntity.graphPartnerCategoryFull",
			attributeNodes=
		{
				@NamedAttributeNode(value="partner",subgraph="partner"),
				@NamedAttributeNode(value="serviceCategory",subgraph="serviceCategory"),
		},
		subgraphs=
	{
			@NamedSubgraph
			(
					name="partner",
					attributeNodes=
				{
						@NamedAttributeNode("partnerName"),
						@NamedAttributeNode("contactDetails"),
						@NamedAttributeNode("partnerGroup")									
				}
					),
			@NamedSubgraph
			(
					name="serviceCategory",
					attributeNodes=
				{
						@NamedAttributeNode("name"),
						@NamedAttributeNode("lightWellPartners")
				}
					)
	}
			)

})

@Table(name="ABC_TPI_SRVC_SBSCR")
@EntityListeners({TpiManagedEntityListener.class})
public class ServiceSubscription extends ServiceSubscriptionEntity {

	public static ServiceSubscription newInstance(ServiceSubscription serviceSubscription)
	{
		if (serviceSubscription==null)
		{
			return null;
		}

		ServiceSubscription clonedSS = newMigrationInstance(serviceSubscription);

		clonedSS.setPartner(Partner.newInstance(serviceSubscription.getPartner()));
		clonedSS.setServiceCategory(ServiceCategory.newInstance(serviceSubscription.getServiceCategory()));

		if (serviceSubscription.getServices()!=null)
		{
			for (Service src: serviceSubscription.getServices())
			{
				clonedSS.addService(src);
			}
		}

		return clonedSS;
	}

	/**
	 * Returns shallow copy of ServiceSubscription object (not including services)
	 */
	public static ServiceSubscription newMigrationInstance(ServiceSubscription serviceSubscription)
	{
		if (serviceSubscription==null)
		{
			return null;
		}

		ServiceSubscription clonedSS = new ServiceSubscription();
		if (serviceSubscription.getPartner()!=null)
		{
			Partner shallowPartner = new Partner();
			shallowPartner.setPartnerName(serviceSubscription.getPartner().getPartnerName());
			clonedSS.setPartner(shallowPartner);
		}

		if (serviceSubscription.getServiceCategory()!=null)
		{
			ServiceCategory shallowServiceCategory = new ServiceCategory();
			shallowServiceCategory.setName(serviceSubscription.getServiceCategory().getName());
			clonedSS.setServiceCategory(shallowServiceCategory);
		}

		return clonedSS;
	}
}
