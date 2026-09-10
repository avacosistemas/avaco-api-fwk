package ar.com.avaco.fwk.core.component.dto;

public class PageResponse {

	private Integer totalReg;

	private Integer page;

	private Integer pageSize;

	private String search;

	public Integer getTotalReg() {
		return totalReg;
	}

	public void setTotalReg(Integer totalReg) {
		this.totalReg = totalReg;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

}
