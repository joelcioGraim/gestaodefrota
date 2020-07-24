package br.stefanini.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;

import br.stefanini.model.Usuario;

public class JWTUtil {

	private static final String SECRET = "st3f@n1n12016";

	public static String createToken(Usuario usuario) {
		JWTSigner signer = new JWTSigner(SECRET);

		HashMap<String, Object> claims = new HashMap<String, Object>();
		
		claims.put("num", usuario.getIdUsuario());
		claims.put("nick", usuario.getLogin());
		claims.put("serie", usuario.getSenha());
		

		String token = signer.sign(claims, new JWTSigner.Options()
				.setExpirySeconds(60*60*24*365*30).setIssuedAt(true));

		return token;

	}

	public static Map<String, Object> decode(String token)
			throws InvalidKeyException, NoSuchAlgorithmException,
			IllegalStateException, SignatureException, IOException,
			JWTVerifyException {
		JWTVerifier verifier = new JWTVerifier(SECRET);

		Map<String, Object> map = verifier.verify(token);

		return map;
	}

}