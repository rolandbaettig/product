package ch.steponline.core.model;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 11.04.14
 * Time: 20:11
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="UnitFactorAss",schema = "core",
        uniqueConstraints = {
                @UniqueConstraint(name="UQ_UnitFactorAss",columnNames = {"ParentUnitId","ChildUnitId"})
        } ,
        indexes = {
                @Index(name="IDX_UnitFactorAss_Parent",columnList = "ParentUnitId"),
                @Index(name="IDX_UnitFactorAss_Child",columnList = "ChildUnitId")
        }
)
public class UnitFactorAss extends AbstractEntity<Long,Unit> {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="ParentUnitId",referencedColumnName = "Id",
            foreignKey = @ForeignKey(name = "FK_UnitFactorAss_ParentUnit",foreignKeyDefinition = "FOREIGN KEY (ParentUnitId) REFERENCES Unit (Id)",value=ConstraintMode.CONSTRAINT))
    @NotNull
    private Unit parentUnit;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name="ChildUnitId",referencedColumnName = "Id",
            foreignKey = @ForeignKey(name = "FK_UnitFactorAss_ChildUnit",foreignKeyDefinition = "FOREIGN KEY (ChildUnitId) REFERENCES Unit (Id)",value=ConstraintMode.CONSTRAINT))
    @NotNull
    private Unit childUnit;

    @Column(name="Factor",nullable = false)
    @NotNull
    private Double factor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Unit getParentUnit() {
        return parentUnit;
    }

    public void setParentUnit(Unit parentUnit) {
        if (this.parentUnit!=null &&
                this.parentUnit.getParentUnits().contains(this)) {
            this.parentUnit.removeUnitFactorAss(this);
        }
        this.parentUnit = parentUnit;

        if (parentUnit!=null && !parentUnit.getParentUnits().contains(this)) {
            parentUnit.addUnitFactorAss(this);
        }
    }

    public Unit getChildUnit() {
        return childUnit;
    }

    public void setChildUnit(Unit childUnit) {
        if (this.childUnit!=null &&
                this.childUnit.getChildUnits().contains(this)) {
            this.childUnit.removeUnitFactorAss(this);
        }
        this.childUnit = childUnit;

        if (childUnit!=null && !childUnit.getChildUnits().contains(this)) {
            childUnit.addUnitFactorAss(this);
        }
    }

    public Double getFactor() {
        return factor;
    }

    public void setFactor(Double factor) {
        this.factor = factor;
    }

    @AssertTrue(message = "ch.steponline.core.UnitFactorAss.mustHaveSameUnitType")
    public boolean validateParentChild() {
        return (getChildUnit().getUnitType().equals(getParentUnit().getUnitType()));
    }

}
