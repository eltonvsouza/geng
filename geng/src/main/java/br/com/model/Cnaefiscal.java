package br.com.model;

// Generated 26/10/2012 15:13:15 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Cnaefiscal generated by hbm2java
 */
@Entity
@Table(name = "cnaefiscal", catalog = "geng")
public class Cnaefiscal implements java.io.Serializable {

	private Integer codigo;
	private String descricao;

	public Cnaefiscal() {
	}

	public Cnaefiscal(Integer codigo, String descricao) {
		this.codigo = codigo;
		this.descricao = descricao;
	}

	@Id
	@Column(name = "codigo", unique = true, nullable = false)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "descricao", nullable = false, length = 200)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
