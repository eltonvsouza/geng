package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Cnaefiscal;

@Service
public class CnaeFiscalDAOImpl<T> extends GenericDAOImpl<T> implements CnaeFiscalDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Cnaefiscal.class;
	}
}
