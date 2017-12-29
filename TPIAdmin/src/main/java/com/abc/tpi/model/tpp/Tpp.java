package com.abc.tpi.model.tpp;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.abc.tpi.model.master.Delimiter;
import com.abc.tpi.model.master.Protocol;
import com.abc.tpi.model.master.TppType;
import com.abc.tpi.model.partner.ContactDetail;

@Entity
@NamedQueries({
		@NamedQuery(name = "Tpp.namedFindByAbcId", 
					query = "select t.id from Tpp t "
							+ " where t.lightWellPartner.testIsaID = :testIsaId "
							+ " or t.lightWellPartner.testGsId = :testGsId "
							+ "	or t.lightWellPartner.productionIsaID = :prodIsaId "
							+ "	or t.lightWellPartner.productionGsId = :prodGsId"),
		
		@NamedQuery(name = "Tpp.namedFindByAbcIdAndTppId", 
					query = "select t.id from Tpp t "
							+ "	where ( t.lightWellPartner.testIsaID = :testIsaId "
							+ "	or t.lightWellPartner.testGsId = :testGsId "
							+ "	or t.lightWellPartner.productionIsaID = :prodIsaId "
							+ "	or t.lightWellPartner.productionGsId =:prodGsId) "
							+ "	and t.id <> :tppId")
}
)
@Table(name="ABC_TPI_TPP")
public class Tpp {
	@Id

	@SequenceGenerator(name="ABC_TPI_TPP_GEN", sequenceName="ABC_TPI_TPP_SEQ",allocationSize=10)
	@GeneratedValue(generator="ABC_TPI_TPP_GEN")
	@Column(name="TPP_ID")
	private Long id;
	
	//unidirectional
	@NotNull(message="Tpp Type is required")
	@ManyToOne
	@JoinColumn(name="TPP_TYPE_ID", nullable=false)
	private TppType type;
	
	@NotNull(message="Tpp Name is required")
	@Column(name="TPP_NAME",unique=true,nullable=false)
	private String name;

