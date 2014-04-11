package ch.steponline.persistence;

import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 17:17
 * To change this template use File | Settings | File Templates.
 */
public class EntityManagerProducer {
    private EntityManager entityManager;
    private EntityManagerFactory entityManagerFactory;

    @Produces
    public EntityManager getEntityManager() {
        if (entityManager==null ||
                !entityManager.isOpen())  {
            entityManager=createEntityManager();
        }
        return entityManager;
    }

    private EntityManager createEntityManager() {
        if (entityManagerFactory==null || !entityManagerFactory.isOpen()) {
            entityManagerFactory= Persistence.createEntityManagerFactory("productDS");
        }
        return entityManagerFactory.createEntityManager();
    }
}
