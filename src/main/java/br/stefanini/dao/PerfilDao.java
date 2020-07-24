package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Perfil;

@RequestScoped
public class PerfilDao extends GenericDao<Perfil> {

	public List<Perfil> todosAtivos() {

		String jpql = "select c from Perfil c where c.ativo = :ativo";

		TypedQuery<Perfil> query = em.createQuery(jpql, Perfil.class);
		query.setParameter("ativo", true);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}
}
