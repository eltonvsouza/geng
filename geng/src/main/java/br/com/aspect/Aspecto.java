package br.com.aspect;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.test.context.transaction.AfterTransaction;

import br.com.util.ScheduledJob;

@Aspect
@Component
@Scope("session")
public class Aspecto {
	
//	@After("execution(* *.salvar(..)) throws java.io.IOException")
	@After("execution(* *.salvar(..))")
	public void afterInserir(){
		System.out.println("\n ##### CADASTRO EFETUADO COM SUCESSO ##### ");

		// Cria uma nova mensagem de informação para o JSF
//		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO,	"CADASTRO EFETUADO COM SUCESSO", "CADASTRO EFETUADO COM SUCESSO!");
		// Adiciona a mensagem ao formulário de cadastro
//		FacesContext.getCurrentInstance().addMessage("cadastro", message);
	}

	@Before("execution(* *.listar(..))")
	public void beforeListar(){
		System.out.println("\n ##### LISTAGEM ##### ");
	}

	
	@After("execution(* *.salvarOuAtualizar(..))")
	public void afterAlterar(){
		System.out.println("\n ##### REGISTRO ALTERADO COM SUCESSO ##### ");
	}

//	@AfterTransaction()
	@After("execution(* *.excluir(..))")
	public void afterExcluir(){
		System.out.println("\n ##### REGISTRO EXCLUÍDO COM SUCESSO ##### ");
	}

//	@Before("execution(* *.carregar*(..))")
//	public void beforeCarregar(){
//		System.out.println("Verifica Session ...");
//	}

	@Before(value="execution(* *.login(..))")
	public void beforeLogin(){
		System.out.println("Verificando BD ...");
	}

//	@AfterThrowing(pointcut="execution(* *.salvar(..))", throwing="he")
//	public void afterErro(org.hibernate.exception.ConstraintViolationException he){
//		System.out.println("\n ##### ERRO AO EXECUTAR AÇÃO ##### " + he);
//	}
	
	@AfterThrowing(pointcut = "execution(* *.salvar(..))", throwing = "e")  
    public void checkError(Throwable e) {  
		System.out.println("\n ##### ERRO AO EFETUAR O CADASTRO ##### ");

		// Cria uma nova mensagem de informação para o JSF
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO EFETUAR O CADASTRO", "ERRO AO EFETUAR O CADASTRO!");
		// Adiciona a mensagem ao formulário de cadastro
		FacesContext.getCurrentInstance().addMessage("cadastro", message);
    }
//	org.hibernate.util.JDBCExceptionReporter
//	org.hibernate.event.def.AbstractFlushingEventListener
//	org.hibernate.exception.ConstraintViolationException
//	java.sql.BatchUpdateException
	
	@Before("execution(* *.enviaEmail(..))")
	public void beforeMail(){
		System.out.println("\n ##### ENVIANDO E-MAIL ##### ");
	}
}