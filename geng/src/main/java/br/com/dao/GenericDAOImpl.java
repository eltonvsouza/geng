package br.com.dao;

import java.sql.BatchUpdateException;
import java.sql.SQLException;
import java.util.Collection;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.BatchUpdateUtils;
import org.springframework.orm.hibernate3.HibernateJdbcException;
import org.springframework.orm.hibernate3.HibernateQueryException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.model.Unidade;

@Repository
public abstract class GenericDAOImpl<T> implements GenericDAO<T> {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Transactional
	public T carregar(Integer id) {
		Object object = null;
		try {
			Query select = sessionFactory.getCurrentSession()
					.createQuery("SELECT c FROM " + getClasseConsulta().getName() + " c WHERE c.id = :id");
			select.setParameter("id", id);
			object = select.uniqueResult();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			System.gc();
		}
		return (T) object;
	}
	
	@Transactional
	public Collection<T> listar(Unidade unidade) throws Exception {

		Collection<T> lista = null;
		
		try {
			Query consulta = sessionFactory.getCurrentSession().createQuery("from " + getClasseConsulta().getName() + " c WHERE c.unidade = :unidade");
			consulta.setParameter("unidade", unidade);
			lista = consulta.list();
		} catch (HibernateException he) {
			he.printStackTrace();
		} finally {
			System.gc();
		}
		return lista;
	}

	/**
	 * Retorna a classe que será utilizada para consulta ou listagem.
	 * @return a classe que será utilizada para consulta ou listagem.
	 */
	protected abstract Class<T> getClasseConsulta();
	
	@Transactional
	public void salvar(T object){
		FacesMessage message = null;
		try {
			sessionFactory.getCurrentSession().save(object);
			message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"CADASTRO EFETUADO COM SUCESSO", "CADASTRO EFETUADO COM SUCESSO!");
		}catch (ConstraintViolationException e) {
			e.printStackTrace();
			sessionFactory.getCurrentSession().clear();
			if (e.getErrorCode() == 1062) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "JÁ EXISTE UM REGISTRO COM ESTE CÓDIGO", null);
			}else{
				if (e.getErrorCode() == 1452) {
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "DEPENDÊNCIA DE DADOS", "DEPENDÊNCIA DE DADOS!");
				}else{
					message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
				}
			}
		}catch (HibernateException e) {
			e.printStackTrace();
			sessionFactory.getCurrentSession().clear();
			message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
		}finally {
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			System.gc();
		}
	}

		 
		@Transactional
		public void salvarOuAtualizar(T object) {
		try {
		sessionFactory.getCurrentSession().saveOrUpdate(object);
		sessionFactory.getCurrentSession().flush();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "ALTERAÇÃO EFETUADA COM SUCESSO", "ALTERAÇÃO EFETUADA COM SUCESSO!");
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
		} catch (NonUniqueObjectException e) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO NÃO PODE SER ALTERADO", "REGISTRO NÃO PODE SER ALTERADO!");
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}catch (ConstraintViolationException e) {
		e.printStackTrace();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "DUPLICIDADE DE DADOS", "DUPLICIDADE DE DADOS!");
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
		}
		 
		@Transactional
		public void excluir(T object) {
		try {
		sessionFactory.getCurrentSession().delete(object);
		sessionFactory.getCurrentSession().flush();
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "REGISTRO EXCLUÍDO COM SUCESSO", "REGISTRO EXCLUÍDO COM SUCESSO!");
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}catch (ConstraintViolationException e) {
		e.printStackTrace();
		FacesMessage message;
		if (e.getErrorCode() == 1451) {
		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO EM USO, NÃO PODE SER EXCLUÍDO", "REGISTRO EM USO, NÃO PODE SER EXCLUÍDO!");
		}else{
		message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "REGISTRO NÃO PODE SER EXCLUÍDO", "REGISTRO NÃO PODE SER EXCLUÍDO!");
		}
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
		} finally {
		System.gc();
		}
		}
}
