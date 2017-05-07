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
import com.fiyoteam.model.DTO.UserProjectDTO;
import com.fiyoteam.model.DTO.UserSkillDTO.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/project")
public class ProjectService {
	
	private static final Logger log = LoggerFactory.getLogger(ProjectService.class);
	private final int NUMBER_OF_RECORDS_PER_PAGE = 6;
	
	@GET
	@Path("/{pageNumber}/{searchCriteria : (.*)?}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAllProjects(@PathParam("pageNumber") Integer pageNumber, @PathParam("searchCriteria") String searchCriteria) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Query query;
		if((null != searchCriteria) && (!"".equals(searchCriteria))){
			query = em.createQuery("FROM Project p WHERE p.status = :status AND ((SELECT COUNT(*) FROM ProjectSkill ps  WHERE ps.project = p AND ps.skill.skill LIKE :searchCritearia) > 0 OR p.description LIKE :searchCritearia)  ORDER BY p.createdAt DESC");
			query.setParameter("status", "active");
			query.setParameter("searchCritearia", "%" + searchCriteria + "%");
		}else{
			query = em.createQuery("FROM Project p WHERE p.status = :status ORDER BY p.createdAt DESC");
			query.setParameter("status", "active");
		}
		
		query.setFirstResult(NUMBER_OF_RECORDS_PER_PAGE * pageNumber);
		query.setMaxResults(NUMBER_OF_RECORDS_PER_PAGE);
				
		@SuppressWarnings("unchecked")
		List<Project> projectList = (List<Project>) query.getResultList();
		
		List<UserProjectDTO> userProjectResponseList = new ArrayList<>();
		for(Project project : projectList){
			UserProjectDTO upr = new UserProjectDTO();
			
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
	@Path("/nrpages/{searchCriteria : (.*)?}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getNrOfPages(@PathParam("searchCriteria") String searchCriteria) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Query query;
		if((null != searchCriteria) && (!"".equals(searchCriteria))){
			query = em.createQuery("SELECT COUNT(*) FROM Project p WHERE p.status = :status AND ((SELECT COUNT(*) FROM ProjectSkill ps  WHERE ps.project = p AND ps.skill.skill LIKE :searchCritearia) > 0 OR p.description LIKE :searchCritearia)");
			query.setParameter("status", "active");
			query.setParameter("searchCritearia", "%" + searchCriteria + "%");
		}else{
			query = em.createQuery("SELECT COUNT(*) FROM Project p WHERE p.status = :status");
			query.setParameter("status", "active");
		}

		Long nrOfRows = (Long) query.getSingleResult();
		
		Long nrOfPages = 0L;
		if(nrOfRows%NUMBER_OF_RECORDS_PER_PAGE == 0){
			nrOfPages = nrOfRows/NUMBER_OF_RECORDS_PER_PAGE;
		}else{
			nrOfPages = nrOfRows/NUMBER_OF_RECORDS_PER_PAGE + 1;
		}
		
		Entitymanager.closeEntityManager();
		
		List<Long> pages = new ArrayList<>();
		for(Long i = 0L; i<nrOfPages; ++i){
			pages.add(i);
		}
		
		log.info("Returned the nr of pages with Projects");
		
		return Response.ok(pages).build();
	}

}
