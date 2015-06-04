package org.westcoasthonorcamp.mas.web.music;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.data.Music;
import org.westcoasthonorcamp.mas.data.Schedule;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;
import org.westcoasthonorcamp.mas.server.MusicScheduler;

/**
 * Servlet implementation class Delete
 */
@WebServlet({ "/music/delete"})
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
			
			Music music = pm.findById(Music.class, Integer.parseInt(request.getParameter("musicId")));
			Files.deleteIfExists(Paths.get(music.getLocation()));
			for(Schedule schedule : music.getSchedules())
			{
				ms.unregisterMusic(schedule.getId());
			}
			pm.delete(music);
			response.sendRedirect(getServletContext().getContextPath() + "/home");
			
		}
		catch(NumberFormatException | NullPointerException e)
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Music with that id was not found");
		}
		
	}

}
