package org.westcoasthonorcamp.ma.service.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Joshua
 */
@Entity
@XmlRootElement
public class Music implements BaseEntity
{
	
	private static final long serialVersionUID = 2742456251827514498L;

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
	private String location;
	
	@Getter
	@ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
	@JoinColumn(name = "PLAN_ID")
	private Plan plan;
	
	@Getter
	@Setter
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "music", orphanRemoval = true)
	private List<Schedule> schedules = new ArrayList<>();
	
	public void setPlan(Plan plan)
	{
		this.plan = plan;
		plan.getMusics().add(this);
	}
	
}
