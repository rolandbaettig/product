package ch.steponline.core.model;

import org.hibernate.annotations.OptimisticLock;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 09:32
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="LanguageTextContainer",
        schema="core",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId","LanguageIsoCode"})},
        indexes = {
                @Index(name="IDX_LanguageTextContainer_Language",
                        columnList = "SourceId"
                ),
                @Index(name="IDX_LanguageTextContainer",
                        columnList = "LanguageIsoCode"
                )
        }
)
@Audited
@AuditTable(value="LanguageTextContainer_AUDIT",schema = "audit")

public class LanguageTextContainer extends AbstractTextContainer {

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="SourceId",referencedColumnName = "Id",
            insertable = false,updatable = false,
            foreignKey = @ForeignKey(name="FK_LanguageTextContainer",foreignKeyDefinition ="FOREIGN KEY (SourceId) REFERENCES Language (Id) ON DELETE CASCADE",value = ConstraintMode.CONSTRAINT)
    )
    @OptimisticLock(excluded = true)
    private Language language;

    public LanguageTextContainer(){};

    public LanguageTextContainer(Language language, String languageIso) {
        super(language.getId(), languageIso);
        this.language=language;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
