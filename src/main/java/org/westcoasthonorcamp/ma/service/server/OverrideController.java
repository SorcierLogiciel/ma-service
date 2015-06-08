package org.westcoasthonorcamp.ma.service.server;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;

import org.westcoasthonorcamp.ma.common.data.Music;
import org.westcoasthonorcamp.ma.service.info.OverrideMusic;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;

import se.hirt.pi.adafruitlcd.Button;
import se.hirt.pi.adafruitlcd.ButtonListener;
import se.hirt.pi.adafruitlcd.ButtonPressedObserver;
import se.hirt.pi.adafruitlcd.Color;
import se.hirt.pi.adafruitlcd.ILCD;
import se.hirt.pi.adafruitlcd.impl.RealLCD;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class OverrideController
{
	
	private enum CommandState
	{
		
		DISABLED,
		ENABLED,
		HIDDEN,
		PLAY,
		STOP
		
	}
	
	@Inject
	private MusicScheduler ms;
	
	@Inject
	private PersistenceManager pm;
	
	private ILCD lcd;
	private ButtonPressedObserver observer;
	private ButtonListener up;
	private ButtonListener down;
	private ButtonListener left;
	private ButtonListener right;
	private ButtonListener select;
	private ButtonListener override;
	private CommandState savedState;
	private CommandState state;
	private String screenText = "";
	private int selectedMusic = -1;
	
	@PostConstruct
	private void init()
	{
		
		try
		{
			
			up = new ButtonListener()
			{
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					if(button != Button.UP)
					{
						return;
					}
					
					try
					{
						
						List<Music> musics = pm.findAll(Music.class);
						if(musics.size() > 0)
						{

							state = CommandState.PLAY;
							selectedMusic = selectedMusic - 1 < 0 ? Integer.MAX_VALUE : selectedMusic - 1;
							selectedMusic = selectedMusic > musics.size() - 1 ? musics.size() - 1 : selectedMusic;
							setText(musics.get(selectedMusic).getName(), "Play Now?");
							
						}
						
					}
					catch(IOException e)
					{
						System.out.println("Unable to scroll up");
					}
					
				}
				
			};
			
			down = new ButtonListener()
			{
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					if(button != Button.DOWN)
					{
						return;
					}
					
					try
					{
						
						List<Music> musics = pm.findAll(Music.class);
						if(musics.size() > 0)
						{
							
							state = CommandState.PLAY;
							selectedMusic = selectedMusic + 1 > musics.size() - 1 ? 0 : selectedMusic + 1;
							setText(musics.get(selectedMusic).getName(), "Play Now?");
							
						}
						
					}
					catch(IOException e)
					{
						System.out.println("Unable to scroll down");
					}
					
				}
				
			};
			
			left = new ButtonListener()
			{
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					if(button != Button.LEFT)
					{
						return;
					}
					
					toggleBacklight();
					
				}
			
			};
			
			right = new ButtonListener()
			{
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					if(button != Button.RIGHT)
					{
						return;
					}
					
					try
					{
						state = CommandState.STOP;
						setText("Stop Playing?");
					}
					catch(IOException e)
					{
						System.out.println("Unable to prompt stop");
					}
					
				}
			
			};
			
			select = new ButtonListener()
			{
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					if(button != Button.SELECT)
					{
						return;
					}
					
					try
					{
						
						switch(state)
						{
							
							case PLAY:
								List<Music> musics = pm.findAll(Music.class);
								if(selectedMusic >= 0 && selectedMusic < musics.size())
								{
									
									Music music = musics.get(selectedMusic);
									setText(music.getName(), "Now Playing");
									ms.registerMusic(new OverrideMusic(music.getLocation()));
									
								}
								break;
								
							case STOP:
								setText("Stopped");
								ms.stopMusic();
								break;
								
							default:
								break;
								
						}
						state = CommandState.ENABLED;
						
					}
					catch(IOException e)
					{
						System.out.println("Unable to stop or start song");
					}
					
				}
				
			};
			
			override = new ButtonListener()
			{
				
				private int count = 0;
				
				@Override
				public void onButtonPressed(Button button)
				{
					
					count++;
					
					if(button != Button.SELECT)
					{
						count = 0;
					}
					
					if(count >= 5)
					{
						count = 0;
						toggleOverride();
					}
					
				}
			
			};
			
			lcd = new RealLCD();
			lcd.setBacklight(Color.ON);
			observer = new ButtonPressedObserver(lcd);
			observer.addButtonListener(left);
			setOverride(state != CommandState.DISABLED);
			
		}
		catch(IOException | UnsatisfiedLinkError e)
		{
			System.out.println("Override will not be available");
		}
		
	}
	
	@PreDestroy
	private void destroy()
	{
		
		try
		{
			lcd.clear();
			lcd.stop();
		}
		catch(IOException | UnsatisfiedLinkError | NullPointerException e)
		{
			System.out.println("Override was not available for shutdown");
		}
		
	}
	
	public void toggleBacklight()
	{
		setBacklight(state == CommandState.HIDDEN);
	}
	
	public void toggleOverride()
	{
		setOverride(state == CommandState.DISABLED);
	}
	
	private void setBacklight(boolean enable)
	{
		
		if(lcd != null)
		{
			
			try
			{
				
				if(enable)
				{
					
					lcd.setBacklight(Color.ON);
					lcd.setText(screenText);
					state = savedState;
					setObservers(state != CommandState.DISABLED);
					
				}
				else
				{
					
					clearObservers();
					lcd.clear();
					lcd.setBacklight(Color.OFF);
					savedState = state;
					state = CommandState.HIDDEN;
					
				}
				
			}
			catch(IOException e)
			{
				System.out.println("Error toggling backlight");
			}
			
		}
		else
		{
			System.out.println("Backlight is not available");
		}
		
	}
	
	private void setOverride(boolean enable)
	{
		
		if(lcd != null)
		{
			
			try
			{
				
				if(enable)
				{
					setText("Override", "Enabled");
					state = CommandState.ENABLED;
				}
				else
				{
					setText("Override", "Disabled");
					state = CommandState.DISABLED;
				}
				setObservers(enable);
				selectedMusic = -1;
				
			}
			catch(IOException e)
			{
				System.out.println("Error toggling override");
			}
			
		}
		else
		{
			System.out.println("Override is not available");
		}
		
	}
	
	private void setObservers(boolean enable)
	{
		
		clearObservers();
		if(enable)
		{
			enableObservers();
		}
		else
		{
			disableObservers();
		}
		
	}

	private void clearObservers()
	{
		
		observer.removeButtonListener(up);
		observer.removeButtonListener(down);
		observer.removeButtonListener(right);
		observer.removeButtonListener(select);
		observer.removeButtonListener(override);
		
	}
	
	private void enableObservers()
	{

		observer.addButtonListener(up);
		observer.addButtonListener(down);
		observer.addButtonListener(right);
		observer.addButtonListener(select);
		observer.removeButtonListener(override);
		
	}
	
	private void disableObservers()
	{

		observer.removeButtonListener(up);
		observer.removeButtonListener(down);
		observer.removeButtonListener(right);
		observer.removeButtonListener(select);
		observer.addButtonListener(override);
		
	}
	
	private void setText(String lineOne) throws IOException
	{
		setText(lineOne, "");
	}

	private void setText(String lineOne, String lineTwo) throws IOException
	{
		
		lcd.clear();
		screenText = String.format("%s\n%s", lineOne, lineTwo);
		lcd.setText(screenText);
		
	}
	
}
