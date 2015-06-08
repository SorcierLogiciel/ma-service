package org.westcoasthonorcamp.ma.service.info;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class OverrideMusic implements MusicInfo
{
	
	private static final long serialVersionUID = -1502314817218315970L;
	
	private final Date start = new Date(System.currentTimeMillis() + 1000);
	private final String location;
	
	public OverrideMusic(String location)
	{
		this.location = location;
	}
	
	@Override
	public int getId()
	{
		return 0;
	}

	@Override
	public Path getLocation()
	{
		return Paths.get(location);
	}

	@Override
	public Date getNextEvent()
	{
		return start;
	}

	@Override
	public boolean getOverride()
	{
		return true;
	}
	
}
