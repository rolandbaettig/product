package ch.steponline.persistence;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.SequenceGenerator;
import org.hibernate.id.enhanced.TableGenerator;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 09.04.14
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class MyIdGenerator extends TableGenerator {


    @Override
    public Serializable generate(SessionImplementor session, Object object)
            throws HibernateException {
        if (object == null)
            throw new HibernateException(new NullPointerException());

        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)
                    && field.isAnnotationPresent(GeneratedValue.class)) {
                boolean isAccessible = field.isAccessible();
                try {
                    field.setAccessible(true);
                    Object obj = field.get(object);
                    field.setAccessible(isAccessible);
                    if (obj != null) {
                        if (Integer.class.isAssignableFrom(obj.getClass())) {
                            if (((Integer) obj) > 0) {
                                return (Serializable) obj;
                            }
                        }
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return super.generate(session, object);
    }


}
