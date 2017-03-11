package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.User;
import com.fiyoteam.model.UserLanguage;
import com.fiyoteam.model.UserSkill;
import com.fiyoteam.model.response.UserLanguageResponse;
import com.fiyoteam.model.response.UserLanguageResponse.Language;
import com.fiyoteam.model.response.UserSkillResponse;
import com.fiyoteam.model.response.UserSkillResponse.Skill;
import com.fiyoteam.persistence.Entitymanager;

@Path("/user")
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	/*
	 * 	Services of the User
	 */

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM User");

		@SuppressWarnings("unchecked")
		List<User> personList = (List<User>) query.getResultList();

		log.info("Returned all of the Users");

		return personList;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		log.info("Returned the User with id: " + id);

		return user;
	}
	
	/*
	 * 	Services for languages of the User
	 */

	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> getUserLanguage(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		UserLanguageResponse userLanguageResponse = new UserLanguageResponse();
		for (UserLanguage userLanguage : user.getUserLanguage()) {
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(),
					userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}

		log.info("Returned languages of the User with id: " + id);

		return userLanguageResponse.getLanguages();
	}

	@POST
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> updateUserLanguage(@PathParam("id") Integer id,
			List<UserLanguageResponse.Language> languages) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		for (UserLanguage userLanguage : user.getUserLanguage()) {
			for (UserLanguageResponse.Language language : languages) {
				if (userLanguage.getLanguage().getId() == language.getId()) {
					userLanguage.setLevel(language.getLevel());
				}
			}
		}

		em.getTransaction().begin();
		em.merge(user);
		em.flush();
		em.getTransaction().commit();

		log.info("Languages Updated of the User with id: " + id);
		
		return getUserLanguage(id);
	}

	@PUT
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> addUserLanguage(@PathParam("id") Integer id, Language newLanguage) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		User user = em.find(User.class, id);
		com.fiyoteam.model.Language language = em.find(com.fiyoteam.model.Language.class, newLanguage.getId());
		
		UserLanguage newUserLanguage = new UserLanguage();
		newUserLanguage.setUser(user);
		newUserLanguage.setLanguage(language);
		newUserLanguage.setLevel(newLanguage.getLevel());
		
		em.getTransaction().begin();
		em.persist(newUserLanguage);
		em.flush();
		em.getTransaction().commit();
		
		user.getUserLanguage().add(newUserLanguage);
		
		log.info("New language added to the User with id: " + id);

		return getUserLanguage(id);
	}

	@DELETE
	@Path("/languages/{id}/{language}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> deleteUserLanguage(@PathParam("id") Integer id,
			@PathParam("language") Integer language) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		for (UserLanguage userLanguage : user.getUserLanguage()) {
			if (userLanguage.getLanguage().getId() == language) {
				user.getUserLanguage().remove(userLanguage);
				
				em.getTransaction().begin();
				em.merge(user);
				em.flush();
				em.remove(userLanguage);
				em.flush();
				em.getTransaction().commit();
				break;
			}
		}
		
		log.info("Language deleted of the User with id: " + id);
		
		return getUserLanguage(id);
	}
	
	/*
	 * 	Services for skills of the User
	 */
	
	@GET
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> getUserSkill(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		UserSkillResponse userSkillResponse = new UserSkillResponse();
		for (UserSkill userSkill : user.getUserSkill()) {
			userSkillResponse.addSkill(userSkill.getSkill().getId(), userSkill.getSkill().getSkill(), userSkill.getLevel());
		}

		log.info("Returned languages of the User with id: " + id);

		return userSkillResponse.getSkills();
	}

	@POST
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> updateUserSkill(@PathParam("id") Integer id,
			List<UserSkillResponse.Skill> skills) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		for (UserSkill userSkill : user.getUserSkill()) {
			for (UserSkillResponse.Skill skill : skills) {
				if (userSkill.getSkill().getId() == skill.getId()) {
					userSkill.setLevel(skill.getLevel());
				}
			}
		}

		em.getTransaction().begin();
		em.merge(user);
		em.flush();
		em.getTransaction().commit();

		log.info("Skills Updated of the User with id: " + id);

		return getUserSkill(id);
	}

	@PUT
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> addUserSkill(@PathParam("id") Integer id, Skill newSkill) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		User user = em.find(User.class, id);
		com.fiyoteam.model.Skill skill = em.find(com.fiyoteam.model.Skill.class, newSkill.getId());
		
		UserSkill newUserSkill = new UserSkill();
		newUserSkill.setUser(user);
		newUserSkill.setSkill(skill);
		newUserSkill.setLevel(newSkill.getLevel());
		
		em.getTransaction().begin();
		em.persist(newUserSkill);
		em.flush();
		em.getTransaction().commit();
		
		user.getUserSkill().add(newUserSkill);
		
		log.info("New skill added to the User with id: " + id);

		return getUserSkill(id);
	}

	@DELETE
	@Path("/skills/{id}/{skill}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> deleteUserSkill(@PathParam("id") Integer id,
			@PathParam("skill") Integer skill) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		for (UserSkill userSkill: user.getUserSkill()) {
			if (userSkill.getSkill().getId() == skill) {
				user.getUserSkill().remove(userSkill);
				
				em.getTransaction().begin();
				em.merge(user);
				em.flush();
				em.remove(userSkill);
				em.flush();
				em.getTransaction().commit();
				break;
			}
		}
		
		log.info("Skill deleted of the User with id: " + id);
		
		return getUserSkill(id);
	}
}
