package org.westcoasthonorcamp.mas.web.schedule;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.data.Music;
import org.westcoasthonorcamp.mas.data.Schedule;
import org.westcoasthonorcamp.mas.enums.ScheduleScope;
import org.westcoasthonorcamp.mas.info.StandardMusic;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;
import org.westcoasthonorcamp.mas.server.MusicScheduler;

/**
 * Servlet implementation class Create
 */
@WebServlet({ "/schedule/create"})
public class Create extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	private static final DateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd k:mm");
	
	@Inject
	private MusicScheduler ms;
	
	@Inject
	private PersistenceManager pm;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setAttribute("musics", pm.findAll(Music.class));
		request.setAttribute("scopes", ScheduleScope.values());
		request.getRequestDispatcher("/WEB-INF/schedule/create.jsp").forward(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		try
		{
			
			Music music = pm.findById(Music.class, Integer.parseInt(request.getParameter("selectedMusic")));
			Schedule schedule = new Schedule();
			schedule.setName(request.getParameter("name"));
			schedule.setScheduleScope(Enum.valueOf(ScheduleScope.class, request.getParameter("selectedScope")));
			schedule.setCreationTime(new Timestamp(DATE_TIME_FORMAT.parse(request.getParameter("creationDate") + " " + request.getParameter("creationTime")).getTime()));
			schedule.setNextEventTime(schedule.getCreationTime());
			if(schedule.getScheduleScope() != ScheduleScope.NONE)
			{
				
				schedule.setRepeatScale(Integer.parseInt(request.getParameter("repeatScale")));
				schedule.setRepeatLimited(request.getParameter("repeatLimited") != null);
				schedule.setRepeatLimit(schedule.isRepeatLimited() ? Integer.parseInt(request.getParameter("repeatLimit")) : 0);
				
			}
			schedule.setEnabled(request.getParameter("enabled") != null);
			schedule.generateNextEventTime();
			
			if(schedule.getName() == null || schedule.getName().trim().length() == 0)
			{
				schedule.setName(music.getName());
			}
			
			if(schedule.getScheduleScope() != ScheduleScope.NONE && schedule.getRepeatScale() < 1)
			{
				throw new IllegalArgumentException("Repeat Scale must be greater than 0");
			}
			
			if(schedule.isRepeatLimited() && schedule.getRepeatLimit() < 1)
			{
				throw new IllegalArgumentException("Repeat Limit must be greater than 0");
			}
			
			if(music == null)
			{
				throw new IllegalArgumentException("Selected Music is not available");
			}
			
			pm.create(schedule);
			schedule.setMusic(music);
			pm.update(schedule);
			
			if(schedule.isEnabled())
			{
				ms.registerMusic(new StandardMusic(music.getId(), schedule.getId()));
			}

			request.setAttribute("schedule", schedule);
			request.getRequestDispatcher("/WEB-INF/schedule/createSuccess.jsp").forward(request, response);
			
		}
		catch(ParseException | IllegalArgumentException e)
		{
			doGet(request, response);
		}
		
	}

}
