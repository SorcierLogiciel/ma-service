package org.westcoasthonorcamp.mas.enums;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Joshua
 */
@AllArgsConstructor
public enum ScheduleScope
{
	
	NONE(0, "None", "None")
	{
		
		@Override
		public Date next(Date initialDate, int multiplier, AtomicInteger limit)
		{
			return initialDate;
		}
		
	},
	
	SECOND(Calendar.SECOND, "Second", "Seconds"),
	MINUTE(Calendar.MINUTE, "Minute", "Minutes"),
	HOUR(Calendar.HOUR, "Hour", "Hours"),
	DAY(Calendar.DAY_OF_YEAR, "Day", "Days"),
	WEEK(Calendar.WEEK_OF_YEAR, "Week", "Weeks"),	
	MONTH(Calendar.MONTH, "Month", "Months"),
	YEAR(Calendar.YEAR, "Year", "Years");
	
	@Getter
	private final int calendarField;
	
	@Getter
	private final String label;
	
	@Getter
	private final String labelPlural;
	
	/**
	 * 
	 * @param initialDate
	 * @param multiplier
	 * @return
	 */
	public Date next(Date initialDate, int multiplier, AtomicInteger limit)
	{

		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTime(initialDate);
		Date now = new Date();
		while(calendar.getTime().before(now) && (limit == null || limit.get() > 0))
		{
			calendar.add(calendarField, multiplier);
			if(limit != null)
			{
				limit.decrementAndGet();
			}
		}
		return calendar.getTime();
		
	}
	
}
