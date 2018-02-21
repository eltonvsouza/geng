package br.com.util;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import br.com.dao.UnidadeDAOImpl;
import br.com.model.Unidade;

@Component
@Scope("session")
public class SistemaValidator {
	private UnidadeDAOImpl<Unidade> unidadeDAOImpl;

	public UnidadeDAOImpl<Unidade> getUnidadeDAOImpl() {
		return unidadeDAOImpl;
	}

	public void setUnidadeDAOImpl(UnidadeDAOImpl<Unidade> unidadeDAOImpl) {
		this.unidadeDAOImpl = unidadeDAOImpl;
	}
	
	public boolean validar(User user){
		return true;
	}
}
