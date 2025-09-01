package me.mispz.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class PersistenceEntityManager {

    private static PersistenceEntityManager instance = null;
    private final EntityManagerFactory emf;

    private PersistenceEntityManager () {
        emf = Persistence.createEntityManagerFactory("default");
    }

    public static synchronized PersistenceEntityManager getInstance(){
        if(instance == null){
            instance = new PersistenceEntityManager();
        }
        return instance;
    }

    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

}
