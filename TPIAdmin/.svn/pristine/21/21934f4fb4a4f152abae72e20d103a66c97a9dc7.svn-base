package com.abc.tpi.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.abc.tpi.model.migrator.ObjectTracker;
import com.abc.tpi.model.migrator.ServiceSubscriptionApprovalReview;
import com.abc.tpi.model.service.Service;
import com.abc.tpi.model.service.ServiceSubscription;
import com.abc.tpi.repository.ObjectTrackerRepository;
import com.abc.tpi.repository.ServiceSubscriptionRepository;
import com.abc.tpi.service.ServiceSubscriptionService;
import com.abc.tpi.service.migrator.MigratorDataService;
import com.abc.tpi.service.migrator.client.MigratorClientService;

@ContextConfiguration(locations={"classpath:application-contexttest.xml"})
@RunWith(SpringJUnit4ClassRunner.class)

public class TestTpiAuditDataTest {

	@Autowired
	EntityManager em;
	
	@Autowired
	ServiceSubscriptionService serviceSubscriptionService;
	
	@Autowired
	ObjectTrackerRepository objectTrackerRepository;
	
	AuditReader auditReader;
	
	@Autowired
	MigratorDataService migratorDataSerice;
	
	@Autowired
	ServiceSubscriptionRepository serviceSubscriptionRepository;
	
	@Autowired
	MigratorClientService migratorClientService;
	
	
	@Test
	@Transactional
	public void testFindLastestServiceRevision()
	{	
		auditReader = AuditReaderFactory.get(em);
		
		Service service = serviceSubscriptionService.getAllServiceSubscriptions().get(0).getServices().iterator().next();
		
		List<Number> revisionNumbers = auditReader.getRevisions(Service.class, service.getId());
		for (Number rev : revisionNumbers) {
			
			Service src = auditReader.find(Service.class, service.getId(), rev);
			System.out.println("Service id " +  src.getId() + " at revison " + rev);
		}
		
		AuditQuery q = auditReader.createQuery().forRevisionsOfEntity(Service.class, false, true);
		q.addProjection(AuditEntity.revisionNumber().max());
		q.add(AuditEntity.id().eq(service.getId()));
		
		Number revision = (Number) q.getSingleResult();
		System.out.println("Latest Revision: " + revision);
	}
	
	@Test
	public void testAddObjectTrackerEntity()
	{
		ObjectTracker ot = new ObjectTracker();
		ot.setClassName("parent");
		ot.setSourceId(1);
		
		ObjectTracker child1 = new ObjectTracker();
		ObjectTracker child2 = new ObjectTracker();
		
		child1.setClassName("child");
		child1.setSourceId(2);
		child2.setSourceId(3);
		child2.setClassName("child");
		//ot.setObjectTrackers(new ArrayList<ObjectTracker>());
		ot.addObjectTracker(child1);
		ot.addObjectTracker(child2);
		
		objectTrackerRepository.save(ot);
	}

	@Test
	public void getAllModifiedBusinessServices()
	{
		List<ServiceSubscription> result = serviceSubscriptionRepository.namedFindAllNotMigratedServiceSubscription2();
		for (ServiceSubscription bs: result)
		{
			System.out.println("BS ID: " + bs.getId());
		}
	}
	
	@Test
	public void testMigratorServiceGetDataRerview()
	{
		List<ServiceSubscriptionApprovalReview> result = migratorDataSerice.getApprovalReviewForSriId("123");
		
		try {
			migratorClientService.migrateServiceSubscription(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//migrateServiceSubscription.
		
	}	

}
