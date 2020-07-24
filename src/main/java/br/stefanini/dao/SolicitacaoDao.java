package br.stefanini.dao;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.stefanini.model.Solicitacao;

@RequestScoped
public class SolicitacaoDao extends GenericDao<Solicitacao> {

	public List<Solicitacao> todosAtivos() {

		String jpql = "SELECT c FROM Solicitacao c WHERE c.ativo = :ativo";

		TypedQuery<Solicitacao> query = em.createQuery(jpql, Solicitacao.class);
		query.setParameter("ativo", true);		
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}
	}	
}
