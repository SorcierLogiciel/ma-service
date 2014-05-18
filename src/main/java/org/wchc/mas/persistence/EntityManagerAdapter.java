package org.wchc.mas.persistence;

import java.util.List;

import javax.ejb.Singleton;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.wchc.mas.data.BaseEntity;
import org.wchc.mas.enums.EntityEvent;

/**
 * 
 * @author Joshua
 */
@Singleton
public class EntityManagerAdapter
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
		query.select(root);
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
		
		em.remove(entity);
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
