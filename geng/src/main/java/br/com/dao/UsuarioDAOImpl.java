package br.com.dao;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.utility.HibernateUtility;
import br.com.model.Usuario;

@Service
public class UsuarioDAOImpl<T> extends GenericDAOImpl<T> implements UsuarioDAO {
	
	@SuppressWarnings("unchecked")
	@Override
	protected Class getClasseConsulta() {
		return Usuario.class;
	}
	
	@Transactional
	public T carregarUsuario(String login) {
		Object object = null;
		try {
			Query select = getSessionFactory().getCurrentSession()
					.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.usuario = :login");
			select.setParameter("login", login);
			object = select.uniqueResult();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			System.gc();
		}
		return (T) object;
	}
}
