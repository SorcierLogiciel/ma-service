package org.westcoasthonorcamp.mas.web.schedule;

import java.io.IOException;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.data.Schedule;
import org.westcoasthonorcamp.mas.info.StandardMusic;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;
import org.westcoasthonorcamp.mas.server.MusicScheduler;

/**
 * Servlet implementation class Enable
 */
@WebServlet({ "/schedule/enable"})
public class Enable extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private Instance<PersistenceManager> pm;
	
	@Inject
	private MusicScheduler ms;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			
			Schedule schedule = pm.get().findById(Schedule.class, Integer.parseInt(request.getParameter("scheduleId")));
			boolean wasEnabled = schedule.isEnabled();
			schedule.setEnabled(request.getParameter("enable") != null);
			pm.get().update(schedule);
			
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
