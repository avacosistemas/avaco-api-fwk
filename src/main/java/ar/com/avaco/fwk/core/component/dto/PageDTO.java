package ar.com.avaco.fwk.core.component.dto;

import java.util.List;

public class PageDTO<DTO extends DTOEntity<?>> {

	public PageDTO() {
	}

	public PageDTO(List<DTO> page, long totalReg) {
		super();
		this.page = page;
		this.totalReg = totalReg;
	}

	private List<DTO> page;

	private long totalReg;

	public List<DTO> getPage() {
		return page;
	}

	public void setPage(List<DTO> page) {
		this.page = page;
	}

	public long getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(long totalReg) {
		this.totalReg = totalReg;
	}

}
