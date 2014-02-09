package org.wchc.mas.data;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.wchc.mas.enums.ScheduleScope;

import lombok.Getter;
import lombok.Setter;

@Entity
public abstract class Schedule implements BaseEntity
{
	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Getter
	@Setter
	private ScheduleScope scheduleScope;
	
	@Getter
	@Setter
	private Date creationTime;
	
	@Getter
	@Setter
	private Date nextEventTime;
	
	@Getter
	@Setter
	private int repeatScale = 0;
	
}
