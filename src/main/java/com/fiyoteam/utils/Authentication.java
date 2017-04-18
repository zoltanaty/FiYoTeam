package com.fiyoteam.utils;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.fiyoteam.model.AuthToken;
import com.fiyoteam.persistence.Entitymanager;

public class Authentication {

	public static boolean isTokenAllowed(String token, Integer userId) {
		if (token != null) {
			EntityManager em = Entitymanager.getEntityManagerInstance();

			Query query = em.createQuery("FROM AuthToken a WHERE a.user = :userId");
			query.setParameter("userId", userId);

			@SuppressWarnings("unchecked")
			List<AuthToken> tokens = (List<AuthToken>) query.getResultList();

			if (tokens.size() > 0) {
				if (token.equals(tokens.get(0).getToken())) {
					return true;
				}
			}
			Entitymanager.closeEntityManager();
		}
		
		return false;
	}

}
