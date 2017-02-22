package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fiyoteam.model.User;
import com.fiyoteam.persistence.Entitymanager;

@Path("/user")
public class UserService {
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllPerson() {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM User");
		
	    @SuppressWarnings("unchecked")
		List<User> personList = (List<User>) query.getResultList();
	    return personList;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getPerson(@PathParam("id") Integer id) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		return user;
	}
}