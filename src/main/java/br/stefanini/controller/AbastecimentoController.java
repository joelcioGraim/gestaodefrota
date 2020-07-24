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
import br.stefanini.dao.AbastecimentoDao;
import br.stefanini.model.Abastecimento;

@Controller
@Path(value = "/abastecimento")
public class AbastecimentoController {
	
	private Result result;
	private AbastecimentoDao abastecimentoDao;	
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public AbastecimentoController(){}
	
	@Inject
	public AbastecimentoController( Result result, AbastecimentoDao abastecimentoDao){
		this.result = result;
		this.abastecimentoDao = abastecimentoDao;	
	}
		
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Abastecimento abastecimento  ) throws ParseException {			
		abastecimento.setDataAbastecimento(new Date());				
		abastecimentoDao.novo(abastecimento);	
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(abastecimentoDao.todosAtivos())	
			  .include("postoCredenciado")
			  .include("usuario")
			  .include("veiculo")
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Abastecimento abastecimento, Long ids) throws ParseException {		
		abastecimento.setIdAbastecimento(ids);
		abastecimentoDao.atualizar(abastecimento);
		result.use(status()).ok();
	}	
	
}
