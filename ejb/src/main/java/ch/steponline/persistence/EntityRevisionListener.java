package ch.steponline.persistence;

import ch.steponline.service.UserService;
import org.hibernate.envers.RevisionListener;

import javax.inject.Inject;

public class EntityRevisionListener implements RevisionListener {

    @Inject
    private UserService userService;

    public void newRevision(Object revisionEntity) {
        EntityRevision revision = (EntityRevision) revisionEntity;
        revision.setUsername(getUserService().getUsername()); //for testing
    }

    private UserService getUserService() {
        if (userService==null) {
            userService=new UserService();
        }
        return userService;
    }
}
