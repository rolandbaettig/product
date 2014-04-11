package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 09:24
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractVersionedEntity<K extends Serializable,E extends AbstractVersionedEntity> extends AbstractEntity<K,E> implements Serializable{

    @Version
    @Column(name="Version")
    @NotNull
    @XmlTransient
    private int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
