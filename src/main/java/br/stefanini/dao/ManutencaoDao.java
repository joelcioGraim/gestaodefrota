package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Manutencao;

@RequestScoped
public class ManutencaoDao extends GenericDao<Manutencao> {

	public List<Manutencao> todosAtivos() {

		String jpql = "SELECT c FROM Manutencao c WHERE c.ativo = :ativo";

		TypedQuery<Manutencao> query = em.createQuery(jpql, Manutencao.class);
		query.setParameter("ativo", true);	
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
