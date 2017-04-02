package com.fiyoteam.persistence;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Entitymanager {

	private static EntityManager em = null;

	public static EntityManager getEntityManagerInstance() {
		if (em == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("FiYoTeamPersistence");
			em = emf.createEntityManager();
		}

		return em;
	}

	// private static final EntityManagerFactory emf;
	// private static final ThreadLocal<EntityManager> threadLocal;
	//
	// static {
	// emf = Persistence.createEntityManagerFactory("FiYoTeamPersistence");
	// threadLocal = new ThreadLocal<EntityManager>();
	// }
	//
	// public static EntityManager getEntityManagerInstance() {
	// EntityManager em = threadLocal.get();
	//
	// if (em == null) {
	// em = emf.createEntityManager();
	// em.setFlushMode(FlushModeType.COMMIT);
	// threadLocal.set(em);
	// }
	// return em;
	// }
	//
	// public static void closeEntityManager() {
	// EntityManager em = threadLocal.get();
	// if (em != null) {
	// em.close();
	// threadLocal.set(null);
	// }
	// }
	//
	// public static void closeEntityManagerFactory() {
	// emf.close();
	// }
	//
	// public static void begin() {
	// getEntityManagerInstance().getTransaction().begin();
	// }
	//
	// public static void rollback() {
	// getEntityManagerInstance().getTransaction().rollback();
	// }
	//
	// public static void commit() {
	// getEntityManagerInstance().getTransaction().commit();
	// }
}
