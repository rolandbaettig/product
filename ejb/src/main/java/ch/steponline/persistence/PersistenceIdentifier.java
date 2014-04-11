package ch.steponline.persistence;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 03.04.14
 * Time: 21:06
 * To change this template use File | Settings | File Templates.
 */
public abstract class PersistenceIdentifier<T> implements IPersistenceIdentifier {
    protected T id;

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersistenceIdentifier)) return false;

        PersistenceIdentifier that = (PersistenceIdentifier) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
