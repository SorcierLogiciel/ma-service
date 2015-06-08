package org.westcoasthonorcamp.ma.service.rest;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.westcoasthonorcamp.ma.service.data.Music;
import org.westcoasthonorcamp.ma.service.persistence.PersistenceManager;

@Stateless
@Path("music")
public class MusicService
{
	
	@Inject
	private PersistenceManager pm;
	
	@GET
	@Produces(MediaType.APPLICATION_XML)
	public List<Music> getMusic()
	{
		return pm.findAll(Music.class);
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_XML)
	public Music getMusic(@PathParam("id") int id)
	{
		return pm.findById(Music.class, id);
	}
	
	@GET
	@Path("{id}/file")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response getMusicFile(@PathParam("id") int id)
	{
		
		Music music = pm.findById(Music.class, id);
		
		if(music == null || music.getLocation() == null || music.getLocation().isEmpty())
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		File file = Paths.get(music.getLocation()).toFile();
		
		if(file == null || !file.exists())
		{
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(file).header("Content-Disposition", "attachment; filename=" + file.getName()).build();
		
	}
	
}
