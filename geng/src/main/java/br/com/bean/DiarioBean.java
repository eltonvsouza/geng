package br.com.bean;

import java.math.BigDecimal;
import java.net.URL;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dao.ConfiguracaoDAOImpl;
import br.com.dao.DiarioDAOImpl;
import br.com.dao.FuncaoDAOImpl;
import br.com.dao.InsalubridadeDAOImpl;
import br.com.dao.TrabalhadorDAOImpl;
import br.com.dao.UsuarioDAOImpl;
import br.com.model.Configuracao;
import br.com.model.Diario;
import br.com.model.Funcao;
import br.com.model.Insalubridade;
import br.com.model.Servico;
import br.com.model.Trabalhador;
import br.com.model.Unidade;
import br.com.model.Usuario;
import br.com.relatorio.GenericRelatorio;

@Component
@Scope("session")
public class DiarioBean {

	private Diario diario;
	@Autowired
	private DiarioDAOImpl<Diario> diarioDAOImpl;
	private ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl;
	private TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl;
	private FuncaoDAOImpl<Funcao> funcaoDAOImpl;
	private InsalubridadeDAOImpl<Insalubridade> insalubridadeDAOImpl;
	@Autowired
	private UsuarioDAOImpl<Usuario> usuarioDAOImpl;
	private LoginBean loginBean;
	private Collection<Diario> diarios;
	private Integer idBusca;
	private String mensagem;
	private Date dataInicio;
	private Date dataFim;
	private String servico;
	private String trabalhador;
	private String lote;
	private Double adiantamento = 0.0;
	private Date today = new Date();

	public Diario getDiario() {
		return diario;
	}

	public void setDiario(Diario diario) {
		this.diario = diario;
	}
	
	public DiarioDAOImpl<Diario> getDiarioDAOImpl() {
		return diarioDAOImpl;
	}

	public void setDiarioDAOImpl(DiarioDAOImpl<Diario> diarioDAOImpl) {
		this.diarioDAOImpl = diarioDAOImpl;
	}
	
	public ConfiguracaoDAOImpl<Configuracao> getConfiguracaoDAOImpl() {
		return configuracaoDAOImpl;
	}

	public void setConfiguracaoDAOImpl(
			ConfiguracaoDAOImpl<Configuracao> configuracaoDAOImpl) {
		this.configuracaoDAOImpl = configuracaoDAOImpl;
	}

	public TrabalhadorDAOImpl<Trabalhador> getTrabalhadorDAOImpl() {
		return trabalhadorDAOImpl;
	}

	public void setTrabalhadorDAOImpl(
			TrabalhadorDAOImpl<Trabalhador> trabalhadorDAOImpl) {
		this.trabalhadorDAOImpl = trabalhadorDAOImpl;
	}

	public FuncaoDAOImpl<Funcao> getFuncaoDAOImpl() {
		return funcaoDAOImpl;
	}

	public void setFuncaoDAOImpl(FuncaoDAOImpl<Funcao> funcaoDAOImpl) {
		this.funcaoDAOImpl = funcaoDAOImpl;
	}

	public InsalubridadeDAOImpl<Insalubridade> getInsalubridadeDAOImpl() {
		return insalubridadeDAOImpl;
	}

	public void setInsalubridadeDAOImpl(
			InsalubridadeDAOImpl<Insalubridade> insalubridadeDAOImpl) {
		this.insalubridadeDAOImpl = insalubridadeDAOImpl;
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

	public Collection<Diario> getDiarios() throws Exception  {
		diarios = diarioDAOImpl.listar(getUnidade());
		return diarios;
	}
	
	public void setDiarios(Collection<Diario> diarios) {
		this.diarios = diarios;
	}

	public Integer getIdBusca() {
		return idBusca;
	}
	
	public void setIdBusca(Integer idBusca) {
		this.idBusca = idBusca;
	}
	
	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}

	public String getTrabalhador() {
		return trabalhador;
	}

	public void setTrabalhador(String trabalhador) {
		this.trabalhador = trabalhador;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}
	
