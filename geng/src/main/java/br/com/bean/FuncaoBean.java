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

import br.com.dao.FuncaoDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcao;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class FuncaoBean {
	private Funcao funcao;
	@Autowired
	private FuncaoDAOImpl<Funcao> funcaoDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Funcao> funcoes;
	private List<SelectItem> listaFuncao;
	private Integer idBusca;
	private String mensagem;
	
	public Funcao getFuncao() {
		return this.funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public FuncaoDAOImpl<Funcao> getFuncaoDAOImpl() {
		return funcaoDAOImpl;
	}

	public void setFuncaoDAOImpl(FuncaoDAOImpl<Funcao> funcaoDAOImpl) {
		this.funcaoDAOImpl = funcaoDAOImpl;
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
	
	public Collection<Funcao> getFuncoes() throws Exception{
		funcoes = funcaoDAOImpl.listar(getUnidade());
		return funcoes;
	}
	
	public void setFuncoes(Collection<Funcao> funcoes) {
		this.funcoes = funcoes;
	}
	
	@Transactional
	public List<SelectItem> getListaFuncao() throws Exception {
		listaFuncao = new ArrayList<SelectItem>();
		Collection<Funcao> funcao = funcaoDAOImpl.listar(getUnidade());
		for (Funcao c : funcao) {
			listaFuncao.add(new SelectItem(c.getCodigo(), c.getDescricao()));
		}
		return listaFuncao;
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

	public String carregarCadastrar(){
		limpar();
		return "/tela/trabalhador/funcao/cadastrarFuncao";
	}
	
	public String carregarAlterar() {
		funcao = funcaoDAOImpl.carregar(idBusca);
		return "/tela/trabalhador/funcao/alterarFuncao";
	}
	
	public String cadastrar() {
		funcao.setUnidade(getUnidade());
		funcaoDAOImpl.salvar(funcao);
		limpar();
		return "/tela/trabalhador/funcao/cadastrarFuncao";
	}
	
	public String alterar() {
		funcaoDAOImpl.salvarOuAtualizar(funcao);
		return "/tela/trabalhador/funcao/listarFuncao";
	}
	
	public String excluir(){
		funcao = funcaoDAOImpl.carregar(idBusca);
		funcaoDAOImpl.excluir(funcao);
		return "/tela/trabalhador/funcao/listarFuncao";
	}
	
	public void limpar(){
		funcao = null;
		funcao = new Funcao();
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioFuncao.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
