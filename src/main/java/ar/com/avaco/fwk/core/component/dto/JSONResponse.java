/**
 * 
 */
package ar.com.avaco.fwk.core.component.dto;

/**
 * 
 *
 */
public class JSONResponse {

	public static final String ERROR = "ERROR";
	public static final String OK = "OK";

	private String status;
	private Object data;

	private Boolean ok;

	private String error;

	private PageResponse page;

	public JSONResponse() {

	}

	public JSONResponse(PageDTO<?> dto) {
		this.data = dto.getList();
		this.page = dto.toPageRepsponse();
		this.status = OK;
		this.ok = true;

	}

	public JSONResponse(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
		this.ok = true;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public PageResponse getPage() {
		return page;
	}

	public void setPage(PageResponse page) {
		this.page = page;
	}

	public Boolean getOk() {
		return ok;
	}

	public void setOk(Boolean ok) {
		this.ok = ok;
	}

}