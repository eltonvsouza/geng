package br.com.model;

// Generated 26/10/2012 15:13:15 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Unidade generated by hbm2java
 */
@Entity
@Table(name = "unidade", catalog = "geng")
public class Unidade implements java.io.Serializable {

	private Integer codigo;
	private String descricao;
	private Date validade;
	private Set<Geral> gerals = new HashSet<Geral>(0);
	private Set<Diario> diarios = new HashSet<Diario>(0);
	private Set<Trabalhador> trabalhadors = new HashSet<Trabalhador>(0);
	private Set<Insalubridade> insalubridades = new HashSet<Insalubridade>(0);
	private Set<Responsavel> responsavels = new HashSet<Responsavel>(0);
	private Set<Recibo> recibos = new HashSet<Recibo>(0);
	private Set<Configuracao> configuracaos = new HashSet<Configuracao>(0);
	private Set<Empresa> empresas = new HashSet<Empresa>(0);
	private Set<Ocorrencia> ocorrencias = new HashSet<Ocorrencia>(0);
	private Set<Funcao> funcaos = new HashSet<Funcao>(0);
	private Set<Usuario> usuarios = new HashSet<Usuario>(0);
	private Set<Servico> servicos = new HashSet<Servico>(0);

	public Unidade() {
	}

	public Unidade(String descricao, Date validade) {
		this.descricao = descricao;
		this.validade = validade;
	}

	public Unidade(String descricao, Date validade, Set<Geral> gerals,
			Set<Diario> diarios, Set<Trabalhador> trabalhadors,
			Set<Insalubridade> insalubridades, Set<Responsavel> responsavels,
			Set<Recibo> recibos, Set<Configuracao> configuracaos,
			Set<Empresa> empresas, Set<Ocorrencia> ocorrencias,
			Set<Funcao> funcaos, Set<Usuario> usuarios, Set<Servico> servicos) {
		this.descricao = descricao;
		this.validade = validade;
		this.gerals = gerals;
		this.diarios = diarios;
		this.trabalhadors = trabalhadors;
		this.insalubridades = insalubridades;
		this.responsavels = responsavels;
		this.recibos = recibos;
		this.configuracaos = configuracaos;
		this.empresas = empresas;
		this.ocorrencias = ocorrencias;
		this.funcaos = funcaos;
		this.usuarios = usuarios;
		this.servicos = servicos;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "codigo", unique = true, nullable = false)
	public Integer getCodigo() {
		return this.codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	@Column(name = "descricao", nullable = false, length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "validade", nullable = false, length = 10)
	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Geral> getGerals() {
		return this.gerals;
	}

	public void setGerals(Set<Geral> gerals) {
		this.gerals = gerals;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Diario> getDiarios() {
		return this.diarios;
	}

	public void setDiarios(Set<Diario> diarios) {
		this.diarios = diarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Trabalhador> getTrabalhadors() {
		return this.trabalhadors;
	}

	public void setTrabalhadors(Set<Trabalhador> trabalhadors) {
		this.trabalhadors = trabalhadors;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Insalubridade> getInsalubridades() {
		return this.insalubridades;
	}

	public void setInsalubridades(Set<Insalubridade> insalubridades) {
		this.insalubridades = insalubridades;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Responsavel> getResponsavels() {
		return this.responsavels;
	}

	public void setResponsavels(Set<Responsavel> responsavels) {
		this.responsavels = responsavels;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Recibo> getRecibos() {
		return this.recibos;
	}

	public void setRecibos(Set<Recibo> recibos) {
		this.recibos = recibos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Configuracao> getConfiguracaos() {
		return this.configuracaos;
	}

	public void setConfiguracaos(Set<Configuracao> configuracaos) {
		this.configuracaos = configuracaos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Empresa> getEmpresas() {
		return this.empresas;
	}

	public void setEmpresas(Set<Empresa> empresas) {
		this.empresas = empresas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Ocorrencia> getOcorrencias() {
		return this.ocorrencias;
	}

	public void setOcorrencias(Set<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Funcao> getFuncaos() {
		return this.funcaos;
	}

	public void setFuncaos(Set<Funcao> funcaos) {
		this.funcaos = funcaos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(Set<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "unidade")
	public Set<Servico> getServicos() {
		return this.servicos;
	}

	public void setServicos(Set<Servico> servicos) {
		this.servicos = servicos;
	}

}
