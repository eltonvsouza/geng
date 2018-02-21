package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Trabalhador;

@Service
public class TrabalhadorDAOImpl<T> extends GenericDAOImpl<T> implements TrabalhadorDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Trabalhador.class;
	}
}