	public Double getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Double adiantamento) {
		this.adiantamento = adiantamento;
	}

	public Unidade getUnidade() {
		return usuarioDAOImpl.carregarUsuario(loginBean.getUsuario().getUsuario()).getUnidade();
	}
	
	public String carregarCadastrar() throws ParseException{
		limpar();
		return "/tela/diario/cadastrarDiario";
	}
	
	public String carregarAlterar() {
		diario = diarioDAOImpl.carregar(idBusca);
		return "/tela/diario/alterarDiario";
	}

	public String carregarRelProducao() {
		limpar();
		return "/tela/diario/relatorio/relProducao";
	}
	
	public String carregarRelProducaoServico() {
		limpar();
		return "/tela/diario/relatorio/relProducaoServico";
	}
	
	public String carregarRelProducaoTrabalhador() {
		limpar();
		return "/tela/diario/relatorio/relatorio/relProducaoTrabalhador";
	}

	public String carregarRelFolhaPagamento() {
		limpar();
		return "/tela/diario/relatorio/relFolhaPagamento";
	}
	
	public String cadastrar() {
//		if(diario.getData().after(today)){
//			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERRO AO CADASTRAR DATAS FUTURAS", "ERRO AO CADASTRAR DATAS FUTURAS!");
//			FacesContext.getCurrentInstance().addMessage("cadastro", message);
//		}else{
		
		diario.setSalario(calculaSalario(diario));
		diario.setVhextra(calculaHoraExtra(diario));
		diario.setVinsalubridade(calculaInsalubridade(diario));
		diarioDAOImpl.salvar(diario);
		limpar();
		
//		}
		return "/tela/diario/cadastrarDiario";
	}

	public String alterar() {
		diario.setSalario(calculaSalario(diario));
		diarioDAOImpl.salvarOuAtualizar(diario);
		limpar();
		return "/tela/diario/listarDiario";
	}

	public void excluir(){
		diario = diarioDAOImpl.carregar(idBusca);
		diarioDAOImpl.excluir(diario);
	}
	
	private double calculaSalario(Diario diario) {
		Long prodExec = diario.getProducaoexecutada();
		if (prodExec != 0){
			Long prodAcord = diario.getProducaoacordada();
			double salario = salarioFuncaoTrabalhador(diario.getTrabalhador());
			if (diario.getData().getDay() == 6){
				return ((prodExec/prodAcord) * 0.5 + 0.5) * salario;
			}else{
				return (prodExec/prodAcord) * salario;
			}
		}else{
			return 0;
		}
	}
	
	private double calculaHoraExtra(Diario diario) {
		Date horaExtra = diario.getHoraextra();
		if (horaExtra != null){
			double salario = salarioFuncaoTrabalhador(diario.getTrabalhador());
			double minuto = Double.parseDouble(Integer.toString(horaExtra.getMinutes())) / 60;
			double hora = horaExtra.getHours();
			return ((salario) / 8 * 1.6)*(hora + minuto);
		}else{
			return 0;
		}
	}
	
	@Transactional
	private double calculaInsalubridade(Diario diario) {
//		Servico servico = diario.getServico();
//		Insalubridade insalubridade = insalubridadeDAOImpl.carregar(servico.getCodigo());
//		if (insalubridade != null){
//			double salario = salarioFuncaoTrabalhador(diario.getTrabalhador());
//			double porcentagem = insalubridade.getPorcentagem();
//			return porcentagem/100*salario;
//		}else{
//			return 0;
//		}
		
			Insalubridade insalubridade = insalubridadeDAOImpl.carregar(diario.getServico().getCodigo());
			if (insalubridade != null){
				double salario = salarioFuncaoTrabalhador(diario.getTrabalhador());
				double porcentagem = insalubridade.getPorcentagem();
				return porcentagem/100*salario;
			}else{
				return 0;
			}
	}
	
	@Transactional
	private double salarioFuncaoTrabalhador(Trabalhador trabalhador) {
		double salarioDia = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "dia").getValor());
//		Trabalhador t = trabalhadorDAOImpl.carregar(trabalhador.getMatricula());
//		return funcaoDAOImpl.carregar(t.getFuncao()).getSalario() * salarioDia;
		return trabalhador.getFuncao().getSalario().doubleValue() * salarioDia;
	}
	
	public void setDataFim(){
		if (getDataInicio().getDate() == 1) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dataInicio);
