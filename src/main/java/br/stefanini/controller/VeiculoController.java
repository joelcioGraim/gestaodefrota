package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.status;

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
import br.stefanini.dao.VeiculoDao;
import br.stefanini.model.Veiculo;

@Controller
@Path(value = "/veiculo")
public class VeiculoController {
	
	private Result result;
	private VeiculoDao veiculoDao;	
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public VeiculoController(){}
	
	@Inject
	public VeiculoController( Result result, VeiculoDao veiculoDao){
		this.result = result;
		this.veiculoDao = veiculoDao;	
	}
		
	
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Veiculo veiculo ) {	
		veiculo.setDtcadastro(new Date());		
		veiculo.setAtivo(true);		
		veiculoDao.novo(veiculo);
		result.use(status()).created();
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(veiculoDao.todosAtivos())		  
			  .serialize();
	}
	
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Veiculo veiculo, Long id) {
		veiculo.setIdVeiculo(id);		
		veiculoDao.atualizar(veiculo);
		result.use(status()).ok();
	}	
	
}
