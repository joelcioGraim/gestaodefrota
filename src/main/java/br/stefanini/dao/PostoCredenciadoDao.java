package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.PostoCredenciado;

@RequestScoped
public class PostoCredenciadoDao extends GenericDao<PostoCredenciado> {

	public List<PostoCredenciado> todosAtivos() {

		String jpql = "select c from PostoCredenciado c where c.ativo = :ativo";

		TypedQuery<PostoCredenciado> query = em.createQuery(jpql, PostoCredenciado.class);
		query.setParameter("ativo", true);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
