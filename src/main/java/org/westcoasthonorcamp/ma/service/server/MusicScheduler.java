package org.westcoasthonorcamp.ma.service.server;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerConfig;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import org.westcoasthonorcamp.ma.common.data.Schedule;
import org.westcoasthonorcamp.ma.service.info.MusicInfo;
import org.westcoasthonorcamp.ma.service.info.StandardMusic;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class MusicScheduler
{
	
	@Inject
	private PersistenceManager pm;
	
	@Resource
	private TimerService timerService;
	
	private Player player;
	private Executor playerExecutor = Executors.newSingleThreadExecutor();
	private Map<Integer, Timer> timerMap = new HashMap<>();
	
	@PostConstruct
	private void init()
	{
		for(Schedule s : pm.findAll(Schedule.class))
		{
			registerMusic(new StandardMusic(s.getMusic().getId(), s.getId()));
		}
	}

	public void registerMusic(MusicInfo info)
	{
		if(info != null )
		{
			Date nextEvent = info.getNextEvent();
			if(nextEvent != null && nextEvent.after(new Date()))
			{
				timerMap.put(info.getId(), timerService.createSingleActionTimer(nextEvent, new TimerConfig(info, false)));				
			}
		}
	}
	
	public void stopMusic()
	{
		if(player != null)
		{
			player.close();
			player = null;
		}
	}
	
	public void unregisterMusic(int id)
	{
		Timer timer = timerMap.remove(id);
		if(timer != null)
		{
			timer.cancel();
		}
	}
	
	@Timeout
	private void startMusic(Timer timer)
	{

		MusicInfo info = (MusicInfo)timer.getInfo();
		timerMap.remove(info.getId());
		final Path location = info.getLocation();
		if(location != null)
		{
			
			registerMusic(info);
			if(player == null || player.isComplete() || info.getOverride())
			{
				
				if(player != null)
				{
					player.close();
				}
					
				playerExecutor.execute(new Runnable()
				{
					
					@Override
					public void run()
					{
					
						try
						{
							player = new Player(Files.newInputStream(location));
							player.play();
						}
						catch(JavaLayerException | IOException e)
						{
							System.out.println(String.format("Unable to play %s", location));
						}
						
					}
					
				});
				
			}
			
		}
		
	}
	
}
