package org.westcoasthonorcamp.ma.service.web.control;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.ma.service.server.MusicScheduler;

/**
 * Servlet implementation class StopMusic
 */
@WebServlet({ "/control/stopmusic"})
public class StopMusic extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private MusicScheduler ms;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		ms.stopMusic();
		response.sendRedirect(getServletContext().getContextPath() + "/control");
	}

}
