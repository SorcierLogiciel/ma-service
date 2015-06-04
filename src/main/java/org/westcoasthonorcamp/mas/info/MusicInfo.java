package org.westcoasthonorcamp.mas.info;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Date;

public interface MusicInfo extends Serializable
{
	
	int getId();
	
	Path getLocation();
	
	Date getNextEvent();
	
	boolean getOverride();
	
}
