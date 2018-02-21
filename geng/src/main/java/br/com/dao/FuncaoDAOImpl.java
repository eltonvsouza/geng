package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Funcao;

@Service
public class FuncaoDAOImpl<T> extends GenericDAOImpl<T> implements FuncaoDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Funcao.class;
	}
}
