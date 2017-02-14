package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.AuthenticationResponse;
import com.fiyoteam.model.User;
import com.fiyoteam.persistence.Entitymanager;
import com.fiyoteam.utils.PasswordStorage;
import com.fiyoteam.utils.PasswordStorage.CannotPerformOperationException;
import com.fiyoteam.utils.PasswordStorage.InvalidHashException;

@Path("/authentication")
public class AuthenticationService {

	private static final Logger log = LoggerFactory.getLogger(AuthenticationService.class);

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuthenticationResponse login(User user) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM User u WHERE u.email = :email");
		query.setParameter("email", user.getEmail());

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.getResultList();

		AuthenticationResponse response = new AuthenticationResponse();

		if (userList.size() > 0) {
			try {
				if (PasswordStorage.verifyPassword(user.getPassword(), userList.get(0).getPassword())) {
					response.setId(userList.get(0).getId());
					response.setEmail(userList.get(0).getEmail());
					response.setRole(userList.get(0).getRole());
					
					log.info("Logged in: " + response);
				}
			} catch (CannotPerformOperationException e) {
				response.setId(-1);
				response.setEmail("none");
				response.setRole("none");
				
				log.debug("Failed to Login: " + user.getEmail() + " - " + e);
			} catch (InvalidHashException e) {
				response.setId(-1);
				response.setEmail("none");
				response.setRole("none");
				
				log.debug("Failed to Login: " + user.getEmail() + " - " + e);
			}

		} else {
			response.setId(-1);
			response.setEmail("none");
			response.setRole("none");
		}

		return response;

	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public AuthenticationResponse register(User user) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM User u WHERE u.email = :email");
		query.setParameter("email", user.getEmail());

		@SuppressWarnings("unchecked")
		List<User> userList = (List<User>) query.getResultList();

		AuthenticationResponse response = new AuthenticationResponse();

		if (userList.size() > 0) {
			// the email is already in use
			log.info("Registration attempt - email already in use: " + user.getEmail());
			response.setId(-1);
			return response;

		} else {
			user.setRole("user");
			try {
				user.setPassword(PasswordStorage.createHash(user.getPassword()));
				log.info("Password successfully hashed to the user: " + user.getEmail());
			} catch (CannotPerformOperationException e) {
				log.debug("Password hashing failed to the user: " + user.getEmail() + " - " + e);
			}

			// store the new user
			em.getTransaction().begin();
			em.persist(user);
			em.flush();
			em.getTransaction().commit();

			response.setId(user.getId());
			response.setEmail(user.getEmail());
			response.setRole(user.getRole());

			log.info("Successful registration : " + response);
			
			return response;
		}
	}

}
