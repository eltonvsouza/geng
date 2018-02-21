package br.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Insalubridade;

@Service
public class InsalubridadeDAOImpl<T> extends GenericDAOImpl<T> implements InsalubridadeDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Insalubridade.class;
	}
}
