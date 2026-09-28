package ar.com.avaco.fwk.core.component.dto.entity;

import java.io.Serializable;

import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;

import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class DTOEntity<ID extends Serializable> {

	public abstract void setId(ID id);

	public abstract ID getId();
	
	@JsonIgnore
	public ProjectionList getProjections() {
		return Projections.projectionList();
	}

}
