package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.Language;
import com.fiyoteam.persistence.Entitymanager;
import com.sun.jersey.spi.container.ResourceFilters;

@Path("/language")
public class LanguageService {

	private static final Logger log = LoggerFactory.getLogger(LanguageService.class);

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response getAllLanguages() {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Language l ORDER BY l.language");

		@SuppressWarnings("unchecked")
		List<Language> languageList = (List<Language>) query.getResultList();

		log.info("Returned all of the Languages");

		Entitymanager.closeEntityManager();
		return Response.ok(languageList).build();

	}
	
	@GET
	@Path("/{newLanguage}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response addNewLanguage(@PathParam("newLanguage") String newLanguage) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Language language = new Language();
		language.setId(0);
		language.setLanguage(newLanguage);
		
		em.getTransaction().begin();
		em.persist(language);
		em.flush();
		em.getTransaction().commit();

		log.info("New language added by Admin");

		Entitymanager.closeEntityManager();
		return getAllLanguages();

	}
	
	@DELETE
	@Path("/{languageId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResourceFilters(MyRequestFilter.class)
	public Response deleteLanguage(@PathParam("languageId") Integer languageId) {
		EntityManager em = Entitymanager.getEntityManagerInstance();
		
		Language toDelete = em.find(Language.class, languageId);
		
		em.getTransaction().begin();
		em.remove(toDelete);
		em.flush();
		em.getTransaction().commit();

		log.info("Language removed by Admin");

		Entitymanager.closeEntityManager();
		return getAllLanguages();

	}

}
