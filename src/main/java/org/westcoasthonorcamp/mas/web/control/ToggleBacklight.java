package org.westcoasthonorcamp.mas.web.control;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.mas.server.OverrideController;

/**
 * Servlet implementation class ToggleBacklight
 */
@WebServlet({ "/control/backlight"})
public class ToggleBacklight extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OverrideController oc;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		oc.toggleBacklight();
		response.sendRedirect(getServletContext().getContextPath() + "/control");
	}

}
