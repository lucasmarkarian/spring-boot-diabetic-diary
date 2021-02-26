package com.bolsadeideas.springboot.diabeticdiary.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "controldiary")
@IdClass(AccountId.class)
public class ControlDiary implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String username_match;
	
	@Id
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;

	@Min(value = 1)
	private Integer glucemiaAntDes;

	@Min(value = 1)
	private Integer glucemiaAntAlm;

	@Min(value = 1)
	private Integer glucemiaAntMer;

	@Min(value = 1)
	private Integer glucemiaAntCen;
	
	private String glucemiaObservaciones;

	@Min(value = 1)
	private Integer insulinaAntDes;

	@Min(value = 1)
	private Integer insulinaAntAlm;

	@Min(value = 1)
	private Integer insulinaAntMer;

	@Min(value = 1)
	private Integer insulinaAntCen;
	
	private String insulinaObservaciones;

	@Min(value = 1)
	private Integer correccionAntDes;

	@Min(value = 1)
	private Integer correccionAntAlm;

	@Min(value = 1)
	private Integer correccionAntMer;

	@Min(value = 1)
	private Integer correccionAntCen;
	
	private String correccionObservaciones;

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getGlucemiaAntDes() {
		return glucemiaAntDes;
	}

	public void setGlucemiaAntDes(Integer glucemiaAntDes) {
		this.glucemiaAntDes = glucemiaAntDes;
	}

	public Integer getGlucemiaAntAlm() {
		return glucemiaAntAlm;
	}

	public void setGlucemiaAntAlm(Integer glucemiaAntAlm) {
		this.glucemiaAntAlm = glucemiaAntAlm;
	}

	public Integer getGlucemiaAntMer() {
		return glucemiaAntMer;
	}

	public void setGlucemiaAntMer(Integer glucemiaAntMer) {
		this.glucemiaAntMer = glucemiaAntMer;
	}

	public Integer getGlucemiaAntCen() {
		return glucemiaAntCen;
	}

	public void setGlucemiaAntCen(Integer glucemiaAntCen) {
		this.glucemiaAntCen = glucemiaAntCen;
	}

	public String getGlucemiaObservaciones() {
		return glucemiaObservaciones;
	}

	public void setGlucemiaObservaciones(String glucemiaObservaciones) {
		this.glucemiaObservaciones = glucemiaObservaciones;
	}

	public Integer getInsulinaAntDes() {
		return insulinaAntDes;
	}

	public void setInsulinaAntDes(Integer insulinaAntDes) {
		this.insulinaAntDes = insulinaAntDes;
	}

	public Integer getInsulinaAntAlm() {
		return insulinaAntAlm;
	}

	public void setInsulinaAntAlm(Integer insulinaAntAlm) {
		this.insulinaAntAlm = insulinaAntAlm;
	}

	public Integer getInsulinaAntMer() {
		return insulinaAntMer;
	}

	public void setInsulinaAntMer(Integer insulinaAntMer) {
		this.insulinaAntMer = insulinaAntMer;
	}

	public Integer getInsulinaAntCen() {
		return insulinaAntCen;
	}

	public void setInsulinaAntCen(Integer insulinaAntCen) {
		this.insulinaAntCen = insulinaAntCen;
	}

	public String getInsulinaObservaciones() {
		return insulinaObservaciones;
	}

	public void setInsulinaObservaciones(String insulinaObservaciones) {
		this.insulinaObservaciones = insulinaObservaciones;
	}

	public Integer getCorreccionAntDes() {
		return correccionAntDes;
	}

	public void setCorreccionAntDes(Integer correccionAntDes) {
		this.correccionAntDes = correccionAntDes;
	}

	public Integer getCorreccionAntAlm() {
		return correccionAntAlm;
	}

	public void setCorreccionAntAlm(Integer correccionAntAlm) {
		this.correccionAntAlm = correccionAntAlm;
	}

	public Integer getCorreccionAntMer() {
		return correccionAntMer;
	}

	public void setCorreccionAntMer(Integer correccionAntMer) {
		this.correccionAntMer = correccionAntMer;
	}

	public Integer getCorreccionAntCen() {
		return correccionAntCen;
	}

	public void setCorreccionAntCen(Integer correccionAntCen) {
		this.correccionAntCen = correccionAntCen;
	}

	public String getCorreccionObservaciones() {
		return correccionObservaciones;
	}

	public void setCorreccionObservaciones(String correccionObservaciones) {
		this.correccionObservaciones = correccionObservaciones;
	}
	
	public String getUsername_match() {
		return username_match;
	}

	public void setUsername_match(String username_match) {
		this.username_match = username_match;
	}

	public boolean glucemiaHasValues() {
		if (this.glucemiaAntDes != null
				|| this.glucemiaAntAlm != null
				|| this.glucemiaAntMer != null
				|| this.glucemiaAntCen != null) {
			return true;
		}
		return false;
	}
	
	public boolean insulinaHasValues() {
		if (this.insulinaAntDes != null
				|| this.insulinaAntAlm != null
				|| this.insulinaAntMer != null
				|| this.insulinaAntCen != null) {
			return true;
		}
		return false;
	}
	
	public boolean correccionHasValues() {
		if (this.correccionAntDes != null
				|| this.correccionAntAlm != null
				|| this.correccionAntMer != null
				|| this.correccionAntCen != null) {
			return true;
		}
		return false;
	}
	
	public boolean hasInfo() {
		if (this.glucemiaHasValues() || this.insulinaHasValues() || this.correccionHasValues()) {
			return true;
		}
		return false;
	}

}
