package com.fiyoteam.rest;

import java.util.List;
import java.util.ListIterator;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.User;
import com.fiyoteam.model.UserLanguage;
import com.fiyoteam.model.response.UserLanguageResponse;
import com.fiyoteam.persistence.Entitymanager;

@Path("/user")
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllPerson() {
		
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
	public User getPerson(@PathParam("id") Integer id) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);
		
		log.info("Returned the User with id: " + id);

		return user;
	}
	
	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> getUserLanguage(@PathParam("id") Integer id) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);
		
		UserLanguageResponse userLanguageResponse = new UserLanguageResponse();
		for(UserLanguage  userLanguage : user.getUserLanguage()){
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(), userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}
		
		log.info("Returned languages of the User with id: " + id);

		return userLanguageResponse.getLanguages();
	}
	
	@POST
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> updateUserLanguage(@PathParam("id") Integer id, List<UserLanguageResponse.Language> languages) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);
		
		for(UserLanguage userLanguage : user.getUserLanguage()){
			for(UserLanguageResponse.Language language : languages){ 
				if(userLanguage.getLanguage().getId() == language.getId()){
					userLanguage.setLevel(language.getLevel());
				}
			}
		}
		
		em.getTransaction().begin();
		em.merge(user);
		em.flush();
		em.getTransaction().commit();
		
		
		UserLanguageResponse userLanguageResponse = new UserLanguageResponse();
		for(UserLanguage  userLanguage : user.getUserLanguage()){
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(), userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}
		
		log.info("Languages Updated of the User with id: " + id);

		return userLanguageResponse.getLanguages();
	}
	
	@DELETE
	@Path("/languages/{id}/{language}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> deleteUserLanguage(@PathParam("id") Integer id, @PathParam("language") Integer language) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);
		
		for(UserLanguage userLanguage : user.getUserLanguage()){
			if(userLanguage.getLanguage().getId() == language){
				em.getTransaction().begin();
				em.remove(userLanguage);
				em.flush();
				em.getTransaction().commit();
			}
		}
		
		ListIterator<UserLanguage> listIterator = user.getUserLanguage().listIterator();
		while(listIterator.hasNext()){
			UserLanguage  userLanguage = listIterator.next();
			if(userLanguage.getLanguage().getId() == language){
				listIterator.remove();
			}
		}
		
		UserLanguageResponse userLanguageResponse = new UserLanguageResponse();
		for(UserLanguage  userLanguage : user.getUserLanguage()){
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(), userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}
		
		log.info("Language:" + language + " deleted of the User with id: " + id);

		return userLanguageResponse.getLanguages();
	}	
}
