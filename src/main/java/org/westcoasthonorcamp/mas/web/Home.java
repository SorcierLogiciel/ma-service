package org.westcoasthonorcamp.mas.web;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.data.Music;
import org.westcoasthonorcamp.mas.data.Schedule;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;

/**
 * Servlet implementation class Index
 */
@WebServlet({ "/home", "/" })
public class Home extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersistenceManager pm;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		
		Set<Schedule> schedules = new TreeSet<>(new Comparator<Schedule>()
		{

			@Override
			public int compare(Schedule o1, Schedule o2)
			{
				return o1.getNextEventTime().compareTo(o2.getNextEventTime());
			}
			
		});
				
		schedules.addAll(pm.findAll(Schedule.class));
		request.setAttribute("schedules", schedules);
		request.setAttribute("musics", pm.findAll(Music.class));
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		
		try
		{
			int scheduleCount = Integer.parseInt(request.getParameter("scheduleCount"));
			if(scheduleCount > 0)
			{
				request.getSession().setAttribute("scheduleCount", scheduleCount);
			}
		}
		finally
		{
			doGet(request, response);
		}
		
	}

}
