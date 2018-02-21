package br.com.bean;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.FilhoDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Filho;
import br.com.model.Funcao;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class FilhoBean {

	private Filho filho;
	@Autowired
	private FilhoDAOImpl<Filho> filhoDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Filho> filhos;
	private Integer idBusca;
	
	public Filho getFilho() {
		return filho;
	}

	public void setFilho(Filho filho) {
		this.filho = filho;
	}
	
	public FilhoDAOImpl<Filho> getFilhoDAOImpl() {
		return filhoDAOImpl;
	}	
	
	public void setFilhoDAOImpl(FilhoDAOImpl<Filho> filhoDAOImpl) {
		this.filhoDAOImpl = filhoDAOImpl;
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
	
	public Collection<Filho> getFilhos() throws Exception {
		filhos = filhoDAOImpl.listar(getUnidade());
		return filhos;
	}
	
	public void setFilhos(Collection<Filho> filhos) {
		this.filhos = filhos;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}
	
	public String carregarCadastrar(){
		limpar();
		return "/tela/trabalhador/filho/cadastrarFilho";
	}
	
	public String carregarAlterar() {
		filho = filhoDAOImpl.carregar(idBusca);
		return "/tela/trabalhador/filho/alterarFilho";
	}
	
	public String cadastrar() {
		filhoDAOImpl.salvar(filho);
		limpar();
		return "/tela/trabalhador/filho/cadastrarFilho";
	}
	
	public String alterar() {
		if(validadata()){
			filhoDAOImpl.salvarOuAtualizar(filho);
			return "/tela/trabalhador/filho/listarFilho";
		}else{
			return "/tela/trabalhador/filho/alterarFilho";
		}
	}
	
	public String excluir(){
		filho = filhoDAOImpl.carregar(idBusca);
		filhoDAOImpl.excluir(filho);
		return "/tela/trabalhador/filho/listarFilho";
	}
	
	private boolean validadata() {
		Calendar dataLimite = Calendar.getInstance();
		dataLimite.add(dataLimite.YEAR, -14);
		
		if(filho.getNascimento().before(dataLimite.getTime())){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "IDADE MÁXIMA PERMITIDA DE 14 ANOS", "IDADE MÁXIMA PERMITIDA DE 14 ANOS!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			return true;
		}
	}

	public void limpar(){
		this.filho = null;
		this.filho = new Filho();
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioFilho.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
