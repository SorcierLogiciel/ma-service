package org.wchc.mas.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Joshua
 */
@Entity
public class Alarm implements BaseEntity
{
	
	@Getter
	@Setter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Getter
	@Setter
	private String name;
	
	@Getter
	@Setter
	private String musicLocation;
	
	@Getter
	@Setter
	@OneToMany
	@JoinColumn(name="ALARM_ID", referencedColumnName="id")
	private List<Schedule> schedules; 
	
	
	public void start()
	{
		
	}
	
}
