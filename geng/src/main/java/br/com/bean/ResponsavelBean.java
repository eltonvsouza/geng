package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.ResponsavelDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Responsavel;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ResponsavelBean {

	private Responsavel responsavel;
	@Autowired
	private ResponsavelDAOImpl<Responsavel> responsavelDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Responsavel> responsaveis;
	private List<SelectItem> listaResponsavel;
	private Integer idBusca;
	private String mensagem;
	private boolean disableEndereco;
	private String foco;

	public Responsavel getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(Responsavel responsavel) {
		this.responsavel = responsavel;
	}
	
	public ResponsavelDAOImpl<Responsavel> getResponsavelDAOImpl() {
		return responsavelDAOImpl;
	}

	public void setResponsavelDAOImpl(ResponsavelDAOImpl<Responsavel> responsavelDAOImpl) {
		this.responsavelDAOImpl = responsavelDAOImpl;
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
	
	public Collection<Responsavel> getResponsaveis() throws Exception  {
		responsaveis = responsavelDAOImpl.listar(getUnidade());
		return responsaveis;
	}
	
	public void setResponsaveis(Collection<Responsavel> responsaveis) {
		this.responsaveis = responsaveis;
	}
	
	@Transactional
	public List<SelectItem> getListaResponsavel() throws Exception {
		listaResponsavel = new ArrayList<SelectItem>();
//		ResponsavelDAOImpl<Responsavel> responsavelDAOImpl = new ResponsavelDAOImpl<Responsavel>();
		Collection<Responsavel> responsavel = responsavelDAOImpl.listar(getUnidade());
		for (Responsavel c : responsavel) {
			listaResponsavel.add(new SelectItem(c, c.getInscricao() + " - " + c.getNome()));
		}
		return listaResponsavel;
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

	public String carregarCadastrar(){
		limpar();
		return "/tela/responsavel/cadastrarResponsavel";
	}
	
	public String carregarAlterar() {
		responsavel = responsavelDAOImpl.carregar(idBusca);
		return "/tela/responsavel/alterarResponsavel";
	}
	
	public String cadastrar() {
		responsavelDAOImpl.salvar(responsavel);
		limpar();
		return "/tela/responsavel/cadastrarResponsavel";
	}
	
	public String alterar() {
		responsavelDAOImpl.salvarOuAtualizar(responsavel);
		return "/tela/responsavel/listarResponsavel";
	}
	
	public void excluir(){
		responsavel = responsavelDAOImpl.carregar(idBusca);
		responsavelDAOImpl.excluir(responsavel);
	}
	
	public void limpar(){
		responsavel = null;
		responsavel = new Responsavel();
		foco = "#inscricao";
		disableEndereco = false;
		responsavel.setTipo("cnpj");
		responsavel.setTipofornecedor("cnpj");
		responsavel.setUf(18);
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioResponsavel.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
