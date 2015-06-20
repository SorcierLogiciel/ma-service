package org.westcoasthonorcamp.ma.service.info;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;

public interface MusicInfo extends Serializable
{
	
	int getMusicId();
	
	int getScheduleId();
	
	Path getLocation();
	
	Date getNextEvent();
	
	boolean isOverride();
	
}
