package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Veiculo;

@RequestScoped
public class VeiculoDao extends GenericDao<Veiculo> {

	public List<Veiculo> todosAtivos() {

		String jpql = "SELECT c FROM Veiculo c WHERE c.ativo = :ativo";

		TypedQuery<Veiculo> query = em.createQuery(jpql, Veiculo.class);
		query.setParameter("ativo", true);		
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
