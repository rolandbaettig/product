package ch.steponline.service;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 10.04.14
 * Time: 17:16
 * To change this template use File | Settings | File Templates.
 */
@Singleton
@Named(value = "UserService")
public class UserService {

    public String getUsername() {
        return System.getProperty("user.name");
    }
}
