package org.wchc.mas.enums;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public enum ScheduleScope
{
	
	//TODO: Add initialization methods for server start
	//TODO: Add tests to verify increment calculations
	
	SECOND
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.SECOND);
		}
		
	},
	
	MINUTE
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.MINUTE);
		}
		
	},
	
	HOUR
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.HOUR);
		}
		
	},
	
	DAY
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.DAY_OF_YEAR);
		}
		
	},
	
	WEEK
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.WEEK_OF_YEAR);
		}
		
	},
	
	MONTH_DAY_OF_WEEK
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
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
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.MONTH);
		}
		
	},
	
	YEAR
	{

		@Override
		public Date incrementDate(Date initialDate, int multiplier)
		{
			return incrementDate(initialDate, multiplier, Calendar.YEAR);
		}
		
	};
	
	public abstract Date incrementDate(Date initialDate, int multiplier);
	
	protected Date incrementDate(Date initialDate, int multiplier, int calendarField)
	{
		
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(initialDate);
		calendar.add(calendarField, multiplier);
		return calendar.getTime();
		
	}
	
}
