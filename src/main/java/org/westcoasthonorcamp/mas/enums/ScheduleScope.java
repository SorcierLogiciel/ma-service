package org.westcoasthonorcamp.mas.enums;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 
 * @author Joshua
 */
public enum ScheduleScope
{
	
	//TODO: Add initialization methods for server start
	//TODO: Add tests to verify increment calculations
	
	SECOND
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.SECOND);
		}
		
	},
	
	MINUTE
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.MINUTE);
		}
		
	},
	
	HOUR
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.HOUR);
		}
		
	},
	
	DAY
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.DAY_OF_YEAR);
		}
		
	},
	
	WEEK
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.WEEK_OF_YEAR);
		}
		
	},
	
	MONTH_DAY_OF_WEEK
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			
			GregorianCalendar initialCalendar = new GregorianCalendar();
			initialCalendar.setTime(initialDate);
			GregorianCalendar resultCalendar = new GregorianCalendar();
			resultCalendar.setTime(initialDate);
			resultCalendar.add(Calendar.MONTH, multiplier);
			resultCalendar.set(Calendar.WEEK_OF_MONTH, initialCalendar.get(Calendar.WEEK_OF_MONTH));
			resultCalendar.set(Calendar.DAY_OF_WEEK,  initialCalendar.get(Calendar.DAY_OF_WEEK));
			return initialCalendar.getTime();
			
		}
		
	},
	
	MONTH_DAY_OF_MONTH
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.MONTH);
		}
		
	},
	
	YEAR
	{

		@Override
		public Date next(Date initialDate, int multiplier)
		{
			return next(initialDate, multiplier, Calendar.YEAR);
		}
		
	};
	
	/**
	 * 
	 * @param initialDate
	 * @param multiplier
	 * @return
	 */
	public abstract Date next(Date initialDate, int multiplier);
	
	/**
	 * 
	 * @param initialDate
	 * @param multiplier
	 * @param calendarField
	 * @return
	 */
	protected Date next(Date initialDate, int multiplier, int calendarField)
	{
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(initialDate);
		calendar.add(calendarField, multiplier);
		return calendar.getTime();
		
	}
	
}
