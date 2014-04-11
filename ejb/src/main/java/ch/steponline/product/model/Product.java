package ch.steponline.product.model;

import ch.steponline.core.model.AbstractEntityTextContainer;
import ch.steponline.core.model.Unit;
import ch.steponline.product.model.ProductTextContainer;
import ch.steponline.core.model.AbstractTextContainer;
import ch.steponline.core.model.AbstractVersionedEntity;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Defines a Product
 * User: Roland Bättig
 * Date: 03.04.14
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name ="Product",
         schema="product",
        uniqueConstraints = {
                @UniqueConstraint(name="UQ_Product",columnNames = {"Number","ProductGroupId"})
        }
        indexes = {
                @Index(name="IDX_Product_ProductGroup",columnList = "ProductGroupId")
        }
)
@NamedQueries(
        value={
                @NamedQuery(name="ProductsOfProductGroupByLanguage",query="select distinct p from Product p join fetch p.textContainers tx where p.productGroup=:productGroup and tx.id.languageIsoCode=:language order by tx.abbreviation")
        }
)
@FilterDef(name="LanguageFilter",
        parameters=@ParamDef( name="languageParam", type="string" ) )
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@Audited
@AuditTable(value="Product_AUDIT",schema = "audit")
public class Product extends AbstractEntityTextContainer<Long,Product> {
    @Id
    private Long id;

    /**
     * Unique Number of the Product in the defined Product Group
     */
    @Column(name="Number",nullable = false)
    @NotNull
    @Size(max = 50)
    private String number;

    @ManyToOne(optional = false)
    @JoinColumn(name = "ProductGroupId",referencedColumnName = "Id",nullable = false,
            foreignKey = @ForeignKey(name="FK_Product_ProductGroup",value = ConstraintMode.CONSTRAINT,
                    foreignKeyDefinition = "FOREIGN KEY (ProductGroupId) REFERENCES ProductGroup (Id) ON DELETE CASCADE"))
    @NotNull
    private ProductGroup productGroup;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,targetEntity = ProductTextContainer.class)
    @Size(min=1)
    @org.hibernate.annotations.Filter(
            name = "LanguageFilter",
            condition="languageIsoCode = :languageParam"
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<AbstractTextContainer> textContainers=new HashSet<AbstractTextContainer>();

    @ManyToOne(optional = true)
    @JoinColumn(name="UnitId",referencedColumnName = "Id",
            foreignKey = @ForeignKey(name="FK_Product_Unit",foreignKeyDefinition = "FOREIGN KEY (UnitId) REFERENCES Unit (Id)",value = ConstraintMode.CONSTRAINT))
    private Unit unit;

    /**
     * Approximated Hours for preparing the Work for this Product
     */
    @Column(name="ApproximateWorkPrepareHour",nullable = false)
    @NotNull
    private Double approximateWorkPrepareHour;

    /**
     * Approximated Hours used on the Machines
     */
    @Column(name="ApproximateMachineHour",nullable = false)
    @NotNull
    private Double approximateMachineHour;

    /**
     * Approximated Hours used to finish the Product
     */
    @Column(name="ApproximateWorkHour",nullable = false)
    @NotNull
    private Double approximateWorkHour;

    /**
     * Approximated Hours used to install the Product at the Client
     */
    @Column(name="ApproximateInstallHour",nullable = false)
    @NotNull
    private Double approximateInstallHour;

    /**
     * Approximated average Price for the Material used to build the Product
     */
    @Column(name="ApproximateMaterialPrice",nullable = false)
    @NotNull
    private Double approximateMaterialPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        if (this.productGroup!=null &&
                this.productGroup.getProducts().contains(this)) {
            this.productGroup.getProducts().remove(this);
        }
        this.productGroup = productGroup;

        if (productGroup!=null && !productGroup.getProducts().contains(this)) {
            productGroup.getProducts().add(this);
        }
    }

    public Set<AbstractTextContainer> getTextContainers() {
        return textContainers;
    }

    public void setTextContainers(Set<AbstractTextContainer> textContainers) {
        this.textContainers = textContainers;
    }

    @Override
    protected AbstractTextContainer createTextContainer(String language) {
        ProductTextContainer productTextContainer= new ProductTextContainer(this,language);
        getTextContainers().add(productTextContainer);
        return productTextContainer;
    }

    public Double getApproximateWorkPrepareHour() {
        return approximateWorkPrepareHour;
    }

    public void setApproximateWorkPrepareHour(Double approximateWorkPrepareHour) {
        this.approximateWorkPrepareHour = approximateWorkPrepareHour;
    }

    public Double getApproximateMachineHour() {
        return approximateMachineHour;
    }

    public void setApproximateMachineHour(Double approximateMachineHour) {
        this.approximateMachineHour = approximateMachineHour;
    }

    public Double getApproximateWorkHour() {
        return approximateWorkHour;
    }

    public void setApproximateWorkHour(Double approximateWorkHour) {
        this.approximateWorkHour = approximateWorkHour;
    }

    public Double getApproximateInstallHour() {
        return approximateInstallHour;
    }

    public void setApproximateInstallHour(Double approximateInstallHour) {
        this.approximateInstallHour = approximateInstallHour;
    }

    public Double getApproximateMaterialPrice() {
        return approximateMaterialPrice;
    }

    public void setApproximateMaterialPrice(Double approximateMaterialPrice) {
        this.approximateMaterialPrice = approximateMaterialPrice;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }
}

