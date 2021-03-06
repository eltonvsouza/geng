package br.com.model;

// Generated 07/07/2011 12:15:50 by Hibernate Tools 3.2.4.GA

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Uf generated by hbm2java
 */
@Entity
@Table(name = "uf", catalog = "ceng")
public class Uf implements java.io.Serializable {

	private int ufCodigo;
	private String ufSigla;
	private String ufDescricao;

	public Uf() {
	}

	public Uf(int ufCodigo) {
		this.ufCodigo = ufCodigo;
	}

	public Uf(int ufCodigo, String ufSigla, String ufDescricao) {
		this.ufCodigo = ufCodigo;
		this.ufSigla = ufSigla;
		this.ufDescricao = ufDescricao;
	}

	@Id
	@Column(name = "uf_codigo", unique = true, nullable = false)
	public int getUfCodigo() {
		return this.ufCodigo;
	}

	public void setUfCodigo(int ufCodigo) {
		this.ufCodigo = ufCodigo;
	}

	@Column(name = "uf_sigla", length = 2)
	public String getUfSigla() {
		return this.ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	@Column(name = "uf_descricao", length = 72)
	public String getUfDescricao() {
		return this.ufDescricao;
	}

	public void setUfDescricao(String ufDescricao) {
		this.ufDescricao = ufDescricao;
	}

}
