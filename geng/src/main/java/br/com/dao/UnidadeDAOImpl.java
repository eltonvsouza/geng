package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Unidade;

@Service
public class UnidadeDAOImpl<T> extends GenericDAOImpl<T> implements UnidadeDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Unidade.class;
	}
}
