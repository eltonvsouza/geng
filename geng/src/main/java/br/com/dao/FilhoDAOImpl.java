package br.com.dao;

import java.util.Collection;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Filho;

@Service
public class FilhoDAOImpl<T> extends GenericDAOImpl<T> implements FilhoDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Filho.class;
	}
	
	public Collection<T> filhosTrabalhador(int trabalhador) throws Exception {

		Collection<T> lista = null;
		Session session = HibernateUtility.getSession();
		
		try {
			Query select = session.createQuery("from " + getClasseConsulta().getName() + " WHERE trabalhador = :trabalhador");
			select.setParameter("trabalhador", trabalhador);
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
