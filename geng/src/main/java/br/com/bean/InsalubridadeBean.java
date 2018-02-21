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
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.InsalubridadeDAOImpl;
import br.com.dao.ServicoDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcao;
import br.com.model.Insalubridade;
import br.com.model.Servico;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
@Lazy
public class InsalubridadeBean {

	private Insalubridade insalubridade;
	@Autowired
	private InsalubridadeDAOImpl<Insalubridade> insalubridadeDAOImpl;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Insalubridade> insalubridades;
	private Integer idBusca;
	
	public Insalubridade getInsalubridade() {
		return insalubridade;
	}

	public void setInsalubridade(Insalubridade insalubridade) {
		this.insalubridade = insalubridade;
	}
	
	public InsalubridadeDAOImpl<Insalubridade> getInsalubridadeDAOImpl() {
		return insalubridadeDAOImpl;
	}	
	
	public void setInsalubridadeDAOImpl(InsalubridadeDAOImpl<Insalubridade> insalubridadeDAOImpl) {
		this.insalubridadeDAOImpl = insalubridadeDAOImpl;
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

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public Collection<Insalubridade> getInsalubridades() throws Exception {
		insalubridades = insalubridadeDAOImpl.listar(getUnidade());
		return insalubridades;
	}
	
	public void setInsalubridades(Collection<Insalubridade> insalubridades) {
		this.insalubridades = insalubridades;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}
	
	public String carregarCadastrar(){
		System.out.println("carregando insalubridade!");
		limpar();
		return "/tela/servico/insalubridade/cadastrarInsalubridade";
	}
	
	public String carregarAlterar() {
		insalubridade = insalubridadeDAOImpl.carregar(idBusca);
		return "/tela/servico/insalubridade/alterarInsalubridade";
	}
	
	public String cadastrar() {
		System.out.println("cadastrando insalubridade!");
		insalubridade.setUnidade(getUnidade());
		insalubridadeDAOImpl.salvar(insalubridade);
		limpar();
		return "/tela/servico/insalubridade/cadastrarInsalubridade";
	}
	
	public String alterar() {
		insalubridadeDAOImpl.salvarOuAtualizar(insalubridade);
		return "/tela/servico/insalubridade/listarInsalubridade";
	}
	
	public void excluir(){
		insalubridade = insalubridadeDAOImpl.carregar(idBusca);
		insalubridadeDAOImpl.excluir(insalubridade);
	}
	
	public void limpar(){
		this.insalubridade = null;
		this.insalubridade = new Insalubridade();
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioInsalubridade.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
