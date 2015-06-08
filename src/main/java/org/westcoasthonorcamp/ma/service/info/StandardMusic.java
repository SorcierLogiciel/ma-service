package org.westcoasthonorcamp.ma.service.info;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import javax.enterprise.inject.spi.CDI;

import lombok.AllArgsConstructor;

import org.westcoasthonorcamp.ma.common.data.Music;
import org.westcoasthonorcamp.ma.common.data.Schedule;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;

@AllArgsConstructor
public class StandardMusic implements MusicInfo
{
	private static final long serialVersionUID = -3582526130712436508L;

	private final int musicId;
	private final int scheduleId;
	
	@Override
	public int getId()
	{
		return scheduleId;
	}

	@Override
	public Path getLocation()
	{
		return Paths.get(getPersistenceManager().findById(Music.class, musicId).getLocation());
	}

	@Override
	public Date getNextEvent()
	{
		
		PersistenceManager pm = getPersistenceManager();
		Schedule foundSchedule = pm.findById(Schedule.class, scheduleId);
		if(foundSchedule != null)
		{
			
			foundSchedule.generateNextEventTime();
			pm.update(foundSchedule);
			return foundSchedule.getNextEventTime();
			
		}
		else
		{
			return null;
		}
		
	}
	
	@Override
	public boolean getOverride()
	{
		return false;
	}

	private PersistenceManager getPersistenceManager()
	{
		return CDI.current().select(PersistenceManager.class).get();
	}
	
}
