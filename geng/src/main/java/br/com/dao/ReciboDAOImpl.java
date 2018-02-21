package br.com.dao;

import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Recibo;

@Service
public class ReciboDAOImpl<T> extends GenericDAOImpl<T> implements ReciboDAO {
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Recibo.class;
	}
	
	public T carregarDataTrabalhador(Date data, int trabalhador) {
		Session session = HibernateUtility.getSession();
		Object object = null;
		try {
			Query select = session.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.datainicio = :data AND" +
								 			   "													c.trabalhador = :trabalhador");
			select.setParameter("data", data);
			select.setParameter("trabalhador", trabalhador);
			object = select.uniqueResult();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			session.close();
			session.getSessionFactory().close();
			System.gc();
		}
		return (T) object;
	}
}
