package org.wchc.mas.persistence;

import javax.ejb.Singleton;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.wchc.mas.data.BaseEntity;
import org.wchc.mas.enums.EntityEvent;

@Singleton
public class EntityManagerAdapter
{
	
	@Inject
	private BeanManager bm;
	
	@PersistenceContext(unitName = "MusicAlarmService")
	private EntityManager em;
	
	public <T extends BaseEntity> T create(T entity)
	{
		
		em.persist(entity);
		fireCreate(entity);
		return entity;
		
	}
	
	public <T extends BaseEntity> T update(T entity)
	{
		
		entity = em.merge(entity);
		fireUpdate(entity);
		return entity;
		
	}
	
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
