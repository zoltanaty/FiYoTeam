package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.AccountActivation;
import com.fiyoteam.model.User;
import com.fiyoteam.model.response.AuthenticationResponse;
import com.fiyoteam.persistence.Entitymanager;
import com.fiyoteam.utils.Email;
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
		boolean failedToLogin = false;

		if (userList.size() > 0) {
			try {
				if (PasswordStorage.verifyPassword(user.getPassword(), userList.get(0).getPassword())) {
					response.setId(userList.get(0).getId());
					response.setEmail(userList.get(0).getEmail());
					response.setFirstName(userList.get(0).getFirstName());
					response.setLastName(userList.get(0).getLastName());
					response.setRole(userList.get(0).getRole());

					log.info("Logged in: " + response);
				} else {
					failedToLogin = true;

					log.debug("Failed to Login: " + user.getEmail());
				}
			} catch (CannotPerformOperationException | InvalidHashException e) {
				failedToLogin = true;

				log.debug("Failed to Login: " + user.getEmail() + " - " + e);
			}

		} else {
			failedToLogin = true;

			log.debug("Login attempt: " + user);
		}

		if (failedToLogin) {
			response.setId(-1);
			response.setEmail("none");
			response.setRole("none");
		}
		
		Entitymanager.closeEntityManager();
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
				response.setId(-1);
				return response;
			}

			AccountActivation accountActivation = new AccountActivation();
			accountActivation.setActivationCode(RandomStringUtils.randomAlphanumeric(8));
			accountActivation.setActivated(false);

			try {
				// store the new user
				em.getTransaction().begin();
				em.persist(user);
				em.flush();
				accountActivation.setIdUser(user.getId());
				em.persist(accountActivation);
				em.flush();
				em.getTransaction().commit();

				response.setId(user.getId());
				response.setEmail(user.getEmail());
				response.setRole(user.getRole());

				log.info("Successful registration : " + response);
			} catch (Exception e) {
				em.getTransaction().rollback();
				log.error("Error occured while registering the user with id: " + user.getId() + " - "
						+ e.getStackTrace());
				response.setId(-1);
				return response;
			}

			Email activationEmail = new Email();
			activationEmail.send("zoltanaty@gmail.com", "FiYoTeam Account Activation",
					activationEmail.composeActivationEmail(user.getFirstName(), accountActivation.getActivationCode()));

			Entitymanager.closeEntityManager();	
			return response;
		}
	}

}
