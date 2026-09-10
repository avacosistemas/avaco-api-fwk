package ar.com.avaco.fwk.core.component.dto;

import java.io.Serializable;

public abstract class DTOEntity<ID extends Serializable> {

	public abstract void setId(ID id);

	public abstract ID getId();

}
