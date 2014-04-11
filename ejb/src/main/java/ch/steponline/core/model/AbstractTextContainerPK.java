package ch.steponline.core.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 09:14
 * To change this template use File | Settings | File Templates.
 */
@Embeddable
public class AbstractTextContainerPK implements Serializable {
    @Column(name="SourceId",nullable = false)
    private Long sourceId;

    @Column(name="LanguageIsoCode",nullable = false,length = 10)
    private String languageIsoCode;

    public AbstractTextContainerPK(){}

    public AbstractTextContainerPK(Long targetId, String language) {
        this.sourceId = targetId;
        this.languageIsoCode = language;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getLanguageIsoCode() {
        return languageIsoCode;
    }

    public void setLanguageIsoCode(String languageIsoCode) {
        this.languageIsoCode = languageIsoCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractTextContainerPK that = (AbstractTextContainerPK) o;

        if (getLanguageIsoCode() != null ? !getLanguageIsoCode().equals(that.getLanguageIsoCode()) : that.getLanguageIsoCode() != null) return false;
        if (getSourceId() != null ? !getSourceId().equals(that.getSourceId()) : that.getSourceId() != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getSourceId() != null ? getSourceId().hashCode() : 0;
        result = 31 * result + (getLanguageIsoCode() != null ? getLanguageIsoCode().hashCode() : 0);
        return result;
    }
}
