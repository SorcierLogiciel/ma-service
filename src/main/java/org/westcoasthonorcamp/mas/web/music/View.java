package org.westcoasthonorcamp.mas.web.music;

import java.io.IOException;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.enterprise.inject.Instance;
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
 * Servlet implementation class View
 */
@WebServlet({ "/music"})
public class View extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Instance<PersistenceManager> pm;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			
			int musicId = Integer.parseInt(request.getParameter("musicId"));
			Music music = pm.get().findById(Music.class, musicId);
			
			if(music == null)
			{
				throw new IllegalArgumentException(String.format("Music with id %d not found", musicId));
			}
			
			Set<Schedule> schedules = new TreeSet<>(new Comparator<Schedule>()
			{

				@Override
				public int compare(Schedule o1, Schedule o2)
				{
					return o1.getNextEventTime().compareTo(o2.getNextEventTime());
				}
				
			});
			
			schedules.addAll(music.getSchedules());
			request.setAttribute("music", music);
			request.setAttribute("schedules", schedules);
			request.getRequestDispatcher("/WEB-INF/music/view.jsp").forward(request, response);
			
		}
		catch(IllegalArgumentException e)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Music with that id was not found");
		}
		
	}

}
