package ch.steponline.core.model;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 03.04.14
 * Time: 20:38
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Language",
        schema="core",
        indexes = {
        @Index(name="IDX_Language_IsoCode",columnList = "IsoCode")
}
)
@FilterDef(name="LanguageFilter",
        parameters=@ParamDef( name="languageParam", type="string" ) )
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@Audited
@AuditTable(value="Language_AUDIT",schema = "audit")
public class Language implements Serializable {
    @Id
    private Long id;

    @Column(name="IsoCode")
    private String isoCode;

    @OneToMany(mappedBy = "language",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER)
    @org.hibernate.annotations.Filter(
            name = "LanguageFilter",
            condition="id.languageIsoCode = :languageParam"
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<LanguageTextContainer> textContainers=new HashSet<LanguageTextContainer>();



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public Set<LanguageTextContainer> getTextContainers() {
        return textContainers;
    }

    public void setTextContainers(Set<LanguageTextContainer> textContainers) {
        this.textContainers = textContainers;
    }

    public String getShortName(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getShortName();
    }

    public String getLongName(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getLongName();
    }

    public String getAbbreviation(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getAbbreviation();
    }

    public String getDocumentation(String language) {
        AbstractTextContainer textContainer=getTextContainer(language);
        return textContainer==null?"":textContainer.getDocumentation();
    }

    private AbstractTextContainer getTextContainer(String language) {
        for (AbstractTextContainer textContainer : getTextContainers()) {
            if (language.equals(textContainer.getLanguageIsoCode())) return textContainer;
        }
        return null;
    }

}
