package org.wchc.mas.data;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Alarm implements BaseEntity
{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter
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
	private List<Schedule> schedules; 
	
	
	public void start()
	{
		
	}
	
}
