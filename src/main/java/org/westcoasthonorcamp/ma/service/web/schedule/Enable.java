package org.westcoasthonorcamp.ma.service.web.schedule;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.ma.service.data.Schedule;
import org.westcoasthonorcamp.ma.service.info.StandardMusic;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;
import org.westcoasthonorcamp.ma.service.server.MusicScheduler;

/**
 * Servlet implementation class Enable
 */
@WebServlet({ "/schedule/enable"})
public class Enable extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersistenceManager pm;
	
	@Inject
	private MusicScheduler ms;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			
			Schedule schedule = pm.findById(Schedule.class, Integer.parseInt(request.getParameter("scheduleId")));
			boolean wasEnabled = schedule.isEnabled();
			schedule.setEnabled(request.getParameter("enable") != null);
			pm.update(schedule);
			
			if(schedule.isEnabled() && !wasEnabled)
			{
				ms.registerMusic(new StandardMusic(schedule.getMusic().getId(), schedule.getId()));
			}
			else if(!schedule.isEnabled() && wasEnabled)
			{
				ms.unregisterMusic(schedule.getId());
			}
			
			response.sendRedirect(getServletContext().getContextPath() + "/music?musicId=" + schedule.getMusic().getId());
			
		}
		catch(NumberFormatException | NullPointerException e)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A Schedule with that id was not found");
		}
		
	}

}
