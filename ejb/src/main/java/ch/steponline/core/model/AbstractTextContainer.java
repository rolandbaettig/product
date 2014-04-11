package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class AbstractTextContainer extends AbstractVersionedEntity<AbstractTextContainerPK, AbstractTextContainer> {
    @EmbeddedId
    private AbstractTextContainerPK id;

    @Column(name = "Abbreviation",nullable = false,length = 50)
    @NotNull
    @Size(max=50)
    private String abbreviation;

    @Column(name = "ShortName")
    @Size(max = 250)
    private String shortName;

    @Column(name = "LongName",nullable = false)
    @NotNull
    @Size(max=500)
    private String longName;

    @Column(name = "Documentation")
    @Size(max=4000)
    private String documentation;

    public AbstractTextContainer() {
        id = new AbstractTextContainerPK();
        this.setUnsaved(Boolean.FALSE);
    }

    public AbstractTextContainer(Long targetId, String language) {
        id = new AbstractTextContainerPK(targetId, language);
        this.setUnsaved(Boolean.TRUE);
    }

    public AbstractTextContainerPK getId() {
        return id;
    }

    public void setId(AbstractTextContainerPK id) {
        this.id = id;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getLanguageIsoCode() {
        if (getId()!=null) return getId().getLanguageIsoCode();
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractTextContainer)) return false;
        if (!super.equals(o)) return false;

        AbstractTextContainer that = (AbstractTextContainer) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
