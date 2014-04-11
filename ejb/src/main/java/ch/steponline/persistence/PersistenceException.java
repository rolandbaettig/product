package ch.steponline.persistence;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 10.04.14
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class PersistenceException extends Exception {

    public PersistenceException() {
        super();
    }

    public PersistenceException(String message,Throwable throwable) {
        super(message,throwable);
    }

    public PersistenceException(String message) {
        super(message);
    }

    public PersistenceException(Throwable cause) {
        super(cause);
    }


}
