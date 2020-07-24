package br.stefanini.controller;

import static br.com.caelum.vraptor.view.Results.json;
import static br.com.caelum.vraptor.view.Results.status;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.transaction.Transactional;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithRoot;
import br.com.caelum.vraptor.serialization.gson.WithoutRoot;
import br.stefanini.dao.UsuarioDao;
import br.stefanini.dto.AutenticacaoDto;
import br.stefanini.interceptor.CapturaToken;
import br.stefanini.model.Usuario;
import br.stefanini.util.JWTUtil;
import br.stefanini.util.SHA1Util;

@Controller
@Path(value = "/usuario")
public class UsuarioController {
	
	private Result result;
	private UsuarioDao usuarioDao;
	private AutenticacaoDto autenticacaoDto;
	
	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public UsuarioController(){}
	
	@Inject
	public UsuarioController( Result result, UsuarioDao usuarioDao, AutenticacaoDto autenticacaoDto){
		this.result = result;
		this.usuarioDao = usuarioDao;	
		this.autenticacaoDto = autenticacaoDto;		
	}
		
	@CapturaToken
	@Post
	@Path(value = {"/", ""})
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void novo( Usuario usuario ) {
		//Criptografia de senha
		String senha = usuario.getSenha();		
		usuario.setSenha(SHA1Util.shaPassword(senha));
		usuario.setDataRegistro(new Date());
		usuario.setAtivo(true);
		
		usuarioDao.novo(usuario);
		result.use(status()).created();
	}
	
	@CapturaToken
	@Get
	@Path(value = {"/", ""})
	public void todos() {
		result.use(json())
			  .withoutRoot()
			  .from(usuarioDao.todosAtivos())			  
			  .include("perfil")
			  .serialize();
	}
	
	@CapturaToken
	@Put
	@Path(value = "/{id}")
	@Consumes(value = "application/json", options = WithoutRoot.class)
	public void editar(Usuario usuario, Long id) {
		usuario.setIdUsuario(id);	
		
	    usuarioDao.atualizar(usuario);
		result.use(status()).ok();
	}
	
	@Transactional
	@CapturaToken
	@Post
	@Path(value = {"/trocarSenha", " "})
	@Consumes(value = "application/json", options = WithRoot.class)
	public void trocarSenha(Usuario usuario) throws Exception {		
		
		String resultado = usuarioDao.validaTrocaSenha(usuario);
		
		if(resultado.equals("0")){
			 result.use(json()).from(new ValidationMessage("success", "Atualizado com sucesso!")).serialize();
		}else if (resultado.equals("1")){
			 result.use(json()).from(new ValidationMessage("warning", "Usuário não encontrado!")).serialize();			
		}else if (resultado.equals("2")){
			 result.use(json()).from(new ValidationMessage("warning", "Senha atual incorreta!")).serialize();			
		}else{
			 result.use(json()).from(new ValidationMessage("error", "Servidor não respodeu a autenticação!")).serialize();			
		}
	}
	
	
	@Post
	@Path(value = {"/autenticar", ""})
	@Consumes(value = "application/json", options = WithRoot.class)		
	public void autenticar( Usuario usuario) throws Exception {
	
		Usuario user = usuarioDao.validaLogin(usuario);
		
		if(user != null){
			autenticacaoDto.setToken(JWTUtil.createToken(user));
			autenticacaoDto.setNum(user.getIdUsuario());
			autenticacaoDto.setNome(user.getNome());
			autenticacaoDto.setEmail(user.getEmail());
			autenticacaoDto.setPerfil(user.getPerfil());
			
			result.use(json()).from(autenticacaoDto)		
			.include("perfil")			
			.serialize();				
		}			
	}		
	
}
