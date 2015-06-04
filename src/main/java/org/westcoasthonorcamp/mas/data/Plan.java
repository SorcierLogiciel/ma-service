package org.westcoasthonorcamp.mas.data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Joshua
 */
@Entity
public class Plan implements BaseEntity
{
	
	private static final long serialVersionUID = -5328952057792292158L;

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
	@OneToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL }, mappedBy = "plan", orphanRemoval = true)
	private List<Music> musics = new ArrayList<>();
	
}
