package br.stefanini.interceptor;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Date;
import java.util.Map;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;

import com.auth0.jwt.JWTVerifyException;

import br.com.caelum.vraptor.AroundCall;
import br.com.caelum.vraptor.Intercepts;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.interceptor.AcceptsWithAnnotations;
import br.com.caelum.vraptor.interceptor.SimpleInterceptorStack;
import br.com.caelum.vraptor.view.Results;
import br.stefanini.dao.GenericDao;
import br.stefanini.model.Usuario;
import br.stefanini.util.JWTUtil;


@Intercepts
@RequestScoped
@AcceptsWithAnnotations(CapturaToken.class)
public class SecurityInterceptor extends GenericDao<Usuario> {

	private HttpServletRequest request;
	private Result result;

	/**
	 * CDI eyes only
	 * @deprecated
	 */
	public SecurityInterceptor() {
		this(null, null);
	}
	
//	@Accepts
//    public boolean accepts(ControllerMethod method) {
//        return method.containsAnnotation(CapturaToken.class);
//    }
		
	@Inject
	public SecurityInterceptor(HttpServletRequest request, Result result) {
		this.request = request;
		this.result = result;
	}
	

	@AroundCall
	public void intercept(SimpleInterceptorStack stack) {
		
		String token = request.getHeader("Authorization");		

		Map<String, Object> claims;
		try {
			claims = JWTUtil.decode(token);

			Integer userId = (Integer) claims.get("num");		
			String userLog = (String) claims.get("nick");
			String userPass = (String) claims.get("serie");
			
			if(userId != null && userLog != null && userPass != null){
				String jpql = "SELECT c FROM Usuario AS c WHERE c.idUsuario = :idUsuario AND c.login = :login AND c.senha = :senha AND c.ativo = :ativo";
				TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);	
				query.setParameter("idUsuario", userId.longValue());
				query.setParameter("login", userLog);
				query.setParameter("senha", userPass);
				query.setParameter("ativo", true);			
			
				System.out.println("Query: "+query.getSingleResult()); 
	
				if (query.getSingleResult().equals(null)) {
					result.use(Results.http()).setStatusCode(401);
					result.use(Results.json())
							.from("Não autorizado!", "msg").serialize();
				} else {
					result.use(Results.http()).addHeader("Authorization", token);
	
					stack.next();
				}
			}else{
				result.use(Results.http()).setStatusCode(401);
				result.use(Results.json())
						.from("Não autorizado!", "Tentativa de violação: "+ new Date()).serialize();
			}
		} catch (InvalidKeyException | NoSuchAlgorithmException
				| IllegalStateException | SignatureException | IOException
				| JWTVerifyException e) {
			result.use(Results.http()).setStatusCode(401);
			result.use(Results.json()).from(e.getMessage(), "Tentativa de violação: "+ new Date()).serialize();
		}
	}
}