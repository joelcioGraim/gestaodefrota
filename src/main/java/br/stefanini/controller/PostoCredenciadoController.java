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
import br.stefanini.dao.PostoCredenciadoDao;
import br.stefanini.model.PostoCredenciado;

@Controller
@Path(value = "/posto")
public class PostoCredenciadoController {

	private Result result;
	private PostoCredenciadoDao postoCredenciadoDao;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public PostoCredenciadoController(){}
	
	@Inject
	public PostoCredenciadoController( Result result, PostoCredenciadoDao postoCredenciadoDao ){
		this.result = result;
		this.postoCredenciadoDao = postoCredenciadoDao;
	}
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( PostoCredenciado postoCredenciado  ) throws ParseException {			
		postoCredenciado.setDataRegistro(new Date());
		postoCredenciado.setAtivo(true);
		postoCredenciadoDao.novo(postoCredenciado);	
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(postoCredenciadoDao.todosAtivos())			  
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(PostoCredenciado postoCredenciado, Long id) throws ParseException {		
		postoCredenciado.setIdPosto(id);
		postoCredenciadoDao.atualizar(postoCredenciado);
		result.use(status()).ok();
	}	
}
