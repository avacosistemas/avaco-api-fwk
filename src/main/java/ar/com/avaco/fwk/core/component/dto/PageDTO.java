package ar.com.avaco.fwk.core.component.dto;

import java.util.List;

public class PageDTO<DTO extends DTOEntity<?>> {

	private List<DTO> list;

	private Integer totalReg;

	private Integer pageSize;

	private Integer page;

	public PageDTO() {
	}

	public PageDTO(List<DTO> list, Integer totalReg, Integer pageSize, Integer page) {
		super();
		this.list = list;
		this.totalReg = totalReg;
		this.pageSize = pageSize;
		this.page = page;
	}

	public PageResponse toPageRepsponse() {
		PageResponse pr = new PageResponse();
		pr.setPage(page);
		pr.setPageSize(pageSize);
		pr.setSearch(null);
		pr.setTotalReg(totalReg);
		return pr;
	}

	public List<DTO> getList() {
		return list;
	}

	public void setList(List<DTO> list) {
		this.list = list;
	}

	public Integer getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

}
