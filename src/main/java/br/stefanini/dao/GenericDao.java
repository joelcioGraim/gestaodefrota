package br.stefanini.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.transaction.Transactional;

/**
 *
 * Uma classe Repositorio abstrata para uso com as entidades do nosso sistema
 *
 */
@Stateless
public abstract class GenericDao<T> {
	
	protected Class<T> tipo = retornaTipo();
	
	@PersistenceContext(unitName = "gestaodefrota")
	protected EntityManager em;

	@Transactional
	public void novo(T entidade) {
		em.persist(entidade);
	}

	@Transactional
	public void remover(T entidade) {
		em.remove(entidade);
	}

	@SuppressWarnings("unchecked")
	public List<T> todos() {
		CriteriaQuery<Object> cq = em.getCriteriaBuilder().createQuery();
		cq.select(cq.from(tipo));
		return (List<T>) em.createQuery(cq).getResultList();
	}

	public T localizar(long id) {
		return em.find(tipo, id);
	}

	@Transactional
	public T atualizar(T entidade) {
		return em.merge(entidade);
	}

	/**
	 * @author Joelcio Graim<br>
	 *
	 *  Este método retorna o tipo da Classe, dessa maneira não é
	 *  necessário cada Service expor seu tipo!!!!
	 *
	 * @return Class<T>
	 */
	@SuppressWarnings({ "unchecked" })
	private Class<T> retornaTipo() {
		Class<?> clazz = this.getClass();
		
		while (!clazz.getSuperclass().equals(GenericDao.class)) {
			clazz = clazz.getSuperclass();
		}
		
		ParameterizedType tipoGenerico = (ParameterizedType) clazz.getGenericSuperclass();
		return (Class<T>) tipoGenerico.getActualTypeArguments()[0];
	}
}
