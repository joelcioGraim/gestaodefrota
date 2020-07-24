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
import br.stefanini.dao.OficinaDao;
import br.stefanini.model.Oficina;

@Controller
@Path(value = "/oficina")
public class OficinaController {
	
	private Result result;
	private OficinaDao oficinaDao;	
	
	/**
	 * @deprecated
	 */
	public OficinaController() {
		// TODO Auto-generated constructor stub
	}

	@Inject
	public OficinaController(Result result, OficinaDao oficinaDao) {
		this.result = result;
		this.oficinaDao = oficinaDao;
	}
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Oficina oficina  ) throws ParseException {			
		oficina.setDataRegistro(new Date());		
		oficina.setAtivo(true);		
		oficinaDao.novo(oficina);	
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(oficinaDao.todosAtivos())			  
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar( Oficina oficina, Long id) throws ParseException {
		oficina.setIdOficina(id);
		oficinaDao.atualizar(oficina);
		result.use(status()).ok();
	}	

}
