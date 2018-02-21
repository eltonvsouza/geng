package br.com.dao;

import org.springframework.stereotype.Service;

import br.com.model.Empresa;

@Service
public class EmpresaDAOImpl<T> extends GenericDAOImpl<T> implements EmpresaDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Empresa.class;
	}
}
