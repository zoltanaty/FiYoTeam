package com.fiyoteam.rest;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fiyoteam.model.response.UserLanguageResponse.Language;
import com.fiyoteam.persistence.Entitymanager;

@Path("/language")
public class LanguageService {
	
	private static final Logger log = LoggerFactory.getLogger(LanguageService.class);
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Language> getAllLanguages() {
		
		EntityManager em = Entitymanager.getEntityManagerInstance();
		Query query = em.createQuery("FROM Language");
		
	    @SuppressWarnings("unchecked")
		List<Language> languageList = (List<Language>) query.getResultList();
	    
	    Entitymanager.closeEntityManager();
	    
	    log.info("Returned all of the Languages");
	    
	    return languageList;
	}

}
