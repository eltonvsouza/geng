package br.com.dao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Agenda;

@Service
public class AgendaDAOImpl<T> extends GenericDAOImpl<T> implements AgendaDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Agenda.class;
	}
	
	@Transactional
	public Collection<T> listarData(Date data) throws Exception {
		Collection<T> lista = null;
		try {
			Query query = getSessionFactory().getCurrentSession().createQuery("from " + getClasseConsulta().getName() + " c WHERE DATE_FORMAT(c.datahora, '%Y-%m-%d') = DATE_FORMAT(:data, '%Y-%m-%d')");
			query.setParameter("data", data);
			lista = query.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			System.gc();
		}
		return lista;
	}
}
