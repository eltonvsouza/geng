package br.com.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.CnaeFiscalDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Cnaefiscal;
import br.com.model.Transportadora;
import br.com.model.Unidade;
import br.com.model.Usuario;

@Component
@Scope("session")
@ManagedBean
@RequestScoped
@Controller
public class CnaeFiscalBean {
	private Cnaefiscal cnaeFiscal;
	@Autowired
	private CnaeFiscalDAOImpl<Cnaefiscal> cnaeFiscalDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Cnaefiscal> cnaeFiscais;
	private Integer idBusca;
	
	public Cnaefiscal getCnaefiscal() {
		return this.cnaeFiscal;
	}

	public void setCnaefiscal(Cnaefiscal cnaefiscal) {
		this.cnaeFiscal = cnaefiscal;
	}

	public CnaeFiscalDAOImpl<Cnaefiscal> getCnaeFiscalDAOImpl() {
		return cnaeFiscalDAOImpl;
	}

	public void setCnaeFiscalDAOImpl(CnaeFiscalDAOImpl<Cnaefiscal> cnaefiscalDAOImpl) {
		this.cnaeFiscalDAOImpl = cnaefiscalDAOImpl;
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
	
	public Collection<Cnaefiscal> getCnaefiscais() throws Exception{
		cnaeFiscais = cnaeFiscalDAOImpl.listar(getUnidade());
		return cnaeFiscais;
	}
	
	public void setFuncoes(Collection<Cnaefiscal> cnaeFiscais) {
		this.cnaeFiscais = cnaeFiscais;
	}
	
	@Transactional
	public List<Cnaefiscal> autocompleteCnaefiscal(Object event) throws Exception{
		String cod = (String) event;
		ArrayList<Cnaefiscal>retorno = new ArrayList<Cnaefiscal>();
//		CnaeFiscalDAOImpl<Cnaefiscal> cnaeFiscalDAOImpl = new CnaeFiscalDAOImpl<Cnaefiscal>();
		Collection<Cnaefiscal> cnaeFiscal = cnaeFiscalDAOImpl.listar(getUnidade());
		for (Cnaefiscal c : cnaeFiscal) {
			if((c.getCodigo() != null && String.valueOf(c.getCodigo()).indexOf(cod.toLowerCase()) == 0) || "".equals(cod))
				retorno.add(c);
		}
		return retorno;
	}
}
