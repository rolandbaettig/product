package ch.steponline.core.model;

import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 11.04.14
 * Time: 20:02
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="UnitTextContainer",
        schema="core",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId","LanguageIsoCode"})},
        indexes = {
                @Index(name="IDX_UnitTextContainer",
                        columnList = "SourceId"
                ),
                @Index(name="IDX_UnitTextContainer",
                        columnList = "LanguageIsoCode"
                )
        }
)
@Audited
@AuditTable(value="UnitTextContainer_AUDIT",schema = "audit")
public class UnitTextContainer extends AbstractTextContainer {
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="SourceId",referencedColumnName = "Id",
            insertable = false,updatable = false,
            foreignKey = @ForeignKey(name="FK_UnitTextContainer",foreignKeyDefinition ="FOREIGN KEY (SourceId) REFERENCES Unit (Id) ON DELETE CASCADE",value = ConstraintMode.CONSTRAINT)
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Unit unit;

    public UnitTextContainer(Unit unit,String language) {
        super(unit.getId(),language);
        this.unit=unit;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}
