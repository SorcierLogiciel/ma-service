package org.westcoasthonorcamp.mas.enums;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.enterprise.util.AnnotationLiteral;

import lombok.AllArgsConstructor;

/**
 * 
 * @author Joshua
 */
public enum EntityEvent
{
	
	CREATED,
	UPDATED,
	DELETED;
	
	/**
	 * 
	 * @author Joshua
	 */
	@javax.inject.Qualifier
	@Retention(RUNTIME)
	@Target({TYPE, METHOD, FIELD, PARAMETER})
	public @interface Annotation 
	{
		EntityEvent value();
	}
	
	/**
	 * 
	 * @author Joshua
	 */
	@AllArgsConstructor
	@SuppressWarnings("all")
	public static final class Qualifier extends AnnotationLiteral<Annotation> implements Annotation
	{
		
		private final EntityEvent value;

		@Override
		public EntityEvent value()
		{
			return value;
		}
		
	}
	
}
