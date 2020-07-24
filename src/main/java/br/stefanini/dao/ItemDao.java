package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Item;

@RequestScoped
public class ItemDao extends GenericDao<Item> {

	public List<Item> todosAtivos() {

		String jpql = "SELECT c FROM Item c WHERE c.ativo = :ativo";

		TypedQuery<Item> query = em.createQuery(jpql, Item.class);
		query.setParameter("ativo", true);	
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
