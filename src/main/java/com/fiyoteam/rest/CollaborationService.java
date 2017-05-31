package com.fiyoteam.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.Collaboration;
import com.fiyoteam.model.Project;
import com.fiyoteam.model.User;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/collaboration")
public class CollaborationService {
	
	private static final Logger log = LoggerFactory.getLogger(CollaborationService.class);
	
	@GET
	@Path("/{userid}/{projectid}/{ownerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response requestCollaboration(@PathParam("userid") Integer userId, @PathParam("projectid") Integer projectId, @PathParam("ownerid") Integer ownerId){
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		User user = em.find(User.class, userId);
		Project project = em.find(Project.class, projectId);
		User owner = em.find(User.class, ownerId);
		
		Collaboration collaboration = new Collaboration();
		collaboration.setUser(user);
		collaboration.setProject(project);
		collaboration.setOwner(owner);
		collaboration.setAccepted(false);
		
		Query query = em.createQuery("FROM Collaboration c WHERE c.user = :user AND c.project = :project AND c.owner = :owner");
		query.setParameter("user", user);
		query.setParameter("project", project);
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();
		
		if(resultList.size() > 0){
			log.info("Collaboration already saved: " + collaboration.toString());
			return Response.ok(-1).build();
		}
		
		try{
			em.getTransaction().begin();
			em.persist(collaboration);
			em.flush();
			em.getTransaction().commit();
			
			log.info("Collaboration request saved: " + collaboration.toString());
			
			return Response.ok(collaboration.getId()).build();
		}catch(Exception e){
			em.getTransaction().rollback();
			
			log.error("Failed to save Collaboration request.");
			
			return Response.ok(-1).build();
		}
		
	}
	
	@GET
	@Path("/requests/{projectid}/{ownerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getCollaborationRequests(@PathParam("projectid") Integer projectId, @PathParam("ownerid") Integer ownerId){
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Project project = em.find(Project.class, projectId);
		User owner = em.find(User.class, ownerId);
		
		Query query = em.createQuery("FROM Collaboration c WHERE c.project = :project AND c.owner = :owner");
		query.setParameter("project", project);
		query.setParameter("owner", owner);
		
		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();
		
		if(resultList.size() == 0){
			log.error("No Collaboration Requests for this project: " + projectId);
			return Response.ok(new ArrayList<Collaboration>()).build();
		}else{
			log.info("Returned the Collaboration Requests for this project: " + projectId);
			return Response.ok(resultList).build();
		}	
	}
	
	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response updateCollaborationRequests(List<Collaboration> collaborations){
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		try{
			em.getTransaction().begin();
			for(Collaboration collaboration : collaborations){
				em.merge(collaboration);
				em.flush();
			}
			em.getTransaction().commit();
			
			log.info("Collaborations updated");
			return Response.ok(collaborations).build();
		}catch(Exception e){
			em.getTransaction().rollback();
			log.error("Failed to Update Collaborations - " + e);
			return Response.ok(collaborations).build();
		}
	}

}
