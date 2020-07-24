package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.status;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.stefanini.dao.SolicitacaoDao;
import br.stefanini.model.Solicitacao;

@Controller
@Path(value = "/solicitacao")
public class SolicitacaoController {
	
	private Result result;
	private SolicitacaoDao solicitacaoDao;	
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public SolicitacaoController(){}
	
	@Inject
	public SolicitacaoController( Result result, SolicitacaoDao solicitacaoDao){
		this.result = result;
		this.solicitacaoDao = solicitacaoDao;	
	}
		
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Solicitacao solicitacao  ) throws ParseException {	
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateSaida = (Date)formatter.parse(solicitacao.getData1());
		Date dateRetorno = (Date)formatter.parse(solicitacao.getData2());	
		
		solicitacao.setDtsaida(dateSaida);
		solicitacao.setDtretorno(dateRetorno);
		solicitacao.setDataRegistro(new Date());		
		solicitacao.setAtivo(true);		
		solicitacaoDao.novo(solicitacao);	
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(solicitacaoDao.todosAtivos())	
			  .include("solicitante")
			  .include("responsavel")
			  .include("veiculo")
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Solicitacao solicitacao, Long id) throws ParseException {	
						
		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateSaida = (Date)formatter.parse(solicitacao.getData1());
		Date dateRetorno = (Date)formatter.parse(solicitacao.getData2());				
		solicitacao.setDtsaida(dateSaida);
		solicitacao.setDtretorno(dateRetorno);
		solicitacao.setIdSolicitacao(id);
		solicitacaoDao.atualizar(solicitacao);
		result.use(status()).ok();
	}	
	
}
