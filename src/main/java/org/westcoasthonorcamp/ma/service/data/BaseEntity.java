package org.westcoasthonorcamp.ma.service.data;

import java.io.Serializable;

/**
 * 
 * @author Joshua
 */
public interface BaseEntity extends Serializable
{
	
	/**
	 * 
	 * @return
	 */
	int getId();
	
	/**
	 * 
	 * @param id
	 */
	void setId(int id);
	
}
