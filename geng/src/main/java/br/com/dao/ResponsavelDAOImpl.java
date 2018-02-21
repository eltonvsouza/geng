package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Responsavel;

@Service
public class ResponsavelDAOImpl<T> extends GenericDAOImpl<T> implements ResponsavelDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Responsavel.class;
	}
}
