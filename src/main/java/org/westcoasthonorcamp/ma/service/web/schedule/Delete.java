package org.westcoasthonorcamp.ma.service.web.schedule;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.ma.common.data.Music;
import org.westcoasthonorcamp.ma.common.data.Schedule;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;
import org.westcoasthonorcamp.ma.service.server.MusicScheduler;

/**
 * Servlet implementation class Delete
 */
@WebServlet({ "/schedule/delete"})
public class Delete extends HttpServlet
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
			ms.unregisterMusic(schedule.getId());
			Music music = schedule.getMusic();
			music.getSchedules().remove(schedule);
			pm.update(music);
			response.sendRedirect(getServletContext().getContextPath() + "/music?musicId=" + music.getId());
			
		}
		catch(NumberFormatException | NullPointerException e)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "A Schedule with that id was not found");
		}
		
	}

}
