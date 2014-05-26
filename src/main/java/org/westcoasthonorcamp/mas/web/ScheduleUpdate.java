package org.westcoasthonorcamp.mas.web;

import java.io.IOException;
import java.sql.Timestamp;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.data.Alarm;
import org.westcoasthonorcamp.mas.data.Schedule;
import org.westcoasthonorcamp.mas.enums.ScheduleScope;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;

/**
 * Servlet implementation class ScheduleUpdate
 */
@WebServlet({ "/schedule"})
@MultipartConfig
public class ScheduleUpdate extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersistenceManager ema;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		request.setAttribute("alarms", ema.findAll(Alarm.class));
		request.setAttribute("scopes", ScheduleScope.values());
		request.getRequestDispatcher("/WEB-INF/schedule.jsp").forward(request, response);
		
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		Alarm alarm = ema.findById(Alarm.class, Integer.parseInt(request.getParameter("alarm")));
		
		Schedule schedule = new Schedule();
		schedule.setScheduleScope(Enum.valueOf(ScheduleScope.class, request.getParameter("scheduleScope")));
		schedule.setCreationTime(new Timestamp(Long.parseLong(request.getParameter("creationTime"))));
		schedule.setRepeatScale(Integer.parseInt(request.getParameter("repeatScale")));
	    
		alarm.getSchedules().add(schedule);
		ema.update(alarm);
		
		response.getWriter().print(String.format("%s updated successfully!", alarm.getName()));
		
	}

}
