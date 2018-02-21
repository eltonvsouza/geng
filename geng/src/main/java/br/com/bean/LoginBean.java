package br.com.bean;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.faces.context.FacesContext;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.UsuarioDAOImpl;
import br.com.model.Usuario;
import br.com.util.SpringSecurityController;

@Component
@Scope("singleton")
public class LoginBean {
	private Usuario usuario;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private SpringSecurityController springSecurityController;
	private Date today = new Date();
	
	public LoginBean() {
		usuario = new Usuario();
    }
	
	@Transactional
	public Usuario getUsuario() {
        usuario = usuarioDAOImpl.carregarUsuario(springSecurityController.login().getUsername());
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (today.before(usuario.getUnidade().getValidade()) || dateFormat.format(today).toString().equals(usuario.getUnidade().getValidade().toString())){
        	return usuario;
        }else{
        	carregarSistemaInvalido();
        	return null;
        }
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioDAOImpl<Usuario> getUsuarioDAOImpl() {
		return usuarioDAOImpl;
	}

	public void setUsuarioDAOImpl(UsuarioDAOImpl<Usuario> usuarioDAOImpl) {
		this.usuarioDAOImpl = usuarioDAOImpl;
	}

	public SpringSecurityController getSpringSecurityController() {
		return springSecurityController;
	}

	public void setSpringSecurityController(
			SpringSecurityController springSecurityController) {
		this.springSecurityController = springSecurityController;
	}
	
	private void carregarSistemaInvalido(){
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		try {
			response.sendRedirect("/geng/login.jsf?license=true");
		} catch (IOException e) {
			e.printStackTrace();
		}
		FacesContext.getCurrentInstance().responseComplete();
	}
}
