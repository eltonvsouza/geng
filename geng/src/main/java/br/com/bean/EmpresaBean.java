package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.EmpresaDAOImpl;
import br.com.dao.ResponsavelDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Empresa;
import br.com.model.Funcao;
import br.com.model.Responsavel;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class EmpresaBean {

	private Empresa empresa;
	@Autowired
	private EmpresaDAOImpl<Empresa> empresaDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Empresa> empresas;
	private List<SelectItem> listaEmpresa;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
	private String foco;

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public EmpresaDAOImpl<Empresa> getEmpresaDAOImpl() {
		return empresaDAOImpl;
	}

	public void setEmpresaDAOImpl(EmpresaDAOImpl<Empresa> empresaDAOImpl) {
		this.empresaDAOImpl = empresaDAOImpl;
	}

	public UsuarioDAOImpl<Usuario> getUsuarioDAOImpl() {
		return usuarioDAOImpl;
	}

	public void setUsuarioDAOImpl(UsuarioDAOImpl<Usuario> usuarioDAOImpl) {
		this.usuarioDAOImpl = usuarioDAOImpl;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
	}

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public Collection<Empresa> getEmpresas() throws Exception {
		empresas = empresaDAOImpl.listar(getUnidade());
		return empresas;
	}

	public void setEmpresas(Collection<Empresa> empresas) {
		this.empresas = empresas;
	}

	@Transactional
	public List<SelectItem> getListaEmpresa() throws Exception {
		listaEmpresa = new ArrayList<SelectItem>();
//		EmpresaDAOImpl<Empresa> empresaDAOImpl = new EmpresaDAOImpl<Empresa>();
		Collection<Empresa> empresa = empresaDAOImpl.listar(getUnidade());
		for (Empresa c : empresa) {
			listaEmpresa.add(new SelectItem(c.getCodigo(), c.getInscricao() + " - " + c.getNome()));
		}
		return listaEmpresa;
	}
	
	public Integer getIdBusca() {
		return idBusca;
	}

	public void setIdBusca(Integer idBusca) {
		this.idBusca = idBusca;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isDisableEndereco() {
		return disableEndereco;
	}

	public void setDisableEndereco(boolean disableEndereco) {
		this.disableEndereco = disableEndereco;
	}

	public String getFoco() {
		return foco;
	}

	public void setFoco(String foco) {
		this.foco = foco;
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/empresa/cadastrarEmpresa";
	}

	public String carregarAlterar() {
		empresa = empresaDAOImpl.carregar(idBusca);
		return "/tela/empresa/alterarEmpresa";
	}

	public String cadastrar() {
		empresaDAOImpl.salvar(empresa);
		limpar();
		return "/tela/empresa/cadastrarEmpresa";
	}

	public String alterar() {
		empresaDAOImpl.salvarOuAtualizar(empresa);
		return "/tela/empresa/listarEmpresa";
	}

	public void excluir() {
		empresa = empresaDAOImpl.carregar(idBusca);
		empresaDAOImpl.excluir(empresa);
	}
	
	public void limpar() {
		empresa = null;
		empresa = new Empresa();
		foco = "#inscricao";
		disableEndereco = false;
		empresa.setTipo("cnpj");
		empresa.setUf(18);
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioEmpresa.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 e.printStackTrace();
		 }
	 }
}
