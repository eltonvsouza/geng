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

import br.com.dao.ServicoDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcao;
import br.com.model.Servico;
import br.com.model.Trabalhador;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ServicoBean {

	private Servico servico;
	@Autowired
	private ServicoDAOImpl<Servico> servicoDAOImpl;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Servico> servicos;
	private List<SelectItem> listaServico;
	private Integer idBusca;
	private Boolean disable;
	private String foco;
	
	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}
	
	public ServicoDAOImpl<Servico> getServicoDAOImpl() {
		return servicoDAOImpl;
	}	
	
	public void setServicoDAOImpl(ServicoDAOImpl<Servico> servicoDAOImpl) {
		this.servicoDAOImpl = servicoDAOImpl;
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
	
	public Collection<Servico> getServicos() throws Exception {
		servicos = servicoDAOImpl.listar(getUnidade());
		return servicos;
	}
	
	public void setServicos(Collection<Servico> servicos) {
		this.servicos = servicos;
	}
	
	@Transactional
	public List<SelectItem> getListaServico() throws Exception {
		listaServico = new ArrayList<SelectItem>();
		Collection<Servico> servico = servicoDAOImpl.listar(getUnidade());
		for (Servico c : servico) {
			listaServico.add(new SelectItem(c, c.getCodigo() + " - " + c.getDescricao()));
		}
		return listaServico;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}
	
	public Boolean getDisable() {
		return disable;
	}

	public void setDisable(Boolean disable) {
		this.disable = disable;
	}

	public String getFoco() {
		return foco;
	}

	public void setFoco(String foco) {
		this.foco = foco;
	}

//	public List<Servico> autocompleteServico(Object event) throws Exception{
//		String cod = (String) event;
//		ArrayList<Servico>retorno = new ArrayList<Servico>();
//		ServicoDAOImpl<Servico> servicoDAOImpl = new ServicoDAOImpl<Servico>();
//		Collection<Servico> servico = servicoDAOImpl.listar(getUnidade());
//		for (Servico c : servico) {
//			if((c.getCodigo() != null && String.valueOf(c.getCodigo()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod))
//				retorno.add(c);
//		}
//		return retorno;
//	}
	
	@Transactional
	public List<String> autocompleteServico(String cod) throws Exception{
		ArrayList<String>retorno = new ArrayList<String>();
		Collection<Servico> servico = servicoDAOImpl.listar(getUnidade());
		for (Servico c : servico) {
			if((c != null && c.getDescricao().indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
				retorno.add(c.getDescricao());
			}
		}
		return retorno;
	}
	
	public String carregarCadastrar() {
		limpar();
		return "/tela/servico/cadastrarServico";
	}
	
	public String carregarAlterar() {
		servico = servicoDAOImpl.carregar(idBusca);
		this.disable = true;
		this.foco = "#descricao";
		return "/tela/servico/alterarServico";
	}
	
	public String cadastrar() {
		servico.setUnidade(getUnidade());
		servicoDAOImpl.salvar(servico);
		limpar();
		return "/tela/servico/cadastrarServico";
	}
	
	public String alterar() {
		servicoDAOImpl.salvarOuAtualizar(servico);
		return "/tela/servico/listarServico";
	}
	
	public void excluir(){
		servico = servicoDAOImpl.carregar(idBusca);
		servicoDAOImpl.excluir(servico);
	}
	
	public void limpar(){
		this.servico = null;
		this.servico = new Servico();
		
		this.disable = false;
		this.foco = "#codigo";
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioServico.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
