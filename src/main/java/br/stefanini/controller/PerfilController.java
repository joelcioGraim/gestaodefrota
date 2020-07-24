package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;

import javax.inject.Inject;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.stefanini.dao.PerfilDao;
import br.stefanini.interceptor.CapturaToken;

@Controller
@Path(value = "/perfil")
public class PerfilController {

	private Result result;
	private PerfilDao perfilDao;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public PerfilController(){}
	
	@Inject
	public PerfilController( Result result, PerfilDao perfilDao ){
		this.result = result;
		this.perfilDao = perfilDao;
	}
	
	@CapturaToken
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(perfilDao.todosAtivos())			  
			  .serialize();
	}
}
