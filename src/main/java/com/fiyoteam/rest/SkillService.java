package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.response.UserSkillResponse.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.fiyoteam.utils.Authentication;

@Path("/skill")
public class SkillService {
	
	private static final Logger log = LoggerFactory.getLogger(SkillService.class);
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllLanguages(@Context HttpHeaders headers) {
		
		if (headers.getRequestHeader("authorization") != null && headers.getRequestHeader("identifier") != null) {
			String token = headers.getRequestHeader("authorization").get(0);
			Integer identifier = Integer.parseInt(headers.getRequestHeader("identifier").get(0));

			if (Authentication.isTokenAllowed(token, identifier)) {

				EntityManager em = Entitymanager.getEntityManagerInstance();
				Query query = em.createQuery("FROM Skill");
				
			    @SuppressWarnings("unchecked")
				List<Skill> skillList = (List<Skill>) query.getResultList();
			    
			    log.info("Returned all of the Skills");
			    
			    Entitymanager.closeEntityManager();
			    return Response.ok(skillList).build();

			} else {
				return Response.noContent().build();
			}
		} else {
			return Response.noContent().build();
		}
		
	}

}