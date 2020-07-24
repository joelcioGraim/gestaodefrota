package br.stefanini.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA1Util {

	private SHA1Util() {
	}

	// A Funcao

	public static String shaPassword(String senha) {

		// Essa classe pega um valor de tamanho arbitrario e transforma em um
		// valor de tamanho fixo

		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA");

			// Atualiza o valor com os bytes especificados

			md.update(senha.getBytes());

			// Conclui o c�lculo de hash realizando opera��es finais, tais como
			// preenchimento.

			BigInteger hash = new BigInteger(1, md.digest());

			// Retorna a representa��o String decimal deste BigInteger, com
			// tamanho
			// igual a 16.

			String retornaSenha = hash.toString(16);

			return retornaSenha;

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
