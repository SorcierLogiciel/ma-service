package org.westcoasthonorcamp.mas.web;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.westcoasthonorcamp.mas.data.Alarm;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;

/**
 * Servlet implementation class Upload
 */
@WebServlet({ "/upload"})
@MultipartConfig
public class Upload extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private PersistenceManager ema;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/WEB-INF/upload.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		Part filePart = request.getPart("file"); // Retrieves <input type="file" name="file">
	    String fileName = filePart.getSubmittedFileName();
	    InputStream fileContent = filePart.getInputStream();
	    
	    Path uploadDirectory = Paths.get("").toAbsolutePath().getParent().resolve("uploads");
	    if(!Files.exists(uploadDirectory))
	    {
	    	Files.createDirectory(uploadDirectory);
	    }
	    
	    Path uploadFile = uploadDirectory.resolve(fileName);
	    Files.copy(fileContent, uploadFile, StandardCopyOption.REPLACE_EXISTING);
	    
	    Alarm alarm = new Alarm();
	    alarm.setMusicLocation(uploadFile.toString());
	    alarm.setName(fileName);
	    ema.create(alarm);
	    
	    response.getWriter().print(String.format("%s uploaded successfully!", fileName));
	    
	}

}