	@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY, orphanRemoval=true)
	@JoinTable(name="ABC_TPI_TPP_CONTACT",
			joinColumns=@JoinColumn(name="TPP_ID"),
			inverseJoinColumns=@JoinColumn(name="CONTACT_DETAIL_ID"))
	private Set<ContactDetail> contactDetails;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name="ABC_TPI_TPP_PROTOCOLS",
			joinColumns=@JoinColumn(name="TPP_ID"),
			inverseJoinColumns=@JoinColumn(name="PROTOCOL_ID"),uniqueConstraints=@UniqueConstraint(columnNames={"TPP_ID","PROTOCOL_ID"}))
	private Set<Protocol> protocols;
	
	//unidirectional
	@NotNull(message="Transaction is required")
	@OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL,orphanRemoval=true)
	@JoinTable(name="ABC_TPI_TPP_TRANSACTIONS",
			joinColumns=@JoinColumn(name="TPP_ID"),
			inverseJoinColumns=@JoinColumn(name="TRANSACTION_ID"))
	private Set<Transaction> transactions;
	
	@OneToOne(fetch=FetchType.EAGER, cascade= {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.PERSIST},orphanRemoval=true)
	@JoinColumn(name="LW_ID", nullable=true)
	private LightWellPartner lightWellPartner;

	@NotNull
	@ManyToOne
	@JoinColumn(name="SEGMENT_DELIM", nullable=false)
	private Delimiter segmentDelimiter;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="ELEMENT_DELIM")
	private Delimiter elementDelimiter;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name="COMPOSITE_DELIM")
	private Delimiter compositeElementDelimiter;
	
	@ManyToOne
	@JoinColumn(name="REPEAT_DELIM")
	private Delimiter repeatDelimiter;
	
	public void addContact(ContactDetail contact)
	{
		if (contactDetails==null)
		{
			contactDetails = new HashSet<ContactDetail>();
		}
		contactDetails.add(contact);
	}
	
	public void addProtocol(Protocol protocol)
	{
		if (protocols==null)
		{
			protocols = new HashSet<Protocol>();
		}
		protocols.add(protocol);
	}
	
	public void addTransaction(Transaction transaction)
	{
		if (transactions==null)
		{
			transactions = new HashSet<Transaction>();			
		}
		transactions.add(transaction);
	}
	
	public Delimiter getCompositeElementDelimiter() {
		return compositeElementDelimiter;
	}

	public Set<ContactDetail> getContactDetails() {
		return contactDetails;
	}

	public Delimiter getElementDelimiter() {
		return elementDelimiter;
	}

	public Long getId() {
		return id;
	}

	public LightWellPartner getLightWellPartner() {
		return lightWellPartner;
	}

	public String getName() {
		return name;
	}

	public Set<Protocol> getProtocols() {
		return protocols;
	}

	public Delimiter getRepeatDelimiter() {
		return repeatDelimiter;
	}

	public Delimiter getSegmentDelimiter() {
		return segmentDelimiter;
	}

	public Set<Transaction> getTransactions() {
		return transactions;
	}

	public TppType getType() {
		return type;
	}

	public void setCompositeElementDelimiter(Delimiter compositeElementDelimiter) {
		this.compositeElementDelimiter = compositeElementDelimiter;
	}

	public void setElementDelimiter(Delimiter elementDelimiter) {
		this.elementDelimiter = elementDelimiter;
	}

	public void setLightWellPartner(LightWellPartner lightWellPartner) {
		this.lightWellPartner = lightWellPartner;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRepeatDelimiter(Delimiter repeatDelimiter) {
		this.repeatDelimiter = repeatDelimiter;
	}

	public void setSegmentDelimiter(Delimiter segmentDelimiter) {
		this.segmentDelimiter = segmentDelimiter;
	}

	public void setType(TppType type) {
		this.type = type;
	}
	
	public void setProtocols(Set<Protocol> protocols) {
		this.protocols = protocols;
	}

	public void removeContactDetails(Set<ContactDetail> contactDetails) {
		this.contactDetails.removeAll(contactDetails);
	}
	
	public void removeTransactions(Set<Transaction> transactions) {
		this.transactions.removeAll(transactions);
	}
	
	public static Tpp newInstance(Tpp tpp)
	{
		if (tpp==null)
		{
			return null;
		}
		
		Tpp clonedTpp = new Tpp();
		clonedTpp.setType(TppType.newInstance(tpp.getType()));
		clonedTpp.setName(tpp.getName());
		
		if (tpp.getContactDetails()!=null)
		{
			for (ContactDetail contact: tpp.getContactDetails())
			{
				clonedTpp.addContact(ContactDetail.newInstance(contact));
			}
		}
		
		if (tpp.getProtocols()!=null)
		{
			for (Protocol protocol: tpp.getProtocols())
			{
				clonedTpp.addProtocol(Protocol.newInstance(protocol));
			}
		}
		
		if (tpp.getTransactions()!=null)
		{
			for (Transaction transaction: tpp.getTransactions())
			{
				clonedTpp.addTransaction(Transaction.newInstance(transaction));
			}
		}
		
		clonedTpp.setLightWellPartner(LightWellPartner.newInstance(tpp.getLightWellPartner()));
		clonedTpp.setCompositeElementDelimiter(Delimiter.newInstance(tpp.getCompositeElementDelimiter()));
		clonedTpp.setElementDelimiter(Delimiter.newInstance(tpp.getElementDelimiter()));
		clonedTpp.setRepeatDelimiter(Delimiter.newInstance(tpp.getRepeatDelimiter()));
		clonedTpp.setSegmentDelimiter(Delimiter.newInstance(tpp.getSegmentDelimiter()));
		return clonedTpp;
	}
	
}

