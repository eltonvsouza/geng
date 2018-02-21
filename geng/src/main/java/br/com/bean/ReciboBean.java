package br.com.bean;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.dao.ConfiguracaoDAOImpl;
import br.com.dao.DiarioDAOImpl;
import br.com.dao.FilhoDAOImpl;
import br.com.dao.ReciboDAOImpl;
import br.com.dao.TrabalhadorDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Configuracao;
import br.com.model.Diario;
import br.com.model.Filho;
import br.com.model.Recibo;
import br.com.model.Trabalhador;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class ReciboBean {
	private Recibo recibo;
	@Autowired
	private ReciboDAOImpl<Recibo> reciboDAOImpl;
	private DiarioDAOImpl<Diario> diarioDAOImpl;
	private TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl;
	private FilhoDAOImpl<Filho> filhoDAOImpl;
	private ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl;
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Recibo> recibos;
	private List<SelectItem> listaRecibo;
	private Integer idBusca;
	private String mensagem;
	private ProgressBarBean progressBarBean;
	private javax.faces.component.html.HtmlCommandButton componentButton; 
	
	public Recibo getRecibo() {
		return this.recibo;
	}

	public void setRecibo(Recibo recibo) {
		this.recibo = recibo;
	}

	public ReciboDAOImpl<Recibo> getReciboDAOImpl() {
		return reciboDAOImpl;
	}

	public void setReciboDAOImpl(ReciboDAOImpl<Recibo> reciboDAOImpl) {
		this.reciboDAOImpl = reciboDAOImpl;
	}
	
	public Collection<Recibo> getRecibos() throws Exception{
		recibos = reciboDAOImpl.listar(getUnidade());
		return recibos;
	}
		
	public DiarioDAOImpl<Diario> getDiarioDAOImpl() {
		return diarioDAOImpl;
	}

	public void setDiarioDAOImpl(DiarioDAOImpl<Diario> diarioDAOImpl) {
		this.diarioDAOImpl = diarioDAOImpl;
	}

	public TrabalhadorDAOImpl<Trabalhador> getTrabalhadorDAOImpl() {
		return trabalhadorDAOImpl;
	}

	public void setTrabalhadorDAOImpl(
			TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl) {
		this.trabalhadorDAOImpl = trabalhadorDAOImpl;
	}

	public FilhoDAOImpl<Filho> getFilhoDAOImpl() {
		return filhoDAOImpl;
	}

	public void setFilhoDAOImpl(FilhoDAOImpl<Filho> filhoDAOImpl) {
		this.filhoDAOImpl = filhoDAOImpl;
	}

	public ConfiguracaoDAOImpl<Configuracao> getConfiguracaoDAOImpl() {
		return configuracaoDAOImpl;
	}

	public void setConfiguracaoDAOImpl(
			ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl) {
		this.configuracaoDAOImpl = configuracaoDAOImpl;
	}

	public UsuarioDAOImpl<Usuario> getUsuarioDAOImpl() {
		return usuarioDAOImpl;
	}

	public void setUsuarioDAOImpl(UsuarioDAOImpl<Usuario> usuarioDAOImpl) {
		this.usuarioDAOImpl = usuarioDAOImpl;
	}

	public LoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

	public void setRecibos(Collection<Recibo> recibos) {
		this.recibos = recibos;
	}
	
	public List<SelectItem> getListaRecibo() throws Exception {
		listaRecibo = new ArrayList<SelectItem>();
		ReciboDAOImpl<Recibo> reciboDAOImpl = new ReciboDAOImpl<Recibo>();
		Collection<Recibo> recibo = reciboDAOImpl.listar(getUnidade());
		for (Recibo c : recibo) {
			listaRecibo.add(new SelectItem(c.getCodigo(), c.getDatainicio() + " até " + c.getDatafim()));
		}
		return listaRecibo;
	}
	
	public int getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(int idBusca) {
		this.idBusca = idBusca;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public ProgressBarBean getProgressBarBean() {
		return progressBarBean;
	}
	
	public void setProgressBarBean(ProgressBarBean progressBarBean) {
		this.progressBarBean = progressBarBean;
	}

	public javax.faces.component.html.HtmlCommandButton getComponentButton() {
		return componentButton;
	}

	public void setComponentButton(
			javax.faces.component.html.HtmlCommandButton componentButton) {
		this.componentButton = componentButton;
	}
	
	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}

	public String carregarCadastrar(){
		limpar();
		return "/tela/recibo/cadastrarRecibo";
	}

	public String cadastrar() {
		if (recibo.getDatainicio().getDay() == 1) {
			gerar(recibo);
			limpar();
		}else{
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE UMA SEGUNDA-FEIRA", "SELECIONE UMA SEGUNDA-FEIRA!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
		return "/tela/recibo/cadastrarRecibo";
	}

	public String excluir(){
		recibo = reciboDAOImpl.carregar(idBusca);
		reciboDAOImpl.excluir(recibo);	
		return "/tela/recibo/listarRecibo";
	}
	
	private void gerar(Recibo recibo) {
		try {
			Collection<Trabalhador> trabalhador = trabalhadorDAOImpl.listar(getUnidade());
			boolean disableRecibo = false;
			progressBarBean.setTotal(trabalhador.size());
			Integer count = 0;
			for (Trabalhador t : trabalhador){
				progressBarBean.setCount(++count);
				Recibo rec = reciboDAOImpl.carregarDataTrabalhador(recibo.getDatainicio(), t.getMatricula());
				if(rec == null){
					Collection<Diario> diario = diarioDAOImpl.diarioRecibo(recibo.getDatainicio(), recibo.getDatafim(), t.getMatricula());
					double salario = 0, vhoraextra = 0, vinsalubridade = 0;
					int insalubridade = 0;
					double horaextra = 0;
					int filhos = filhoDAOImpl.filhosTrabalhador(t.getMatricula()).size();
					int repouso = 0;
					double rremunerado = 0;
					for (Diario d : diario) {
						if (d.getProducaoexecutada() == 0){
							repouso = 1;
						}
						salario += d.getSalario();
						vhoraextra += d.getVhextra();
						double hora = d.getHoraextra().getHours();
						double minuto = d.getHoraextra().getMinutes();
						horaextra += hora + minuto/60;
						if (d.getVinsalubridade() > 0) insalubridade++; 
						vinsalubridade += d.getVinsalubridade();
					}
//					recibo.setTrabalhador(t.getMatricula());
					recibo.setTrabalhador(t);
					recibo.setHoraextra(horaextra);
					recibo.setVhoraextra(vhoraextra);
					recibo.setInsalubridade(insalubridade);
					recibo.setVinsalubridade(vinsalubridade);
					recibo.setSfamilia(filhos);
					recibo.setVsfamilia(calculaSFamilia(filhos, t.getSalario().doubleValue()));
					if(repouso == 0){
						rremunerado = (salario + vhoraextra + vinsalubridade)/6;
					}
					recibo.setRremunerado(repouso);
					recibo.setVrremunerado(rremunerado);
					recibo.setSsalario(salario);
	//				TESTE SYSOUT
					System.out.println("trab: " + recibo.getTrabalhador() + "\nrr: " + recibo.getRremunerado() + "\nvrr: " + recibo.getVrremunerado() + "\nsalfami: " + recibo.getSfamilia()
							+ "\nvsalfam: " + recibo.getVsfamilia() + "\nhextra: " + recibo.getHoraextra() + "\nvhextra: " + recibo.getVhoraextra()
							+ "\ninsa: " + recibo.getInsalubridade() + "\nvinsa: " + recibo.getVinsalubridade() + "\nsal: " + recibo.getSsalario());
					reciboDAOImpl.salvar(recibo);
					limpar();
				}else{
					disableRecibo = true;
				}
			}
			progressBarBean.setEnabled(false);
			if(disableRecibo){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "RECIBO JÁ GERADO ANTERIORMENTE", "RECIBO JÁ GERADO ANTERIORMENTE!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}else{
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "RECIBO GERADO COM SUCESSO", "RECIBO GERADO COM SUCESSO!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public double calculaSFamilia(int filhos, double salario){
		Double sfamilialimitemenor = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familialimite57391").getValor());
		Double sfamilialimitemaior = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familialimite86260").getValor());
		Double sfamiliamenor = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familia57391").getValor());
		Double sfamiliamaior = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familia86260").getValor());
		if(salario <= sfamilialimitemenor)
			return sfamiliamenor / 2.0 * filhos;
		else{
			if (salario <= sfamilialimitemaior){
				return sfamiliamaior / 2.0 * filhos; 
			}else{
				return 0;
			}
		}
	}
	
	public void setDataFim(){
		if (recibo.getDatainicio().getDay() == 1) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(recibo.getDatainicio());
			calendar.add(Calendar.DAY_OF_MONTH, 6);
			recibo.setDatafim(calendar.getTime());
		}else{
			recibo.setDatafim(null);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE UMA SEGUNDA-FEIRA", "SELECIONE UMA SEGUNDA-FEIRA!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
	}
	
	public void limpar(){
		recibo = null;
		recibo = new Recibo();
	}
	
	public void relatorioGeral() throws Exception {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioRecibo.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
