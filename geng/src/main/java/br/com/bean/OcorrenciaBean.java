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

import br.com.dao.OcorrenciaDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcao;
import br.com.model.Ocorrencia;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class OcorrenciaBean {
	private Ocorrencia ocorrencia;
	@Autowired
	private OcorrenciaDAOImpl<Ocorrencia> ocorrenciaDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Ocorrencia> ocorrencias;
	private Integer idBusca;
	private String mensagem;
	private List<SelectItem> listaOcorrencia;
	
	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}
	
	public OcorrenciaDAOImpl<Ocorrencia> getOcorrenciaDAOImpl() {
		return ocorrenciaDAOImpl;
	}	
	
	public void setOcorrenciaDAOImpl(OcorrenciaDAOImpl<Ocorrencia> ocorrenciaDAOImpl) {
		this.ocorrenciaDAOImpl = ocorrenciaDAOImpl;
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
	
	public Collection<Ocorrencia> getOcorrencias() throws Exception {
		ocorrencias = ocorrenciaDAOImpl.listar(getUnidade());
		return ocorrencias;
	}
	
	public void setOcorrencias(Collection<Ocorrencia> ocorrencias) {
		this.ocorrencias = ocorrencias;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	@Transactional
	public List<SelectItem> getListaOcorrencia() throws Exception {
		listaOcorrencia = new ArrayList<SelectItem>();
//		OcorrenciaDAOImpl<Ocorrencia> ocorrenciaDAOImpl = new OcorrenciaDAOImpl<Ocorrencia>();
		Collection<Ocorrencia> ocorrencia = ocorrenciaDAOImpl.listar(getUnidade());
		for (Ocorrencia c : ocorrencia) {
			listaOcorrencia.add(new SelectItem(c.getCodigo(), String.valueOf(c.getCodigo()) + " - " + c.getDescricao()));
		}
		return listaOcorrencia;
	}
	
	public String carregarCadastrar() {
		limpar();
		return "/tela/trabalhador/ocorrencia/cadastrarOcorrencia";
	}
	
	public String carregarAlterar() {
		ocorrencia = ocorrenciaDAOImpl.carregar(idBusca);
		return "/tela/trabalhador/ocorrencia/alterarOcorrencia";
	}
	
	public String cadastrar() {
		ocorrencia.setUnidade(getUnidade());
		ocorrenciaDAOImpl.salvar(ocorrencia);
		limpar();
		return "/tela/trabalhador/ocorrencia/cadastrarOcorrencia";
	}
	
	public String alterar() {
		ocorrenciaDAOImpl.salvarOuAtualizar(ocorrencia);
		return "/tela/trabalhador/ocorrencia/listarOcorrencia";
	}
	
	public void excluir(){
		ocorrencia = ocorrenciaDAOImpl.carregar(idBusca);
		ocorrenciaDAOImpl.excluir(ocorrencia);
	}
	
	public void limpar(){
		ocorrencia = null;
		ocorrencia = new Ocorrencia();
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioOcorrencia.jasper", "imprimirPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
