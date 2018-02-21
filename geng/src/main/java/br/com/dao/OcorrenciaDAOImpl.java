package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Ocorrencia;

@Service
public class OcorrenciaDAOImpl<T> extends GenericDAOImpl<T> implements OcorrenciaDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Ocorrencia.class;
	}
}
