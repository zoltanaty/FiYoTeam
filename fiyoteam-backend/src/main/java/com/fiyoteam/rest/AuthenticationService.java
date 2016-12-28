package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fiyoteam.model.LoginResponse;
import com.fiyoteam.model.User;
import com.fiyoteam.persistence.Entitymanager;

@Path("/authentication")
public class AuthenticationService {
	
	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public LoginResponse login(User user) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM User u WHERE u.email = :email AND u.password = :password");
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		
	    @SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.getResultList();
	    
	    LoginResponse response = new LoginResponse();
	    
	    if(userList.size() > 0){
	    	response.setId(userList.get(0).getId());
	    	response.setEmail(userList.get(0).getEmail());
	    	response.setRole(userList.get(0).getRole());
	    }else{
	    	response.setId(-1);
	    	response.setEmail(userList.get(0).getEmail());
	    	response.setRole("none");
	    }
	    
	    return response;
	    
	}

}
