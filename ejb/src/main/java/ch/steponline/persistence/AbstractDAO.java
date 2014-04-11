package ch.steponline.persistence;

import ch.steponline.core.model.AbstractEntity;
import org.hibernate.id.IdentifierGenerator;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 10.04.14
 * Time: 10:55
 * To change this template use File | Settings | File Templates.
 */
public class AbstractDAO {
    @Inject
    private EntityManager entityManager;

    @Inject
    private IdentifierGeneratorService identifierGeneratorService;

    private Logger logger=Logger.getLogger(getClass().getName());

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Long generateIdentifier(Object object) {
        return identifierGeneratorService.generateIdentifier(object);
    }

    protected <E extends AbstractEntity> E saveEntity(E object) throws PersistenceException {
        try {
            getEntityManager().getTransaction().begin();
            if (object.getUnsaved()) {
                getEntityManager().persist(object);
            } else {
                object=getEntityManager().merge(object);
            }
            getEntityManager().getTransaction().commit();
            return object;
        } catch (Exception e) {
            logger.log(Level.SEVERE,e.getMessage());
            throw new PersistenceException(e);
        }
    }
}
