package br.com.dao;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Uf;

@Service
public class UfDAOImpl<T> extends GenericDAOImpl<T> implements UfDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Uf.class;
	}
	
	@Transactional
	public Collection<T> listar() throws Exception {
		Collection<T> lista = null;
		try {
			Query consulta = getSessionFactory().getCurrentSession().createQuery("from " + getClasseConsulta().getName() + " c");
			lista = consulta.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			System.gc();
		}
		return lista;
	}
}
