package ch.steponline.core.model;

import ch.steponline.product.model.Product;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 11.04.14
 * Time: 19:57
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="Unit",schema = "core",
        uniqueConstraints = {
                @UniqueConstraint(name="UQ_Unit",columnNames = {"IsoCode"})
        },
        indexes = {
                @Index(name="IDX_Unit_UnitType",columnList = "UnitType")
        }
)
@NamedQueries(
        value={
                @NamedQuery(name="UnitsByType",query="select u from Unit u where u.unitType=:unitType order by u.isoCode"),
                @NamedQuery(name="AllUnitsByLanguage",query="select distinct u from Unit u join fetch u.textContainers tx where tx.id.languageIsoCode=:language order by tx.abbreviation")
        }
)
@FilterDef(name="LanguageFilter",
        parameters=@ParamDef( name="languageParam", type="string" ) )
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@Audited
@AuditTable(value="Unit_AUDIT",schema = "audit")
public class Unit extends AbstractEntityTextContainer<Long,Unit>{
    @Id
    private Long id;

    @Column(name="IsoCode",nullable = false,length=50)
    @NotNull
    @Size(max=50)
    private String isoCode;

    @Column(name = "UnitType",nullable = false,length = 30)
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private UnitType unitType;

    @OneToMany(mappedBy = "unit",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,targetEntity = UnitTextContainer.class)
    @Size(min=1)
    @org.hibernate.annotations.Filter(
            name = "LanguageFilter",
            condition="id.languageIsoCode = :languageParam"
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<AbstractTextContainer> textContainers=new HashSet<AbstractTextContainer>();

    @OneToMany(mappedBy = "childUnit",cascade = CascadeType.ALL)
    @NotAudited
    private Set<UnitFactorAss> childUnits=new HashSet<UnitFactorAss>();

    @OneToMany(mappedBy = "parentUnit",cascade = CascadeType.ALL)
    @NotAudited
    private Set<UnitFactorAss> parentUnits=new HashSet<UnitFactorAss>();

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

    public Set<AbstractTextContainer> getTextContainers() {
        return textContainers;
    }

    public void setTextContainers(Set<AbstractTextContainer> textContainers) {
        this.textContainers = textContainers;
    }

    @Override
    protected AbstractTextContainer createTextContainer(String language) {
        UnitTextContainer unitTextContainer= new UnitTextContainer(this,language);
        getTextContainers().add(unitTextContainer);
        return unitTextContainer;
    }

    public Set<UnitFactorAss> getChildUnits() {
        return childUnits;
    }

    public void setChildUnits(Set<UnitFactorAss> childUnits) {
        this.childUnits = childUnits;
    }

    public Set<UnitFactorAss> getParentUnits() {
        return parentUnits;
    }

    public void setParentUnits(Set<UnitFactorAss> parentUnits) {
        this.parentUnits = parentUnits;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }


    public void addUnitFactorAss(UnitFactorAss unitFactorAss) {
        if (unitFactorAss.getChildUnit()!=null && unitFactorAss.getChildUnit().equals(this)) {
            if (!childUnits.contains(unitFactorAss.getChildUnit())) {
                childUnits.add(unitFactorAss);
                return;
            }
        }
        if (unitFactorAss.getParentUnit()!=null && unitFactorAss.getParentUnit().equals(this)) {
            if (!parentUnits.contains(unitFactorAss.getParentUnit())) {
                parentUnits.add(unitFactorAss);
            }
        }
    }

    public void removeUnitFactorAss(UnitFactorAss unitFactorAss) {
        if (unitFactorAss.getChildUnit()!=null &&
                unitFactorAss.getChildUnit().equals(this)) {
            childUnits.remove(unitFactorAss);
            return;
        }
        if (unitFactorAss.getParentUnit()!=null &&
                unitFactorAss.getParentUnit().equals(this)) {
            parentUnits.remove(unitFactorAss);
        }
    }

}