//			calendar.add(Calendar.DAY_OF_MONTH, 13);
			calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
			setDataFim(calendar.getTime());
		}else{
			setDataFim(null);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE O DIA PRIMEIRO", "SELECIONE O DIA PRIMEIRO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
	}
	
	public void setDataFimRecibo(){
		if (getDataInicio().getDay() == 1) {
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dataInicio);
			calendar.add(Calendar.DAY_OF_MONTH, 13);
//			calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH)-1);
			setDataFim(calendar.getTime());
		}else{
			setDataFim(null);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE UMA SEGUNDA-FEIRA", "SELECIONE O DIA PRIMEIRO!");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
		}
	}
	
	public boolean validaRelatorioProducaoServico(){
		if(servico.equals("")){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Serviço: Obrigatorio", "Campo Serviço: Obrigatorio");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			if(dataInicio == null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Início: Obrigatorio", "Campo Data Início: Obrigatorio");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
				return false;
			}else{
				if (dataFim == null) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Fim: Obrigatorio", "Campo Data Fim: Obrigatorio");
					FacesContext.getCurrentInstance().addMessage("cadastro", message);
					return false;
				}else{
					if(dataFim.before(dataInicio) || dataInicio.after(dataFim)){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "VERIFIQUE AS DATAS", "VERIFIQUE AS DATAS!");
						FacesContext.getCurrentInstance().addMessage("cadastro", message);
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}

	public boolean validaRelatorioProducaoTrabalhador(){
		if(servico == null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Serviço: Obrigatorio", "Campo Serviço: Obrigatorio");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			if(dataInicio == null){
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Início: Obrigatorio", "Campo Data Início: Obrigatorio");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
				return false;
			}else{
				if (dataFim == null) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Fim: Obrigatorio", "Campo Data Fim: Obrigatorio");
					FacesContext.getCurrentInstance().addMessage("cadastro", message);
					return false;
				}else{
					if(dataFim.before(dataInicio) || dataInicio.after(dataFim)){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "VERIFIQUE AS DATAS", "VERIFIQUE AS DATAS!");
						FacesContext.getCurrentInstance().addMessage("cadastro", message);
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	
	public boolean validaRelatorioRecibo(){
		if(dataInicio == null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Início: Obrigatorio", "Campo Data Início: Obrigatorio");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			if (dataInicio.getDay() != 1) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE UMA SEGUNDA-FEIRA", "SELECIONE UMA SEGUNDA-FEIRA!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
				return false;
			}else{
				if(dataFim.before(dataInicio) || dataInicio.after(dataFim)){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "VERIFIQUE AS DATAS, Data Fim menor que Data Início ou Data Início maior que Data Fim", "VERIFIQUE AS DATAS!");
					FacesContext.getCurrentInstance().addMessage("cadastro", message);
					return false;
				}else{
					if(dataFim.getDate() > dataInicio.getDate() + 14){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "USE UM INTERVALO DE 15 DIAS ENTRE AS DATAS", "USE UM INTERVALO DE 15 DIAS ENTRE AS DATAS!");
						FacesContext.getCurrentInstance().addMessage("cadastro", message);
						return false;
					}else{
						if (dataFim == null) {
							FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Fim: Obrigatorio", "Campo Data Fim: Obrigatorio");
							FacesContext.getCurrentInstance().addMessage("cadastro", message);
							return false;
						}else{
							return true;
						}
					}
				}
			}
		}
	}
	
	public boolean validaRelatorioProducao(){
		if(dataInicio == null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Início: Obrigatorio", "Campo Data Início: Obrigatorio");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			if (dataInicio.getDay() != 1) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "SELECIONE UMA SEGUNDA-FEIRA", "SELECIONE UMA SEGUNDA-FEIRA!");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
				return false;
			}else{
				if (dataFim == null) {
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Fim: Obrigatorio", "Campo Data Fim: Obrigatorio");
					FacesContext.getCurrentInstance().addMessage("cadastro", message);
					return false;
				}else{
					if(dataFim.before(dataInicio) || dataInicio.after(dataFim)){
						FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "VERIFIQUE AS DATAS", "VERIFIQUE AS DATAS!");
						FacesContext.getCurrentInstance().addMessage("cadastro", message);
						return false;
					}else{
						return true;
					}
				}
			}
		}
	}
	
	public boolean validaRelatorioFolhaPagamento(){
		if(dataInicio == null){
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Início: Obrigatorio", "Campo Data Início: Obrigatorio");
			FacesContext.getCurrentInstance().addMessage("cadastro", message);
			return false;
		}else{
			if (dataFim == null) {
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Campo Data Fim: Obrigatorio", "Campo Data Fim: Obrigatorio");
				FacesContext.getCurrentInstance().addMessage("cadastro", message);
				return false;
			}else{
				if(dataFim.before(dataInicio) || dataInicio.after(dataFim)){
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "VERIFIQUE AS DATAS, Data Fim menor que Data Início ou Data Início maior que Data Fim", "VERIFIQUE AS DATAS!");
					FacesContext.getCurrentInstance().addMessage("cadastro", message);
					return false;
				}else{
					return true;
				}
			}
		}
	}
	
	public void limpar(){
		Date data = new Date();
		if(diario != null && diario.getData() != null)
			data = diario.getData();
		else
			data = today;
		diario = null;
		diario = new Diario();
		diario.setData(data);
		
		dataInicio = null;
		dataFim = null;
		servico = null;
		trabalhador = null;
		lote = null;
	}
	
	public void relatorioGeral() {
		GenericRelatorio relatorio = new GenericRelatorio();
		Map parametro = new HashMap();
		try {
			relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioDiario.jasper", "salvarPdf", parametro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void relatorioProducaoServico() {
		if(validaRelatorioProducaoServico()){
			GenericRelatorio relatorio = new GenericRelatorio();
			Map parametro = new HashMap();
			parametro.put("datainicio", dataInicio);
			parametro.put("datafim", dataFim);
			parametro.put("servico", servico);
			parametro.put("lote", lote.equals("") ? "" : " AND d.lote = " + lote + " ");
			parametro.put("trabalhador", trabalhador.equals("") ? "" : " AND d.trabalhador = " + trabalhador + " ");
			try {
				relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioProducaoServico.jasper", "salvarPdf", parametro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
//	public void relatorioProducaoTrabalhador() {
//		if(validaRelatorioProducao()){
//			GenericRelatorio relatorio = new GenericRelatorio();
//			Map parametro = new HashMap();
//			parametro.put("datainicio", dataInicio);
//			parametro.put("datafim", dataFim);
//			parametro.put("servico", servico);
//			parametro.put("trabalhador", trabalhador.equals("") ? "" : " AND d.trabalhador = " + trabalhador + " ");
//			try {
//				relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioProducaoTrabalhador.jasper", parametro);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
//	}
	
	public void relatorioProducao() {
		if(validaRelatorioProducaoTrabalhador()){
			GenericRelatorio relatorio = new GenericRelatorio();
			Map parametro = new HashMap();
			parametro.put("datainicio", dataInicio);
			parametro.put("datafim", dataFim);
			parametro.put("lote", lote.equals("") ? "" : " AND d.lote = " + lote + " ");
			parametro.put("servico", servico.equals("") ? "" : " AND d.servico = " + servico + " ");
			parametro.put("trabalhador", trabalhador.equals("") ? "" : " AND d.trabalhador = " + trabalhador + " ");
			try {
				relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioProducao.jasper", "salvarPdf", parametro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void relatorioRecibo() {
		if(validaRelatorioRecibo()){
			GenericRelatorio relatorio = new GenericRelatorio();
			Map parametro = new HashMap();
			parametro.put("datainicio", dataInicio);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(dataInicio);
			calendar.add(Calendar.DAY_OF_MONTH, 6);
			parametro.put("datafim2", calendar.getTime());
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			parametro.put("datainicio2", calendar.getTime());
			parametro.put("datafim", dataFim);
			parametro.put("trabalhador", trabalhador.equals("") ? "" : " AND d.trabalhador = " + trabalhador + " ");
			parametro.put("adiantamento", adiantamento);
//			URL web = getClass().getResource("/relatorio/subFluxo.jasper");
//			parameters.put("SUBREPORT_DIR", path.toString());
			URL path = getClass().getResource("/br/com/relatorio/jasper/");
			parametro.put("path", path.toString().replace("/", "\\\\").substring(7));
//			parametro.put("path", path.toString().substring(6));
			System.out.println(parametro.get("path"));
			try {
				relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioRecibo.jasper", "salvarPdf", parametro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void relatorioFolhaPagamento() {
		if(validaRelatorioFolhaPagamento()){
			Double salario = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "dia").getValor());
			Double sfamilialimitemenor = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familialimite57391").getValor());
			Double sfamilialimitemaior = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familialimite86260").getValor());
			Double sfamiliamenor = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familia57391").getValor());
			Double sfamiliamaior = Double.valueOf(configuracaoDAOImpl.carregarGrupoTipo("salario", "familia86260").getValor());
			GenericRelatorio relatorio = new GenericRelatorio();
			Map parametro = new HashMap();
			parametro.put("datainicio", dataInicio);
			parametro.put("datafim", dataFim);
			parametro.put("salario", salario);
			parametro.put("sfamilialimitemenor", sfamilialimitemenor);
			parametro.put("sfamilialimitemaior", sfamilialimitemaior);
			parametro.put("sfamiliamenor", sfamiliamenor);
			parametro.put("sfamiliamaior", sfamiliamaior);
			try {
				relatorio.gerarRelatorio("/br/com/relatorio/jasper/relatorioFolhaPagamento.jasper", "salvarPdf", parametro);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
