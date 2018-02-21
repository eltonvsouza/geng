package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.TrabalhadorDAOImpl;
import br.com.dao.UfDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Funcao;
import br.com.model.Ocorrencia;
import br.com.model.Trabalhador;
import br.com.model.Uf;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class TrabalhadorBean {

	private Trabalhador trabalhador;
	private Funcao funcao;
	private Ocorrencia ocorrencia;
	@Autowired
	private TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private UfDAOImpl<Uf> ufDAOImpl;
	private Collection<Trabalhador> trabalhadores;
	private Integer idBusca;
	private String mensagem;
	private Boolean disable;
	private String foco;
	private boolean disableEndereco;
	
	public Trabalhador getTrabalhador() {
		return trabalhador;
	}

	public void setTrabalhador(Trabalhador trabalhador) {
		this.trabalhador = trabalhador;
	}

	public Funcao getFuncao() {
		return funcao;
	}

	public void setFuncao(Funcao funcao) {
		this.funcao = funcao;
	}

	public Ocorrencia getOcorrencia() {
		return ocorrencia;
	}

	public void setOcorrencia(Ocorrencia ocorrencia) {
		this.ocorrencia = ocorrencia;
	}

	public TrabalhadorDAOImpl<Trabalhador> getTrabalhadorDAOImpl() {
		return trabalhadorDAOImpl;
	}

	public void setTrabalhadorDAOImpl(TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl) {
		this.trabalhadorDAOImpl = trabalhadorDAOImpl;
	}

	public UfDAOImpl<Uf> getUfDAOImpl() {
		return ufDAOImpl;
	}

	public void setUfDAOImpl(UfDAOImpl<Uf> ufDAOImpl) {
		this.ufDAOImpl = ufDAOImpl;
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
	
	public Collection<Trabalhador> getTrabalhadores() throws Exception {
		trabalhadores = trabalhadorDAOImpl.listar(getUnidade());
		return trabalhadores;
	}

	public void setTrabalhadores(Collection<Trabalhador> trabalhadores) {
		this.trabalhadores = trabalhadores;
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

//	@Transactional
//	public List<Trabalhador> autocompleteTrabalhador(Object event) throws Exception{
//		String cod = (String) event;
//		ArrayList<Trabalhador>retorno = new ArrayList<Trabalhador>();
//		TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl = new TrabalhadorDAOImpl<Trabalhador>();
//		Collection<Trabalhador> trabalhador = trabalhadorDAOImpl.listar(getUnidade());
//		for (Trabalhador c : trabalhador) {
//			if((c.getMatricula() != null && String.valueOf(c.getMatricula()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
//				retorno.add(c);
//			}
//		}
//		return retorno;
//	}
	
	@Transactional
	public List<String> autocompleteTrabalhador(String cod) throws Exception{
		ArrayList<String>retorno = new ArrayList<String>();
		Collection<Trabalhador> trabalhador = trabalhadorDAOImpl.listar(getUnidade());
		for (Trabalhador c : trabalhador) {
			if((c != null && c.getNome().indexOf(cod.toLowerCase()) == 0) || "".equals(cod)){
				retorno.add(c.getNome());
			}
		}
		return retorno;
	}
	
	public boolean isDisableEndereco() {
		return disableEndereco;
	}

	public void setDisableEndereco(boolean disableEndereco) {
		this.disableEndereco = disableEndereco;
	}

	public String carregarCadastrar() {
		limpar();
		return "/tela/trabalhador/cadastrarTrabalhador";
	}

	public String carregarAlterar() {
		trabalhador = trabalhadorDAOImpl.carregar(idBusca);
		this.disable = true;
		this.foco = "#nome";
		return "/tela/trabalhador/alterarTrabalhador";
	}

	public String cadastrar() {
		trabalhadorDAOImpl.salvar(trabalhador);
		limpar();
		return "/tela/trabalhador/cadastrarTrabalhador";
	}

	public String alterar() {
		trabalhadorDAOImpl.salvarOuAtualizar(trabalhador);
		return "/tela/trabalhador/listarTrabalhador";
	}

	public void excluir() {
		trabalhador = trabalhadorDAOImpl.carregar(idBusca);
		trabalhadorDAOImpl.excluir(trabalhador);
	}

	public void limpar() {
		trabalhador = null;
		trabalhador = new Trabalhador();
		disableEndereco = false;
		trabalhador.setUf(18);
		trabalhador.setSexo("M");
		this.disable = false;
		setFoco("#matricula");
	}

	 public void relatorioGeral() throws Exception {
		 GenericRelatorio relatorio = new GenericRelatorio();
		 Map parametro = new HashMap();
		 try {
			 relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioTrabalhador.jasper", "salvarPdf", parametro);
		 } catch (Exception e) {
		 	e.printStackTrace();
		 }
	 }
}
