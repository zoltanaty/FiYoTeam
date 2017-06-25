package com.fiyoteam.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.ProjectSkill;
import com.fiyoteam.model.Skill;
import com.fiyoteam.model.UserSkill;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/skill")
public class SkillService {

	private static final Logger log = LoggerFactory.getLogger(SkillService.class);

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAllLanguages() {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Skill s ORDER BY s.skill");

		@SuppressWarnings("unchecked")
		List<Skill> skillList = (List<Skill>) query.getResultList();

		log.info("Returned all of the Skills");

		Entitymanager.closeEntityManager();
		return Response.ok(skillList).build();
	}
	
	@GET
	@Path("/{newSkill}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response addNewSkill(@PathParam("newSkill") String newSkill) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Skill skill = new  Skill(0, newSkill, new ArrayList<UserSkill>(), new ArrayList<ProjectSkill>());
		
		em.getTransaction().begin();
		em.persist(skill);
		em.flush();
		em.getTransaction().commit();

		log.info("New skill added by Admin");

		Entitymanager.closeEntityManager();
		return getAllLanguages();

	}
	
	@DELETE
	@Path("/{skillId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response deleteSkill(@PathParam("skillId") Integer skillId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Skill toDelete = em.find(Skill.class, skillId);
		
		em.getTransaction().begin();
		em.remove(toDelete);
		em.flush();
		em.getTransaction().commit();

		log.info("Skill removed by Admin");

		Entitymanager.closeEntityManager();
		return getAllLanguages();

	}

}