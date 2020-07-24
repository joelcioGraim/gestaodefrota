package br.stefanini.dao;

import java.util.List;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Usuario;
import br.stefanini.util.JWTUtil;
import br.stefanini.util.SHA1Util;

@RequestScoped
public class UsuarioDao extends GenericDao<Usuario> {

	public List<Usuario> todosAtivos() {

		String jpql = "SELECT c FROM Usuario c WHERE c.ativo = :ativo";

		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		query.setParameter("ativo", true);		
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	public Usuario validaLogin(Usuario usuario) throws Exception{
		try{
			String jpql = "SELECT c FROM Usuario AS c WHERE c.login = :login AND c.senha = :senha AND c.ativo = :ativo";
			
			//Criptografia de senha
			String senha = usuario.getSenha();			
	
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);			
			query.setParameter("login", usuario.getLogin());
			query.setParameter("senha", SHA1Util.shaPassword(senha));
			query.setParameter("ativo", true);			
			
			usuario = query.getSingleResult();
			
			//String token = JWTUtil.createToken(usuario);
			return usuario;
						
		}catch(NoResultException e){
			return null;
		}
	}
	
	
	public String validaTrocaSenha(Usuario usuario) throws Exception{
			Map<String, Object> claims;
			
		try{			
			claims = JWTUtil.decode(usuario.getToken());
			
			int id = (int) claims.get("num");
			long idUsuario = (long) id;
			String senhaAtual = (String) claims.get("serie");	
			
			if(senhaAtual.equals(SHA1Util.shaPassword(usuario.getSenha()))){	
				
				String novaSenha = usuario.getSenhaNova(); //Guarda a nova senha
				
				String jpql = "SELECT c FROM Usuario AS c WHERE c.idUsuario = :idUsuario AND c.senha = :senha AND c.ativo = :ativo";
				
				TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);			
				query.setParameter("idUsuario", idUsuario);
				query.setParameter("senha", SHA1Util.shaPassword(usuario.getSenha()));
				query.setParameter("ativo", true);			
				
				usuario = query.getSingleResult();
			
				if(usuario != null){
					usuario.setIdUsuario(idUsuario);
					usuario.setSenha(SHA1Util.shaPassword(novaSenha)); 
					atualizar(usuario);	
					return "0";
				}else{
					return "1";
				}				
			}else{
				return "2";	
			}
						
		}catch(NoResultException e){
			return "Servidor não respodeu a autenticação!"+ e;
		}
	}
	
	public String validaToken(String token) throws Exception{
		Map<String, Object> claims;
		
		try{			
			claims = JWTUtil.decode(token);
			
			long idUsuario = (long) claims.get("num");
			String login = (String) claims.get("nick");
			String senha = (String) claims.get("serie");
			
			String jpql = "SELECT c FROM Usuario AS c WHERE c.idUsuario = :idUsuario AND c.senha = :senha AND c.login = :login AND c.ativo = :ativo";				
	
			TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);			
			query.setParameter("idUsuario", idUsuario);
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			query.setParameter("ativo", true);			
			
			if(query.getSingleResult() != null){			
			  return "Autorizado";
			}else{
			  return "Não Autorizado";	
			}
						
		}catch(NoResultException e){
			return "Servidor não respodeu a autenticação!";
		}
	}
	
}
