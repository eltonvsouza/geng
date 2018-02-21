package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Servico;

@Service
public class ServicoDAOImpl<T> extends GenericDAOImpl<T> implements ServicoDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Servico.class;
	}
}
