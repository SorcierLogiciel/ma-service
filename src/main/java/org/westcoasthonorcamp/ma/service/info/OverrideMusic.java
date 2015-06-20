package org.westcoasthonorcamp.ma.service.info;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class OverrideMusic implements MusicInfo
{
	
	private static final long serialVersionUID = -1502314817218315970L;
	
	private final Date start = new Date(System.currentTimeMillis() + 1000);
	private final int musicId;
	private final String location;
	
	@Override
	public int getMusicId()
	{
		return musicId;
	}
	
	@Override
	public int getScheduleId()
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
	public boolean isOverride()
	{
		return true;
	}
	
}
