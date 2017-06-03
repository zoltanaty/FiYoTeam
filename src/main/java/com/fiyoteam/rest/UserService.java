package com.fiyoteam.rest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.fiyoteam.model.Project;
import com.fiyoteam.model.ProjectSkill;
import com.fiyoteam.model.User;
import com.fiyoteam.model.UserLanguage;
import com.fiyoteam.model.UserSkill;
import com.fiyoteam.model.DTO.UserLanguageDTO;
import com.fiyoteam.model.DTO.UserLanguageDTO.Language;
import com.fiyoteam.model.DTO.UserProjectDTO;
import com.fiyoteam.model.DTO.UserSkillDTO;
import com.fiyoteam.model.DTO.UserSkillDTO.Skill;
import com.fiyoteam.persistence.Entitymanager;
import com.fiyoteam.utils.ProjectDateSorter;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/user")
public class UserService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	private final int NUMBER_OF_RECORDS_PER_PAGE = 24;

	private static final String CATALINA_BASE = System.getProperty("catalina.base");

	/*
	 * Test Service
	 */

	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON)
	public String testRest() {

		String person = "{ 'name':'John', 'age':31, 'city':'Cluj Napoca' }";

		return person;
	}

	@GET
	@Path("/nrpages/{searchCriteria : (.*)?}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getNrOfPages(@PathParam("searchCriteria") String searchCriteria) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		Query query;
		if ((null != searchCriteria) && (!"".equals(searchCriteria))) {
			query = em.createQuery(
					"SELECT COUNT(*) FROM User u WHERE u.role != :role AND ((SELECT COUNT(*) FROM UserSkill us  WHERE us.skill.skill LIKE :searchCritearia AND us.user = u) > 0 OR u.firstName LIKE :searchCritearia OR u.lastName LIKE :searchCritearia OR u.country LIKE :searchCritearia OR u.city LIKE :searchCritearia OR u.description LIKE :searchCritearia)");
			query.setParameter("role", "admin");
			query.setParameter("searchCritearia", "%" + searchCriteria + "%");
		} else {
			query = em.createQuery("SELECT COUNT(*) FROM User u WHERE u.role != :role");
			query.setParameter("role", "admin");
		}

		Long nrOfRows = (Long) query.getSingleResult();

		Long nrOfPages = 0L;
		if (nrOfRows % NUMBER_OF_RECORDS_PER_PAGE == 0) {
			nrOfPages = nrOfRows / NUMBER_OF_RECORDS_PER_PAGE;
		} else {
			nrOfPages = nrOfRows / NUMBER_OF_RECORDS_PER_PAGE + 1;
		}

		Entitymanager.closeEntityManager();

		List<Long> pages = new ArrayList<>();
		for (Long i = 0L; i < nrOfPages; ++i) {
			pages.add(i);
		}

		log.info("Returned the nr of pages with Users");

		return Response.ok(pages).build();
	}

	/*
	 * Services of the User
	 */

	@GET
	@Path("/page/{pagenr}/{searchCriteria : (.*)?}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAPageOfUser(@PathParam("pagenr") Integer pageNumber,
			@PathParam("searchCriteria") String searchCriteria) {
		EntityManager em = Entitymanager.getEntityManagerInstance();

		Query query;
		if ((null != searchCriteria) && (!"".equals(searchCriteria))) {
			query = em.createQuery(
					"FROM User u WHERE u.role != :role AND ((SELECT COUNT(*) FROM UserSkill us  WHERE us.skill.skill LIKE :searchCritearia AND us.user = u) > 0 OR u.firstName LIKE :searchCritearia OR u.lastName LIKE :searchCritearia OR u.country LIKE :searchCritearia OR u.city LIKE :searchCritearia OR u.description LIKE :searchCritearia)");
			query.setParameter("role", "admin");
			query.setParameter("searchCritearia", "%" + searchCriteria + "%");
		} else {
			query = em.createQuery("FROM User u WHERE u.role != :role");
			query.setParameter("role", "admin");
		}

		query.setFirstResult(NUMBER_OF_RECORDS_PER_PAGE * pageNumber);
		query.setMaxResults(NUMBER_OF_RECORDS_PER_PAGE);

		@SuppressWarnings("unchecked")
		List<User> personList = (List<User>) query.getResultList();

		log.info("Returned all of the Users");

		Entitymanager.closeEntityManager();

		return Response.ok(personList).build();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getUser(@PathParam("id") Integer id) {
		User user = new User();

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		user = em.find(User.class, id);

		log.info("Returned the User with id: " + id);

		Entitymanager.closeEntityManager();
		return Response.ok(user).build();

	}

	@POST
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response updateUser(@PathParam("id") Integer id, User user) {

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
			return Response.ok(_user).build();

		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while updating the user with id: " + id + " - " + e);
			Entitymanager.closeEntityManager();
			return Response.ok(_user).build();
		}

	}

	@POST
	@Path("/profilepic/{id}")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail, @PathParam("id") Integer id) {

//		String fileExtension = getFileExtension(fileDetail.getFileName());
//		 String uploadedFileLocation = CATALINA_BASE +
//		 "/FiYoTeam/photos/user_" + id + "." + fileExtension;
		String uploadedFileLocation = "user_" + id; // +

		uploadToCloudinary(uploadedInputStream, uploadedFileLocation, id);

//		if (writeToFile(uploadedInputStream, uploadedFileLocation) == true) {
//			log.info("The User with id: " + id + " successfully uploaded his Profile Picture");
//			return Response.ok(true).build();
//		} else {
//			log.error("The User with id: " + id + " Failed to upload his Profile Picture");
//			return Response.ok(false).build();
//		}
		
		return Response.ok(true).build();
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

	private String uploadToCloudinary(InputStream uploadedInputStream, String fileName, Integer userId) {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "zoltanaty", "api_key",
				"614166556482157", "api_secret", "sIduiCPqAxjH2x7pOvh7TkUl3zs"));

		try {
			final File fileToUpload = File.createTempFile(fileName, "");
			java.nio.file.Files.copy(uploadedInputStream, fileToUpload.toPath(), StandardCopyOption.REPLACE_EXISTING);

			Transformation transformation = new Transformation().width(600).height(600).crop("crop").gravity("face");
			@SuppressWarnings("rawtypes")
			Map uploadParams = ObjectUtils.asMap("public_id", fileName, "unique_filename", false, "eager", Arrays.asList(transformation));
			@SuppressWarnings("rawtypes")
			Map uploadResult = cloudinary.uploader().upload(fileToUpload, uploadParams);
			fileToUpload.delete();
			
			@SuppressWarnings("rawtypes")
			ArrayList eagerMap = (ArrayList) uploadResult.get("eager");
			@SuppressWarnings("rawtypes")
			HashMap urlMap = (HashMap) eagerMap.get(0);
			String secureUrl = (String) urlMap.get("secure_url");
			log.info("Successful upload to Cloudinary. You can reach in the URL: " + secureUrl);
			
			EntityManager em = Entitymanager.getEntityManagerInstance();
			em.clear();
			User user = em.find(User.class, userId);
			user.setProfilePicUrl(secureUrl);
			
			em.getTransaction().begin();
			em.merge(user);
			em.flush();
			em.getTransaction().commit();
			
			Entitymanager.closeEntityManager();
			
			log.info("Profilepic URL saved to DataBase");
		} catch (IOException e) {
			log.error("Failed to upload to Cloudinary. - " + e);
		}

		return fileName;
	}

	@GET
	@Path("/profilepic/{id}")
	@Produces("image/png")
	public Response getFullImage(@PathParam("id") int id) {
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
			return Response.ok(file).build();
		} catch (Exception e) {
			log.error("Failed to load the file. - " + e);
			return Response.noContent().build();
		}
	}
	
	@GET
	@Path("/profilepicurl/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getProfilePicUrl(@PathParam("id") int userId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, userId);
		Entitymanager.closeEntityManager();
		
		String profilePicUrl = user.getProfilePicUrl();
		log.info("ProfilePicUrl Returned: " + profilePicUrl);
		
		return Response.ok("{ \"profilePicUrl\": \"" + profilePicUrl + "\"}").build();
	}

	/*
	 * Services for languages of the User
	 */

	@GET
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getUserLanguage(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		UserLanguageDTO userLanguageResponse = new UserLanguageDTO();
		for (UserLanguage userLanguage : user.getUserLanguage()) {
			userLanguageResponse.addLanguage(userLanguage.getLanguage().getId(),
					userLanguage.getLanguage().getLanguage(), userLanguage.getLevel());
		}

		List<UserLanguageDTO.Language> ret = userLanguageResponse.getLanguages();

		log.info("Returned languages of the User with id: " + id + " - nr: " + ret.size());

		Entitymanager.closeEntityManager();

		return Response.ok(ret).build();
	}

	@POST
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response updateUserLanguage(@PathParam("id") Integer id, List<UserLanguageDTO.Language> languages) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		User user = em.find(User.class, id);

		for (UserLanguage userLanguage : user.getUserLanguage()) {
			for (UserLanguageDTO.Language language : languages) {
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
			return Response.ok(languages).build();
		}
	}

	@PUT
	@Path("/languages/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response addUserLanguage(@PathParam("id") Integer id, Language newLanguage) {

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
	@ResourceFilters(MyRequestFilter.class)
	public Response deleteUserLanguage(@PathParam("id") Integer id, @PathParam("language") Integer language) {

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
	@ResourceFilters(MyRequestFilter.class)
	public Response getUserSkill(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		UserSkillDTO userSkillResponse = new UserSkillDTO();
		for (UserSkill userSkill : user.getUserSkill()) {
			userSkillResponse.addSkill(userSkill.getSkill().getId(), userSkill.getSkill().getSkill(),
					userSkill.getLevel());
		}

		List<UserSkillDTO.Skill> ret = userSkillResponse.getSkills();

		log.info("Returned skills of the User with id: " + id + " - nr: " + ret.size());

		Entitymanager.closeEntityManager();
		return Response.ok(ret).build();
	}

	@POST
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response updateUserSkill(@PathParam("id") Integer id, List<UserSkillDTO.Skill> skills) {

		EntityManager em = Entitymanager.getEntityManagerInstance();

		User user = em.find(User.class, id);

		for (UserSkill userSkill : user.getUserSkill()) {
			for (UserSkillDTO.Skill skill : skills) {
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
			return Response.ok(skills).build();
		}
	}

	@PUT
	@Path("/skills/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response addUserSkill(@PathParam("id") Integer id, Skill newSkill) {

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
	@ResourceFilters(MyRequestFilter.class)
	public Response deleteUserSkill(@PathParam("id") Integer id, @PathParam("skill") Integer skill) {

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

	/*
	 * Services for projects of the User
	 */

	@GET
	@Path("/projects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getUserProject(@PathParam("id") Integer id) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		if (null != user) {
			List<UserProjectDTO> userProjectResponse = new ArrayList<>();

			Collections.sort(user.getUserProjects(), new ProjectDateSorter());

			for (Project project : user.getUserProjects()) {
				UserProjectDTO uPR = new UserProjectDTO();
				List<Skill> projectSkillList = new ArrayList<>();

				for (ProjectSkill projectSkill : project.getProjectSkill()) {
					Skill skill = new Skill();
					skill.setId(projectSkill.getSkill().getId());
					skill.setSkill(projectSkill.getSkill().getSkill());
					projectSkillList.add(skill);
				}

				uPR.setProject(project);
				uPR.setSkills(projectSkillList);
				uPR.setAuthorId(project.getUser().getId());
				uPR.setAuthorName(project.getUser().getFirstName() + " " + project.getUser().getLastName());
				uPR.setCreatedAt(project.getCreatedAt());

				userProjectResponse.add(uPR);
			}

			log.info("Returned Projects of the User with id: " + id + " - nr: " + userProjectResponse.size());

			Entitymanager.closeEntityManager();
			return Response.ok(userProjectResponse).build();
		} else {
			return Response.noContent().build();
		}
	}

	@PUT
	@Path("/projects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response addUserProject(@PathParam("id") Integer id, UserProjectDTO newProject) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);

		newProject.getProject().setCreatedAt(new Date());
		newProject.getProject().setUser(user);

		try {
			Project _newProject = newProject.getProject();
			em.getTransaction().begin();
			em.persist(_newProject);
			em.flush();

			List<ProjectSkill> projectSkill = new ArrayList<>();
			for (Skill skill : newProject.getSkills()) {
				ProjectSkill projSkill = new ProjectSkill();
				projSkill.setProject(newProject.getProject());
				projSkill.setSkill(new com.fiyoteam.model.Skill(skill.getId(), skill.getSkill()));

				try {
					em.persist(projSkill);
					em.flush();
					projectSkill.add(projSkill);

					log.info("Skills assigned to New Project to the User with id: " + id);
				} catch (Exception e) {
					em.getTransaction().rollback();
					log.error("Error occured while adding New Project to the Database. " + e);
				}

			}
			em.getTransaction().commit();

			log.info("New Project added to the Database with id: " + newProject.getProject().getId());
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while adding New Project to the Database. " + e);
		}

		Entitymanager.closeEntityManager();
		return getUserProject(id);
	}

	@POST
	@Path("/projects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response editUserProject(@PathParam("id") Integer id, UserProjectDTO projectToEdit) {

		EntityManager em = Entitymanager.getEntityManagerInstance();
		em.clear();
		User user = em.find(User.class, id);
		projectToEdit.getProject().setUser(user);

		try {
			Project _projectToEdit = projectToEdit.getProject();

			try {
				em.getTransaction().begin();
				em.merge(_projectToEdit);
				em.flush();
				em.getTransaction().commit();
			} catch (Exception e) {
				em.getTransaction().rollback();
				log.error("Error occured while Editing Project. " + e);
			}

			_projectToEdit.setProjectSkill(new ArrayList<ProjectSkill>());

			Iterator<Project> itr = user.getUserProjects().iterator();
			while (itr.hasNext()) {
				Project prj = itr.next();
				if (prj.getId() == _projectToEdit.getId()) {
					for (com.fiyoteam.model.ProjectSkill ps : prj.getProjectSkill()) {
						em.getTransaction().begin();
						Query query = em
								.createQuery("DELETE FROM ProjectSkill ps WHERE project = :project and skill = :skill");
						query.setParameter("project", projectToEdit.getProject());
						query.setParameter("skill", ps.getSkill());
						query.executeUpdate();
						em.getTransaction().commit();
					}

					prj.setProjectSkill(new ArrayList<ProjectSkill>());
					break;
				}
			}

			em.getTransaction().begin();
			List<ProjectSkill> projectSkill = new ArrayList<>();
			for (Skill skill : projectToEdit.getSkills()) {
				ProjectSkill projSkill = new ProjectSkill();
				projSkill.setProject(projectToEdit.getProject());
				projSkill.setSkill(new com.fiyoteam.model.Skill(skill.getId(), skill.getSkill()));

				try {
					em.persist(projSkill);
					em.flush();
					projectSkill.add(projSkill);
				} catch (Exception e) {
					em.getTransaction().rollback();
					log.error("Error occured while Editing Project. " + e);
				}
			}
			em.getTransaction().commit();

			log.info("Edited Project added to the Database with id: " + projectToEdit.getProject().getId());
		} catch (Exception e) {
			em.getTransaction().rollback();
			log.error("Error occured while Editing Project. " + e);
		}

		Entitymanager.closeEntityManager();
		return getUserProject(id);

	}
}
