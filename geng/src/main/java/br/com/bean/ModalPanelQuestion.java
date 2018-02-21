package br.com.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("session")
public class ModalPanelQuestion {

	public String mensagem;
	public String tipo;
	public String entidade;
	private UsuarioBean usuarioBean;
	private ConfiguracaoBean configuracaoBean;
	private FuncaoBean funcaoBean;
	private OcorrenciaBean ocorrenciaBean;
	private ServicoBean servicoBean;
	private InsalubridadeBean insalubridadeBean;
	private ResponsavelBean responsavelBean;
	private EmpresaBean empresaBean;
	private TrabalhadorBean trabalhadorBean;
	private FilhoBean filhoBean;
	private DiarioBean diarioBean;

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}


	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getEntidade() {
		return entidade;
	}
	
	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}
	
	public UsuarioBean getUsuarioBean() {
		return usuarioBean;
	}
	
	public void setUsuarioBean(UsuarioBean usuarioBean) {
		this.usuarioBean = usuarioBean;
	}
	
	public ConfiguracaoBean getConfiguracaoBean() {
		return configuracaoBean;
	}
	
	public void setConfiguracaoBean(ConfiguracaoBean configuracaoBean) {
		this.configuracaoBean = configuracaoBean;
	}
	
	public FuncaoBean getFuncaoBean() {
		return funcaoBean;
	}
	
	public void setFuncaoBean(FuncaoBean funcaoBean) {
		this.funcaoBean = funcaoBean;
	}
	
	public OcorrenciaBean getOcorrenciaBean() {
		return ocorrenciaBean;
	}

	public void setOcorrenciaBean(OcorrenciaBean ocorrenciaBean) {
		this.ocorrenciaBean = ocorrenciaBean;
	}

	public ServicoBean getServicoBean() {
		return servicoBean;
	}

	public void setServicoBean(ServicoBean servicoBean) {
		this.servicoBean = servicoBean;
	}

	public InsalubridadeBean getInsalubridadeBean() {
		return insalubridadeBean;
	}

	public void setInsalubridadeBean(InsalubridadeBean insalubridadeBean) {
		this.insalubridadeBean = insalubridadeBean;
	}


	public ResponsavelBean getResponsavelBean() {
		return responsavelBean;
	}

	public void setResponsavelBean(ResponsavelBean responsavelBean) {
		this.responsavelBean = responsavelBean;
	}

	public EmpresaBean getEmpresaBean() {
		return empresaBean;
	}

	public void setEmpresaBean(EmpresaBean empresaBean) {
		this.empresaBean = empresaBean;
	}

	public TrabalhadorBean getTrabalhadorBean() {
		return trabalhadorBean;
	}

	public void setTrabalhadorBean(TrabalhadorBean trabalhadorBean) {
		this.trabalhadorBean = trabalhadorBean;
	}
	
	public FilhoBean getFilhoBean() {
		return filhoBean;
	}

	public void setFilhoBean(FilhoBean filhoBean) {
		this.filhoBean = filhoBean;
	}

	
	public DiarioBean getDiarioBean() {
		return diarioBean;
	}

	public void setDiarioBean(DiarioBean diarioBean) {
		this.diarioBean = diarioBean;
	}
	
	public void setaAtributos(){
		if(tipo.equals("excluir")){
			setMensagem("Deseja excluir o registro?");
		}else{
			if(tipo.equals("Logout")){
				setMensagem("Deseja sair do sistema?");
			}
		}
	}


	public void acao(){
		if(tipo.equals("excluir")){
			excluir();
		}else{
			if(tipo.equals("logout")){
				System.out.println("\nLogout\n");
			}
		}
		limpaAtributos();
	}
	
	private void limpaAtributos() {
		mensagem = null;
		tipo = null;
	}

	private void excluir() {
		if(entidade.equals("usuario")){
			usuarioBean.excluir();
		}else{
			if(entidade.equals("configuracao")){
				configuracaoBean.excluir();
			}else{
				if(entidade.equals("funcao")){
					funcaoBean.excluir();
				}else{
					if(entidade.equals("ocorrencia")){
						ocorrenciaBean.excluir();
					}else{
						if(entidade.equals("servico")){
							servicoBean.excluir();
						}else{
							if(entidade.equals("insalubridade")){
								insalubridadeBean.excluir();
							}else{
								if(entidade.equals("responsavel")){
									responsavelBean.excluir();
								}else{
									if(entidade.equals("empresa")){
										empresaBean.excluir();
									}else{
										if(entidade.equals("trabalhador")){
											trabalhadorBean.excluir();
										}else{
											if(entidade.equals("filho")){
												filhoBean.excluir();
											}else{
												if(entidade.equals("diario")){
													diarioBean.excluir();
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
}
