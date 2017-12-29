/**
 * 
 */
package com.abc.dashboard.model;

import java.io.Serializable;

/**
 * @author ARINDAMSIKDAR
 *
 */
public class PcceAbcBusinessUnitLookupKey implements Serializable {
	
	String sourceId;
	String destinationId;
	String businessUnit;
	String subBusinessUnit;
	String servicePreamble;
	
	
	
	   public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getDestinationId() {
		return destinationId;
	}

	public void setDestinationId(String destinationId) {
		this.destinationId = destinationId;
	}

	public String getBusinessUnit() {
		return businessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		this.businessUnit = businessUnit;
	}

	public String getSubBusinessUnit() {
		return subBusinessUnit;
	}

	public void setSubBusinessUnit(String subBusinessUnit) {
		this.subBusinessUnit = subBusinessUnit;
	}

	public String getServicePreamble() {
		return servicePreamble;
	}

	public void setServicePreamble(String servicePreamble) {
		this.servicePreamble = servicePreamble;
	}

	@Override
	public boolean equals(Object o) {

	        if(o == null)
	            return false;

	        if(!(o instanceof PcceAbcBusinessUnitLookupKey))
	            return false;

	        PcceAbcBusinessUnitLookupKey pcceAbcBusinessUnitLookupKey = (PcceAbcBusinessUnitLookupKey) o;
	        if(!(getBusinessUnit().equalsIgnoreCase(pcceAbcBusinessUnitLookupKey.getBusinessUnit()))){
	            return false;
	        }
	        if(!(getDestinationId().equalsIgnoreCase(pcceAbcBusinessUnitLookupKey.getDestinationId()))){
		            return false;
	        }
	        
	        if(!(getServicePreamble().equalsIgnoreCase(pcceAbcBusinessUnitLookupKey.getServicePreamble())))
		            return false;
	        
	        if(!(getSourceId().equalsIgnoreCase(pcceAbcBusinessUnitLookupKey.getSourceId())))
		            return false;
	        
	        if(!(getSubBusinessUnit().equalsIgnoreCase(pcceAbcBusinessUnitLookupKey.getSubBusinessUnit())))
		            return false;

	        return true;
	    }
		
		@Override
		public int hashCode() {
	       // hashcode
	    	int hash = 31;
	    	hash = hash + ( this.sourceId != null ? this.sourceId.hashCode() :0);
	    	hash = hash + ( this.businessUnit != null ? this.businessUnit.hashCode() :0);
	    	hash = hash + ( this.destinationId != null ? this.destinationId.hashCode() :0);
	    	hash = hash + ( this.servicePreamble != null ? this.servicePreamble.hashCode() :0);
	    	hash = hash + ( this.subBusinessUnit != null ? this.subBusinessUnit.hashCode() :0);
	    	
	    	return hash;
	    }

}
