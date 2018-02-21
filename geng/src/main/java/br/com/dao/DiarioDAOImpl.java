package br.com.dao;

import java.util.Collection;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Diario;

@Service
public class DiarioDAOImpl<T> extends GenericDAOImpl<T> implements ResponsavelDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Diario.class;
	}
	
	public Collection<T> diarioRecibo(Date inicio, Date fim, int trabalhador) throws Exception {
		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		try {
			Query select = session.createQuery("from " + getClasseConsulta().getName() + " WHERE trabalhador = :trabalhador AND " +
																						 "		 data BETWEEN :inicio AND :fim ");
			select.setParameter("trabalhador", trabalhador);
			select.setParameter("inicio", inicio);
			select.setParameter("fim", fim);
			lista = select.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return lista;
	}
}
