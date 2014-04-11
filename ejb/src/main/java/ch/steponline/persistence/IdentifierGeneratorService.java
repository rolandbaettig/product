package ch.steponline.persistence;

import org.hibernate.SessionFactory;
import org.hibernate.StatelessSession;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.cfg.ObjectNameNormalizer;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.enhanced.TableGenerator;
import org.hibernate.id.factory.internal.DefaultIdentifierGeneratorFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.internal.StatelessSessionImpl;
import org.hibernate.type.LongType;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Scope;
import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 10.04.14
 * Time: 09:54
 * To change this template use File | Settings | File Templates.
 */
@Named(value = "IdentifierGeneratorService")
public class IdentifierGeneratorService {
    @Inject
    private EntityManager entityManager;

    private static HashMap<String, IdentifierGenerator> identifierGenerators = new HashMap<String, IdentifierGenerator>();

    private EntityManager getEntityManager() {
        return entityManager;
    }

    public Long generateIdentifier(Object object) {
        SessionImpl sessionImpl = (SessionImpl) getEntityManager().getDelegate();
        SessionFactory sessionFactory = sessionImpl.getSessionFactory();
        StatelessSession session = sessionFactory.openStatelessSession();
        Serializable id = createIdGenerator(object).generate((StatelessSessionImpl) session, new Id());
        session.close();
        return (Long) id;

    }

    /**
     * Target object for ID generation
     */
    private static class Id {
        private Integer id;

        @javax.persistence.Id
        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }


    private IdentifierGenerator createIdGenerator(Object object) {
        String segmentValue = object.getClass().getSimpleName();
        IdentifierGenerator identifierGenerator = identifierGenerators.get(segmentValue);
        if (identifierGenerator == null) {
            Properties configuration = new Properties();
            configuration.setProperty(TableGenerator.TABLE_PARAM, "Product_Sequence");
            configuration.setProperty(TableGenerator.SCHEMA, "product");
            configuration.setProperty(TableGenerator.SEGMENT_COLUMN_PARAM, "sequence_name");
            configuration.setProperty(TableGenerator.SEGMENT_VALUE_PARAM, segmentValue);
            configuration.setProperty(TableGenerator.INITIAL_PARAM, "1");
            configuration.setProperty(TableGenerator.VALUE_COLUMN_PARAM, "next_val");
            configuration.setProperty(TableGenerator.INCREMENT_PARAM, "100");

            ObjectNameNormalizer normalizer = new ObjectNameNormalizer() {

                @Override
                protected boolean isUseQuotedIdentifiersGlobally() {
                    return false;
                }

                @Override
                protected NamingStrategy getNamingStrategy() {
                    return new Configuration().getNamingStrategy();
                }
            };
            configuration.put(TableGenerator.IDENTIFIER_NORMALIZER, normalizer);

            DefaultIdentifierGeneratorFactory identifierGeneratorFactory = new DefaultIdentifierGeneratorFactory();
            SessionImpl sessionImpl = (SessionImpl) getEntityManager().getDelegate();
            SessionFactory sessionFactory = sessionImpl.getSessionFactory();
            identifierGeneratorFactory.injectServices(((SessionFactoryImpl) sessionFactory).getServiceRegistry());


            identifierGenerator = identifierGeneratorFactory.createIdentifierGenerator("enhanced-table",
                    new LongType(),
                    configuration);
            identifierGenerators.put(segmentValue, identifierGenerator);
        }
        return identifierGenerator;
    }
}
