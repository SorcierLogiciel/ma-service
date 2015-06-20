package org.westcoasthonorcamp.ma.service.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Timeout;
import javax.ejb.TimerService;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.enterprise.event.Observes;

import lombok.Synchronized;

import org.westcoasthonorcamp.ma.common.data.Music;
import org.westcoasthonorcamp.ma.common.message.Status;
import org.westcoasthonorcamp.ma.service.enums.EntityEvent;
import org.westcoasthonorcamp.ma.service.event.Start;
import org.westcoasthonorcamp.ma.service.event.Stop;

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class StatusBeacon
{

	@Resource
	private TimerService timerService;
	
	private InetAddress sendAddress;
	private int sendPort;
	private MulticastSocket socket;
	private String serverUrl;
	
	private Status status;
	
	@PostConstruct
	private void init()
	{
		
		try
		{
			
			String address = System.getProperty("MAS_ADDRESS");
			int port = Integer.parseInt(System.getProperty("MAS_PORT", "0"));
			sendAddress = InetAddress.getByName("225.0.0.1");
			sendPort = 5000;
		
			if(address != null && !address.isEmpty() && port != 0)
			{
				
				socket = new MulticastSocket();
				socket.joinGroup(sendAddress);
				serverUrl = new String("http://" + address + ":" + port + "/MusicAlarmService/rest");
				status = new Status(serverUrl, System.currentTimeMillis(), true, System.currentTimeMillis(), 0, 0, false);
				timerService.createTimer(0, 5000, null);
				
			}
			else
			{
				System.out.println("Beacon configuration failed to load");
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Beacon configuration failed to load");
		}
		
	}
	
	@Synchronized("status")
	public void handleCreate(@Observes @EntityEvent.Annotation(EntityEvent.CREATED) Music event)
	{
		
		status.setSystemUpdateId(System.currentTimeMillis());
		status.setReload(true);
		sendBeacon();
		
	}
	
	@Synchronized("status")
	public void handleDelete(@Observes @EntityEvent.Annotation(EntityEvent.DELETED) Music event)
	{
		
		status.setSystemUpdateId(System.currentTimeMillis());
		status.setReload(true);
		sendBeacon();
		
	}
	
	@Synchronized("status")
	public void handleStart(@Observes Start event)
	{
		
		status.setMusicUpdateId(System.currentTimeMillis());
		status.setMusicId(event.getMusicId());
		status.setStartTime(event.getStartTime());
		status.setOverride(event.isOverride());
		sendBeacon();
		
	}
	
	@Synchronized("status")
	public void handleStop(@Observes Stop event)
	{
		
		status.setMusicUpdateId(System.currentTimeMillis());
		status.setMusicId(0);
		status.setOverride(event.isOverride());
		sendBeacon();
		
	}
	
	@Timeout
	@Synchronized("status")
	private void sendBeacon()
	{
		
		try
		{
			
			ByteArrayOutputStream baoStream = new ByteArrayOutputStream(8192);
			ObjectOutputStream ooStream = new ObjectOutputStream(baoStream);
			ooStream.writeObject(status);
			byte[] data = baoStream.toByteArray();
			socket.send(new DatagramPacket(data, data.length, sendAddress, sendPort));
			ooStream.close();
			
		}
		catch(IOException e)
		{
			System.out.println("Unable to send status beacon");
		}
		
	}
	
}
