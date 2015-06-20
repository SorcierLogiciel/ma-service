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
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import org.westcoasthonorcamp.ma.common.data.Schedule;
import org.westcoasthonorcamp.ma.service.event.Start;
import org.westcoasthonorcamp.ma.service.event.Stop;
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
	private BeanManager bm;
	
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
				timerMap.put(info.getScheduleId(), timerService.createSingleActionTimer(nextEvent, new TimerConfig(info, false)));				
			}
		}
	}
	
	public void stopMusic()
	{
		if(player != null)
		{
			
			bm.fireEvent(new Stop(true));
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

		final MusicInfo info = (MusicInfo)timer.getInfo();
		timerMap.remove(info.getScheduleId());
		final Path location = info.getLocation();
		if(location != null)
		{
			
			registerMusic(info);
			if(player == null || player.isComplete() || info.isOverride())
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
							
							long startTime = System.currentTimeMillis() + 3000;
							bm.fireEvent(new Start(info.getMusicId(), startTime, info.isOverride()));
							while(startTime > System.currentTimeMillis())
							{
								Thread.sleep(10);
							}
							player = new Player(Files.newInputStream(location));
							player.play();
							bm.fireEvent(new Stop(false));
							
						}
						catch(JavaLayerException | IOException | InterruptedException e)
						{
							System.out.println(String.format("Unable to play %s", location));
						}
						
					}
					
				});
				
			}
			
		}
		
	}
	
}
