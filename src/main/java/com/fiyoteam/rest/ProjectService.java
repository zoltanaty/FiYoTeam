package com.fiyoteam.rest;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.Project;
import com.fiyoteam.model.ProjectSkill;
import com.fiyoteam.model.response.UserProjectResponse;
import com.fiyoteam.model.response.UserSkillResponse.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/project")
public class ProjectService {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
	private final int NUMBER_OF_RECORDS_PER_PAGE = 6;
	
	@GET
	@Path("/{pageNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAllProjects(@PathParam("pageNumber") Integer pageNumber) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Project p WHERE p.status = :status ORDER BY p.createdAt DESC");
		query.setParameter("status", "active");
		query.setFirstResult(NUMBER_OF_RECORDS_PER_PAGE * pageNumber);
		query.setMaxResults(NUMBER_OF_RECORDS_PER_PAGE);
		
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) query.getResultList();
		
		List<UserProjectResponse> userProjectResponseList = new ArrayList<>();
		for(Project project : projectList){
			UserProjectResponse upr = new UserProjectResponse();
			
			List<Skill> projectSkillList = new ArrayList<>();

			for (ProjectSkill projectSkill : project.getProjectSkill()) {
				Skill skill = new Skill();
				skill.setId(projectSkill.getSkill().getId());
				skill.setSkill(projectSkill.getSkill().getSkill());
				projectSkillList.add(skill);
			}
			
			upr.setSkills(projectSkillList);
			upr.setProject(project);
			upr.setAuthorId(project.getUser().getId());
			upr.setAuthorName(project.getUser().getFirstName() + " " + project.getUser().getLastName());
			upr.setCreatedAt(project.getCreatedAt());
			
			userProjectResponseList.add(upr);
		}

		log.info("Returned all of the Projects");

		Entitymanager.closeEntityManager();
		return Response.ok(userProjectResponseList).build();
	}
	
	@GET
	@Path("/nrpages")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getNrOfPages() {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("SELECT COUNT(*) FROM Project p  WHERE p.status = :status");
		query.setParameter("status", "active");
		Long nrOfRows = (Long) query.getSingleResult();
		Long nrOfPages = nrOfRows/NUMBER_OF_RECORDS_PER_PAGE;
		Entitymanager.closeEntityManager();
		
		List<Long> pages = new ArrayList<>();
		for(Long i = 0L; i<nrOfPages; ++i){
			pages.add(i);
		}
		
		log.info("Returned the nr of Projects");
		
		return Response.ok(pages).build();
	}

}
