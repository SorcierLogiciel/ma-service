package org.westcoasthonorcamp.ma.service.web.control;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.westcoasthonorcamp.ma.service.server.OverrideController;

/**
 * Servlet implementation class ToggleOverride
 */
@WebServlet({ "/control/override"})
public class ToggleOverride extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private OverrideController oc;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		oc.toggleOverride();
		response.sendRedirect(getServletContext().getContextPath() + "/control");
	}

}
