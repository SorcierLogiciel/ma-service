package org.westcoasthonorcamp.ma.service.persistence;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.westcoasthonorcamp.ma.common.data.BaseEntity;
import org.westcoasthonorcamp.ma.service.enums.EntityEvent;

/**
 * 
 * @author Joshua
 */
@Stateless
public class PersistenceManager
{
	
	@Inject
	private BeanManager bm;
	
	@PersistenceContext(unitName = "MusicAlarmService")
	private EntityManager em;
	
	/**
	 * 
	 * @param entityClass
	 * @return
	 */
	public <T extends BaseEntity> List<T> findAll(Class<T> entityClass)
	{
		
		CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(entityClass);
		Root<T> root = query.from(entityClass);
		query.select(root).orderBy(em.getCriteriaBuilder().asc(root.get("id")));
		return em.createQuery(query).getResultList();
		
	}
	
	/**
	 * 
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T extends BaseEntity> T findById(Class<T> entityClass, int id)
	{
		return em.find(entityClass, id);	
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T create(T entity)
	{
		
		em.persist(entity);
		fireCreate(entity);
		return entity;
		
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T update(T entity)
	{
		
		entity = em.merge(entity);
		fireUpdate(entity);
		return entity;
		
	}
	
	/**
	 * 
	 * @param entity
	 * @return
	 */
	public <T extends BaseEntity> T delete(T entity)
	{
		
		em.remove(em.merge(entity));
		fireDelete(entity);
		return entity;
		
	}
	
	private <T extends BaseEntity> void fireCreate(T entity)
	{
		bm.fireEvent(entity, new EntityEvent.Qualifier(EntityEvent.CREATED));
	}
	
	private <T extends BaseEntity> void fireUpdate(T entity)
	{
		bm.fireEvent(entity, new EntityEvent.Qualifier(EntityEvent.UPDATED));
	}
	
	private <T extends BaseEntity> void fireDelete(T entity)
	{
		bm.fireEvent(entity, new EntityEvent.Qualifier(EntityEvent.DELETED));
	}
	
}
