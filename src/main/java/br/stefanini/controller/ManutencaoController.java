package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.status;

import java.text.ParseException;
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
import br.stefanini.dao.ManutencaoDao;
import br.stefanini.model.Item;
import br.stefanini.model.Manutencao;

@Controller
@Path(value = "/manutencao")
public class ManutencaoController {
	
	private Result result;
	private ManutencaoDao manutencaoDao;	
	
	/**
	 * @deprecated
	 */
	public ManutencaoController() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public ManutencaoController( Result result, ManutencaoDao manutencaoDao ) {
		this.result = result;
		this.manutencaoDao = manutencaoDao;
	}
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Manutencao manutencao  ) throws ParseException {	
		/*DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateSaida = (Date)formatter.parse(solicitacao.getData1());
		Date dateRetorno = (Date)formatter.parse(solicitacao.getData2());	
		
		solicitacao.setDtsaida(dateSaida);
		solicitacao.setDtretorno(dateRetorno);*/
		manutencao.setDataRegistro(new Date());		
		manutencao.setAtivo(true);	
		setItens(manutencao);
		manutencaoDao.novo(manutencao);	
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(manutencaoDao.todosAtivos())	
			  .include("usuario")
			  .include("veiculo")
			  .include("oficina")
			  .include("itens")
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Manutencao manutencao, Long id) throws ParseException {	
						
		/*DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateSaida = (Date)formatter.parse(solicitacao.getData1());
		Date dateRetorno = (Date)formatter.parse(solicitacao.getData2());				
		solicitacao.setDtsaida(dateSaida);
		solicitacao.setDtretorno(dateRetorno);*/
		manutencao.setIdManutencao(id);
		setItens(manutencao);
		manutencaoDao.atualizar(manutencao);
		result.use(status()).ok();
	}	
	
	protected void setItens(Manutencao manutencao) {
		if(manutencao.getItens() != null) {
			for (Item item : manutencao.getItens()) {
				item.setManutencao(manutencao);
			}
		}
	}
}
