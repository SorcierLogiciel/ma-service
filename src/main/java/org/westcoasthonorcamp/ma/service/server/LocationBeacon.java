package org.westcoasthonorcamp.ma.service.server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

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

@Startup
@Singleton
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
@TransactionManagement(TransactionManagementType.BEAN)
public class LocationBeacon
{
	

	
	
	@Resource
	private TimerService timerService;
	
	private byte[] serverUrl;
	private InetAddress sendAddress;
	private int sendPort;
	
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
				serverUrl = new String("http://" + address + ":" + port + "/MusicAlarmService/rest").getBytes();
				timerService.createTimer(0, 5000, null);
			}
			else
			{
				System.out.println("Beacon configuration failed to load");
			}
			
		}
		catch(UnknownHostException e)
		{
			System.out.println("Beacon configuration failed to load");
		}
		
	}
	
	@Timeout
	private void sendBeacon()
	{
		
		try(MulticastSocket socket = new MulticastSocket())
		{
			socket.joinGroup(sendAddress);
			socket.send(new DatagramPacket(serverUrl, serverUrl.length, sendAddress, sendPort));
		}
		catch(IOException e)
		{
			System.out.println("Unable to send location beacon");
		}
		
	}
	
}
