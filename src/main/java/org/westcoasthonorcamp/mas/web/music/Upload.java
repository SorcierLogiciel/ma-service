package org.westcoasthonorcamp.mas.web.music;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.westcoasthonorcamp.mas.data.Music;
import org.westcoasthonorcamp.mas.persistence.PersistenceManager;

/**
 * Servlet implementation class Upload
 */
@WebServlet({ "/music/upload"})
@MultipartConfig
public class Upload extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	
	private static final Path UPLOAD_DIRECTORY = Paths.get("").toAbsolutePath().getParent().resolve("uploads");
	private static final List<String> EXTENSIONS = new ArrayList<>();
	
	@Inject
	private Instance<PersistenceManager> pm;
	
	@Override
	public void init(ServletConfig config) throws ServletException
	{
		super.init(config);
		EXTENSIONS.add("mp3");
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getRequestDispatcher("/WEB-INF/music/upload.jsp").forward(request, response);
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{

		Part filePart = request.getPart("file");
		String fileName = filePart.getSubmittedFileName();
		if(fileName != null && fileName.trim().length() > 0 && EXTENSIONS.contains(fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase()))
		{
			
		    fileName = fileName.substring(fileName.lastIndexOf('\\') + 1);
		    InputStream fileContent = filePart.getInputStream();
		    
		    if(!Files.exists(UPLOAD_DIRECTORY))
		    {
		    	Files.createDirectory(UPLOAD_DIRECTORY);
		    }
		    
		    Path uploadFile = UPLOAD_DIRECTORY.resolve(fileName);
		    Files.copy(fileContent, uploadFile, StandardCopyOption.REPLACE_EXISTING);
		    String name = request.getParameter("name");
		    
		    Music music = new Music();
		    music.setLocation(uploadFile.toString());
		    music.setName(name != null && name.trim().length() > 0 ? name : fileName);
		    pm.get().create(music);
		    
		    request.setAttribute("musicId", music.getId());
		    request.setAttribute("name", music.getName());
		    request.getRequestDispatcher("/WEB-INF/music/uploadSuccess.jsp").forward(request, response);
		    
		}
		else
		{
			doGet(request, response);
		}
	    
	}

}
