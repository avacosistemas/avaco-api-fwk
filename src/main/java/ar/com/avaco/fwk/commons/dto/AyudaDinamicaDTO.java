package ar.com.avaco.fwk.commons.dto;

import ar.com.avaco.fwk.core.component.dto.entity.DTOAuditableEntity;

public class AyudaDinamicaDTO extends DTOAuditableEntity<Long> {

	private Long id;

	private String path;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public static AyudaDinamicaDTO ofId(Long idPais) {
		AyudaDinamicaDTO et = new AyudaDinamicaDTO();
		et.setId(idPais);
		return et;
	}

}
