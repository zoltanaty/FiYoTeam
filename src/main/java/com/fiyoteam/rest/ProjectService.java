package com.fiyoteam.rest;

import java.util.ArrayList;
import java.util.Collections;
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

import com.fiyoteam.model.Project;
import com.fiyoteam.model.ProjectSkill;
import com.fiyoteam.model.response.UserProjectResponse;
import com.fiyoteam.model.response.UserSkillResponse.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.fiyoteam.utils.ProjectDateSorter;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/project")
public class ProjectService {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAllProjects() {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Project p WHERE p.status = :status");
		query.setParameter("status", "active");

		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) query.getResultList();
		Collections.sort(projectList, new ProjectDateSorter());
		
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

}
