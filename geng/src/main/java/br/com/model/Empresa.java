package br.com.model;

// Generated 26/10/2012 15:13:15 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Empresa generated by hbm2java
 */
@Entity
@Table(name = "empresa", catalog = "geng", uniqueConstraints = @UniqueConstraint(columnNames = {
		"unidade", "inscricao" }))
public class Empresa implements java.io.Serializable {

	private Integer codigo;
	private Responsavel responsavel;
	private Unidade unidade;
	private String tipo;
	private String inscricao;
	private String rasaosocial;
	private String nome;
	private int cnaefiscal;
	private String fpas;
	private String iestadual;
	private String pais;
	private String cidade;
	private String bairro;
	private String logradouro;
	private Integer numero;
	private Integer uf;
	private String cep;
	private String complemento;
	private String telefone;
	private String celular;
	private Set<Trabalhador> trabalhadors = new HashSet<Trabalhador>(0);

	public Empresa() {
	}

	public Empresa(Responsavel responsavel, Unidade unidade, String tipo,
			String inscricao, String nome, int cnaefiscal, String fpas) {
		this.responsavel = responsavel;
		this.unidade = unidade;
		this.tipo = tipo;
		this.inscricao = inscricao;
		this.nome = nome;
		this.cnaefiscal = cnaefiscal;
		this.fpas = fpas;
	}

	public Empresa(Responsavel responsavel, Unidade unidade, String tipo,
			String inscricao, String rasaosocial, String nome, int cnaefiscal,
			String fpas, String iestadual, String pais, String cidade,
			String bairro, String logradouro, Integer numero, Integer uf,
			String cep, String complemento, String telefone, String celular,
			Set<Trabalhador> trabalhadors) {
		this.responsavel = responsavel;
		this.unidade = unidade;
		this.tipo = tipo;
		this.inscricao = inscricao;
		this.rasaosocial = rasaosocial;
		this.nome = nome;
		this.cnaefiscal = cnaefiscal;
		this.fpas = fpas;
		this.iestadual = iestadual;
		this.pais = pais;
		this.cidade = cidade;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.uf = uf;
		this.cep = cep;
		this.complemento = complemento;
		this.telefone = telefone;
		this.celular = celular;
		this.trabalhadors = trabalhadors;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "responsavel", nullable = false)
	public Responsavel getResponsavel() {
		return this.responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}

	@NotNull(message="Campo Unidade: Obrigat�rio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unidade", nullable = false)
	public Unidade getUnidade() {
		return this.unidade;
	}

	public void setUnidade(Unidade unidade) {
		this.unidade = unidade;
	}

	@Column(name = "tipo", nullable = false, length = 4)
	public String getTipo() {
		return this.tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@NotEmpty(message="Campo Inscri��o: Obrigat�rio")
	@Length(min=14, max=18, message="Campo Inscri��o: Preencha de acordo com a m�scara")
	@Column(name = "inscricao", nullable = false, length = 18)
	public String getInscricao() {
		return this.inscricao;
	}

	public void setInscricao(String inscricao) {
		this.inscricao = inscricao;
	}

	@Column(name = "rasaosocial", length = 200)
	public String getRasaosocial() {
		return this.rasaosocial;
	}

	public void setRasaosocial(String rasaosocial) {
		this.rasaosocial = rasaosocial;
	}

	@NotEmpty(message="Campo Nome: Obrigat�rio")
	@Length(min=3, max=200, message="Campo Nome: Tamanho m�nimo de 3 caracteres")
	@Column(name = "nome", nullable = false, length = 200)
	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@NotEmpty(message="Campo CNAE/Fiscal: Obrigat�rio")
	@Column(name = "cnaefiscal", nullable = false)
	public int getCnaefiscal() {
		return this.cnaefiscal;
	}

	public void setCnaefiscal(int cnaefiscal) {
		this.cnaefiscal = cnaefiscal;
	}

	@NotEmpty(message="Campo FPAS: Obrigat�rio")
//	@Range(min=1, max=5, message="Campo FPAS: Tamanho m�nimo de 1 d�gito")
	@Column(name = "fpas", nullable = false, length = 5)
	public String getFpas() {
		return this.fpas;
	}

	public void setFpas(String fpas) {
		this.fpas = fpas;
	}

	@Column(name = "iestadual", length = 14)
	public String getIestadual() {
		return this.iestadual;
	}

	public void setIestadual(String iestadual) {
		this.iestadual = iestadual;
	}

	@Column(name = "pais", length = 50)
	public String getPais() {
		return this.pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	@Column(name = "cidade", length = 100)
	public String getCidade() {
		return this.cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@Column(name = "bairro", length = 100)
	public String getBairro() {
		return this.bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	@Column(name = "logradouro", length = 100)
	public String getLogradouro() {
		return this.logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	@Column(name = "numero")
	public Integer getNumero() {
		return this.numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	@Column(name = "uf")
	public Integer getUf() {
		return this.uf;
	}

	public void setUf(Integer uf) {
		this.uf = uf;
	}

	@Length(min=0, max=9, message="Campo CEP: Tamanho obrigat�rio de 9 caracteres")
	@Column(name = "cep", length = 11)
	public String getCep() {
		return this.cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Column(name = "complemento", length = 100)
	public String getComplemento() {
		return this.complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	@Column(name = "telefone", length = 13)
	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	@Column(name = "celular", length = 13)
	public String getCelular() {
		return this.celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empresa")
	public Set<Trabalhador> getTrabalhadors() {
		return this.trabalhadors;
	}

	public void setTrabalhadors(Set<Trabalhador> trabalhadors) {
		this.trabalhadors = trabalhadors;
	}

}
