package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.response.UserSkillResponse.Skill;
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
		Query query = em.createQuery("FROM Skill");

		@SuppressWarnings("unchecked")
		List<Skill> skillList = (List<Skill>) query.getResultList();

		log.info("Returned all of the Skills");

		Entitymanager.closeEntityManager();
		return Response.ok(skillList).build();
	}

}