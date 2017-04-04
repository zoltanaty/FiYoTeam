package com.fiyoteam.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/user")
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);

	private static final String CATALINA_BASE = System.getProperty("catalina.base");

	/*
	*	Test Service
	*/

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String testRest() {

		String person = "{ 'name':'John', 'age':31, 'city':'New York' }";

		return person;
	}

	/*
	 * Services of the User
	 */

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUser() {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		Query query = em.createQuery("FROM User u WHERE u.role != :role");
		query.setParameter("role", "admin");

		@SuppressWarnings("unchecked")
		List<User> personList = (List<User>) query.getResultList();

		log.info("Returned all of the Users");
		
		Entitymanager.closeEntityManager();

		return personList;
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		log.info("Returned the User with id: " + id);
		
		Entitymanager.closeEntityManager();

		return user;
	}

	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User updateUser(@PathParam("id") Integer id, User user) {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		User _user = em.find(User.class, id);

		_user.setFirstName(user.getFirstName());
		_user.setLastName(user.getLastName());
		_user.setCountry(user.getCountry());
		_user.setCity(user.getCity());
		_user.setEmail(user.getEmail());
		_user.setDescription(user.getDescription());

		try {
			em.getTransaction().begin();
			em.merge(_user);
			em.flush();
			em.getTransaction().commit();

			log.info("User with id: " + id + " updated.");
			Entitymanager.closeEntityManager();
			return _user;

		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while updating the user with id: " + id + " - " + e);
			Entitymanager.closeEntityManager();
			return user;
		}

	}

	@POST
	@Path("/profilepic/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("id") Integer id) {

		String fileExtension = getFileExtension(fileDetail.getFileName());
		String uploadedFileLocation = CATALINA_BASE + "/FiYoTeam/photos/user_" + id + "." + fileExtension; // +
																											// fileDetail.getFileName();

		if (writeToFile(uploadedInputStream, uploadedFileLocation) == true) {
			log.info("The User with id: " + id + " successfully uploaded his Profile Picture");
			return true;
		} else {
			log.error("The User with id: " + id + " Failed to upload his Profile Picture");
			return false;
		}
	}

	private String getFileExtension(String fileName) {
		try {
			return fileName.substring(fileName.lastIndexOf(".") + 1);
		} catch (Exception e) {
			return "";
		}
	}

	private boolean writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];

			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			log.error("Failed to write the file to the disk. - " + e);
			return false;
		}

		return true;
	}

	@GET
	@Path("/profilepic/{id}")
	@Produces("image/png")
	public File getFullImage(@PathParam("id") int id) {
		File file = null;

		try {			
			String fileName = "user_" + id;
			File dir = new File(CATALINA_BASE + "/FiYoTeam/photos");
			File[] files = dir.listFiles(new FilenameFilter() {
			    public boolean accept(File dir, String name) {
			        return name.startsWith(fileName);
			    }
			});
			
			file = files[0];

			log.info("File successfully loaded of the user: " + id);
		} catch (Exception e) {
			log.error("Failed to load the file. - " + e);
		}

		return file;
	}

	/*
	 * Services for languages of the User
	 */

	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> getUserLanguage(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		em.clear();
		User user = em.find(User.class, id);

		UserLanguageResponse userLanguageResponse = new UserLanguageResponse();
		for (UserLanguage userLanguage : user.getUserLanguage()) {
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(),
					userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}

		List<UserLanguageResponse.Language> ret = userLanguageResponse.getLanguages();

		log.info("Returned languages of the User with id: " + id + " - nr: " + ret.size());
		
		Entitymanager.closeEntityManager();

		return ret;
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

		try {
			em.getTransaction().begin();
			em.merge(user);
			em.flush();
			em.getTransaction().commit();

			log.info("Languages Updated of the User with id: " + id);
			
			Entitymanager.closeEntityManager();
			return getUserLanguage(id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while updating the User's Languages with id: " + id + " - " + e);
			
			Entitymanager.closeEntityManager();
			return languages;
		}
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

		user.getUserLanguage().add(newUserLanguage);
		language.getUserLanguage().add(newUserLanguage);

		try {
			em.getTransaction().begin();
			em.persist(newUserLanguage);
			em.flush();
			em.merge(user);
			em.flush();
			em.merge(language);
			em.flush();
			em.getTransaction().commit();

			log.info("New language added to the User with id: " + id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while adding new Language to the User with id: " + id + " - " + e);
		}
		
		Entitymanager.closeEntityManager();
		return getUserLanguage(id);
	}

	@DELETE
	@Path("/languages/{id}/{language}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserLanguageResponse.Language> deleteUserLanguage(@PathParam("id") Integer id,
			@PathParam("language") Integer language) {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		User _user = em.find(User.class, id);
		com.fiyoteam.model.Language _language = em.find(com.fiyoteam.model.Language.class, language);

		for (UserLanguage userLanguage : _user.getUserLanguage()) {
			if (userLanguage.getLanguage().getId() == language) {

				_user.getUserLanguage().remove(userLanguage);
				_language.getUserLanguage().remove(userLanguage);

				try {
					em.getTransaction().begin();
					em.merge(_user);
					em.flush();
					em.merge(_language);
					em.flush();
					em.remove(userLanguage);
					em.flush();
					em.getTransaction().commit();

					log.info("Language deleted of the User with id: " + id);
				} catch (Exception e) {
					em.getTransaction().rollback();
					log.error("Error occured while deleting Language of the User with id: " + id + " - " + e);
				}

				break;
			}
		}
		
		Entitymanager.closeEntityManager();
		return getUserLanguage(id);
	}

	/*
	 * Services for skills of the User
	 */

	@GET
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> getUserSkill(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		UserSkillResponse userSkillResponse = new UserSkillResponse();
		for (UserSkill userSkill : user.getUserSkill()) {
			userSkillResponse.addSkill(userSkill.getSkill().getId(), userSkill.getSkill().getSkill(),
					userSkill.getLevel());
		}

		List<UserSkillResponse.Skill> ret = userSkillResponse.getSkills();

		log.info("Returned skills of the User with id: " + id + " - nr: " + ret.size());
		
		Entitymanager.closeEntityManager();
		return ret;
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

		try {
			em.getTransaction().begin();
			em.merge(user);
			em.flush();
			em.getTransaction().commit();

			log.info("Skills Updated of the User with id: " + id);
			
			Entitymanager.closeEntityManager();
			return getUserSkill(id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while updating the User's Skills with id: " + id + " - " + e);
			
			Entitymanager.closeEntityManager();
			return skills;
		}
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

		user.getUserSkill().add(newUserSkill);
		skill.getUserSkill().add(newUserSkill);

		try {
			em.getTransaction().begin();
			em.persist(newUserSkill);
			em.flush();
			em.merge(user);
			em.flush();
			em.merge(skill);
			em.flush();
			em.getTransaction().commit();

			log.info("New Skill added to the User with id: " + id);
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while adding New Skill to the User with id: " + id + " - " + e);
		}

		Entitymanager.closeEntityManager();
		return getUserSkill(id);
	}

	@DELETE
	@Path("/skills/{id}/{skill}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UserSkillResponse.Skill> deleteUserSkill(@PathParam("id") Integer id,
			@PathParam("skill") Integer skill) {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		User _user = em.find(User.class, id);
		com.fiyoteam.model.Skill _skill = em.find(com.fiyoteam.model.Skill.class, skill);

		for (UserSkill userSkill : _user.getUserSkill()) {
			if (userSkill.getSkill().getId() == skill) {

				_user.getUserSkill().remove(userSkill);
				_skill.getUserSkill().remove(userSkill);

				try {
					em.getTransaction().begin();
					em.merge(_user);
					em.flush();
					em.merge(_skill);
					em.flush();
					em.remove(userSkill);
					em.flush();
					em.getTransaction().commit();

					log.info("Skill deleted of the User with id: " + id);
				} catch (Exception e) {
					em.getTransaction().rollback();
					log.error("Error occured while deleting Skill of the User with id: " + id + " - " + e);
				}

				break;
			}
		}

		Entitymanager.closeEntityManager();
		return getUserSkill(id);
	}
}
