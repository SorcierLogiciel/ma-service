package org.westcoasthonorcamp.ma.service.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 
 * @author Joshua
 */
@AllArgsConstructor
public class Start
{
	
	@Getter
	private final int musicId;
	
	@Getter
	private final long startTime;
	
	@Getter
	private final boolean override;
	
}
