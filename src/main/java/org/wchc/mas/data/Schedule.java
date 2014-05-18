package org.wchc.mas.data;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import org.wchc.mas.enums.ScheduleScope;

/**
 * 
 * @author Joshua
 */
@Entity
public class Schedule implements BaseEntity
{
	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Getter
	@Setter
	@Enumerated(EnumType.STRING)
	private ScheduleScope scheduleScope;
	
	@Getter
	@Setter
	private Timestamp creationTime;
	
	@Getter
	@Setter
	private Timestamp nextEventTime;
	
	@Getter
	@Setter
	private int repeatScale = 0;
	
}
