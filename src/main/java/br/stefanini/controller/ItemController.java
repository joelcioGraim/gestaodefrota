package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;

import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Result;
import br.stefanini.dao.ItemDao;

@Controller
@Path(value = "/item")
public class ItemController {
	
	private Result result;
	private ItemDao itemDao;
	
	/**
	 * @deprecated
	 */
	public ItemController() {
		// TODO Auto-generated constructor stub
	}
	
	public ItemController( Result result, ItemDao itemDao ) {
		this.result = result;
		this.itemDao = itemDao;
	}
	
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(itemDao.todosAtivos())			  
			  .serialize();
	}

}
