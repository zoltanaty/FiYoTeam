package com.fiyoteam.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fiyoteam.model.Person;

@Path("/hello")
public class HelloWorldService {

	@GET
	@Path("/{first}/{last}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getMsg(@PathParam("first") String first, @PathParam("last") String last) {
		
		Person person = new Person();
		person.setFirstName(first);
		person.setLastName(last);
		person.setAge(21);

		return person;
	}

}