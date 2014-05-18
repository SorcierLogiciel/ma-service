package org.wchc.mas.web;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wchc.mas.data.Schedule;
import org.wchc.mas.persistence.EntityManagerAdapter;

/**
 * Servlet implementation class Index
 */
@WebServlet({ "/home", "/" })
public class Home extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManagerAdapter ema;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{		
		request.setAttribute("schedules", ema.findAll(Schedule.class));
		request.getRequestDispatcher("/WEB-INF/home.jsp").forward(request, response);		
	}

}