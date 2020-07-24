package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Oficina;

@RequestScoped
public class OficinaDao extends GenericDao<Oficina> {

	public List<Oficina> todosAtivos() {

		String jpql = "SELECT c FROM Oficina c WHERE c.ativo = :ativo";

		TypedQuery<Oficina> query = em.createQuery(jpql, Oficina.class);
		query.setParameter("ativo", true);	
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
