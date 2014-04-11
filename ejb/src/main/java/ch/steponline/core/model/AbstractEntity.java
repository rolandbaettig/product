package ch.steponline.core.model;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 09:18
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractEntity<K extends Serializable,E extends AbstractEntity> {
    @Transient
    @XmlTransient
    private Boolean unsaved;

    public abstract K getId();

    public abstract void setId(K id);

    @XmlTransient
    public Boolean getUnsaved() {
        return unsaved!=null && unsaved;
    }

    public void setUnsaved(Boolean unsaved) {
        this.unsaved = unsaved;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractEntity)) return false;

        AbstractEntity that = (AbstractEntity) o;

        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }
}
