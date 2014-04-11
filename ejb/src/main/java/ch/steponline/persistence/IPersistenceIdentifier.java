package ch.steponline.persistence;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 03.04.14
 * Time: 21:05
 * To change this template use File | Settings | File Templates.
 */
public interface IPersistenceIdentifier<T> {
    public T getId();

}
