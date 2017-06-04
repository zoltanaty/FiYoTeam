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
import com.fiyoteam.model.ProjectSkill;
import com.fiyoteam.model.User;
import com.fiyoteam.model.DTO.CollaboratorsForProject;
import com.fiyoteam.model.DTO.UserProjectDTO;
import com.fiyoteam.model.DTO.UserSkillDTO.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/collaboration")
public class CollaborationService {

	private static final Logger log = LoggerFactory.getLogger(CollaborationService.class);

	@GET
	@Path("/{userid}/{projectid}/{ownerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response requestCollaboration(@PathParam("userid") Integer userId, @PathParam("projectid") Integer projectId,
			@PathParam("ownerid") Integer ownerId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		User user = em.find(User.class, userId);
		Project project = em.find(Project.class, projectId);
		User owner = em.find(User.class, ownerId);

		Collaboration collaboration = new Collaboration();
		collaboration.setUser(user);
		collaboration.setProject(project);
		collaboration.setOwner(owner);
		collaboration.setAccepted(false);

		Query query = em
				.createQuery("FROM Collaboration c WHERE c.user = :user AND c.project = :project AND c.owner = :owner");
		query.setParameter("user", user);
		query.setParameter("project", project);
		query.setParameter("owner", owner);

		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();

		if (resultList.size() > 0) {
			log.info("Collaboration already saved: " + collaboration.toString());
			return Response.ok(-1).build();
		}

		try {
			em.getTransaction().begin();
			em.persist(collaboration);
			em.flush();
			em.getTransaction().commit();

			log.info("Collaboration request saved: " + collaboration.toString());

			return Response.ok(collaboration.getId()).build();
		} catch (Exception e) {
			em.getTransaction().rollback();

			log.error("Failed to save Collaboration request.");

			return Response.ok(-1).build();
		}

	}

	@GET
	@Path("/requests/{projectid}/{ownerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getCollaborationRequests(@PathParam("projectid") Integer projectId,
			@PathParam("ownerid") Integer ownerId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		Project project = em.find(Project.class, projectId);
		User owner = em.find(User.class, ownerId);

		Query query = em.createQuery("FROM Collaboration c WHERE c.project = :project AND c.owner = :owner");
		query.setParameter("project", project);
		query.setParameter("owner", owner);

		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();

		if (resultList.size() == 0) {
			log.error("No Collaboration Requests for this project: " + projectId);
			return Response.ok(new ArrayList<Collaboration>()).build();
		} else {
			log.info("Returned the Collaboration Requests for this project: " + projectId);
			return Response.ok(resultList).build();
		}
	}

	@GET
	@Path("/projects/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAppliedProjects(@PathParam("userid") Integer userId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		User user = em.find(User.class, userId);

		Query query = em.createQuery("FROM Collaboration c WHERE c.user = :user");
		query.setParameter("user", user);

		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();

		List<UserProjectDTO> appliedProjectsList = new ArrayList<>();
		for (Collaboration collaboration : resultList) {
			UserProjectDTO upr = new UserProjectDTO();

			List<Skill> projectSkillList = new ArrayList<>();

			for (ProjectSkill projectSkill : collaboration.getProject().getProjectSkill()) {
				Skill skill = new Skill();
				skill.setId(projectSkill.getSkill().getId());
				skill.setSkill(projectSkill.getSkill().getSkill());
				projectSkillList.add(skill);
			}

			upr.setSkills(projectSkillList);
			upr.setProject(collaboration.getProject());
			upr.setAuthorId(collaboration.getProject().getUser().getId());
			upr.setAuthorName(collaboration.getProject().getUser().getFirstName() + " "
					+ collaboration.getProject().getUser().getLastName());
			upr.setCreatedAt(collaboration.getProject().getCreatedAt());

			appliedProjectsList.add(upr);
		}

		log.info("Returned all of the Applied Projects of the user: " + userId);

		Entitymanager.closeEntityManager();

		if (appliedProjectsList.size() == 0) {
			log.info("No Applied Projects for the user: " + userId);
			return Response.ok(new ArrayList<Collaboration>()).build();
		} else {
			log.info("Returned all of the Applied Projects of the user: " + userId);
			return Response.ok(appliedProjectsList).build();
		}
	}

	@GET
	@Path("/projectswithcollaborators/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getUsersProjectsWithCollaborators(@PathParam("userid") Integer userId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, userId);

		Query query = em.createQuery("FROM Project p WHERE p.user = :user");
		query.setParameter("user", user);

		@SuppressWarnings("unchecked")
		ArrayList<Project> myProjects = (ArrayList<Project>) query.getResultList();

		ArrayList<CollaboratorsForProject> collaboratorsForProject = new ArrayList<>();

		if (myProjects.size() > 0) {
			for (Project project : myProjects) {

				Query query1 = em.createQuery("FROM Collaboration c WHERE c.owner = :user AND c.project = :project AND accepted = true");
				query1.setParameter("user", user);
				query1.setParameter("project", project);

				@SuppressWarnings("unchecked")
				ArrayList<Collaboration> collaborationsForProject = (ArrayList<Collaboration>) query1.getResultList();

				if (collaborationsForProject.size() > 0) {
					CollaboratorsForProject projectAndCollaborators = new CollaboratorsForProject();
					projectAndCollaborators.setProject(project);

					ArrayList<User> collaborators = new ArrayList<>();
					for (Collaboration collaboration : collaborationsForProject) {
						collaborators.add(collaboration.getUser());
					}
					projectAndCollaborators.setCollaborators(collaborators);

					collaboratorsForProject.add(projectAndCollaborators);
				}
			}
		}

		return Response.ok(collaboratorsForProject).build();
	}

	@POST
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response updateCollaborationRequests(List<Collaboration> collaborations) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		try {
			em.getTransaction().begin();
			for (Collaboration collaboration : collaborations) {
				em.merge(collaboration);
				em.flush();
			}
			em.getTransaction().commit();

			log.info("Collaborations updated");
			return Response.ok(collaborations).build();
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Failed to Update Collaborations - " + e);
			return Response.ok(collaborations).build();
		}
	}

	public boolean isCollaborationBetweenUs(Integer userId, Integer ownerId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, userId);
		User owner = em.find(User.class, ownerId);

		Query query = em.createQuery(
				"FROM Collaboration c WHERE (c.user = :user AND c.owner = :owner AND c.accepted = true) OR (c.user = :owner AND c.owner = :user AND c.accepted = true)");
		query.setParameter("user", user);
		query.setParameter("owner", owner);

		@SuppressWarnings("unchecked")
		ArrayList<Collaboration> resultList = (ArrayList<Collaboration>) query.getResultList();

		return resultList.size() > 0;
	}

}
