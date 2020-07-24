package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Abastecimento;

@RequestScoped
public class AbastecimentoDao extends GenericDao<Abastecimento> {

	public List<Abastecimento> todosAtivos() {

		String jpql = "SELECT c FROM Abastecimento c";

		TypedQuery<Abastecimento> query = em.createQuery(jpql, Abastecimento.class);			
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
