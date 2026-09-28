package ar.com.avaco.fwk.commons.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import ar.com.avaco.fwk.core.domain.AuditableEntity;

@Entity
@Table(name = "AYUDA_DINAMICA")
public class AyudaDinamica extends AuditableEntity<Long> {

	private static final long serialVersionUID = 3868279756784146751L;

	@Id
	@GeneratedValue(generator = "AYUDA_DINAMICA_SEQ")
	@GenericGenerator(name = "AYUDA_DINAMICA_SEQ", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "sequence_name", value = "AYUDA_DINAMICA_SEQ"),
			@org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
			@org.hibernate.annotations.Parameter(name = "increment_size", value = "1") })
	@Column(name = "ID_AYUDA_DINAMICA", unique = true, nullable = false)
	private Long id;

	@Column(name = "PATH", updatable = false)
	private String path;

	@Column(name = "CONTENT")
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

	public static AyudaDinamica ofId(Long idPais) {
		AyudaDinamica et = new AyudaDinamica();
		et.setId(idPais);
		return et;
	}

}
