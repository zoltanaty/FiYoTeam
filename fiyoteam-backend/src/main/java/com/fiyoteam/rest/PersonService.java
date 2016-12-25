package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fiyoteam.model.Person;
import com.fiyoteam.persistence.Entitymanager;

@Path("/person")
public class PersonService {
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Person> getAllPerson() {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Person");
		
	    List<Person> personList = (List<Person>) query.getResultList();
	    return personList;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("id") Integer id) {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Person person = em.find(Person.class, id);

		return person;
	}
}