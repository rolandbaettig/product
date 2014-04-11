package ch.steponline.product.model;

import ch.steponline.product.model.ProductGroupTextContainer;
import ch.steponline.core.model.AbstractEntityTextContainer;
import ch.steponline.core.model.AbstractTextContainer;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 03.04.14
 * Time: 20:30
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name="ProductGroup",
        schema="product",
        indexes = {
        @Index(name="IDX_ProductGroup_Parent",columnList = "ParentId")
})
@NamedQueries(value={
        @NamedQuery(name="ProductGroupByName",query="select p from ProductGroup p join fetch p.textContainers tx where tx.id.languageIsoCode=:language and (tx.shortName like :name or tx.longName like :name or tx.abbreviation like :name) order by tx.abbreviation")
}

)
@FilterDef(name="LanguageFilter",
        parameters=@ParamDef( name="languageParam", type="string" ) )
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@Audited
@AuditTable(value="ProductGroup_AUDIT",schema = "audit")
public class ProductGroup extends AbstractEntityTextContainer<Long,ProductGroup> {
    @Id
    private Long id;

    @ManyToOne(optional = true,fetch = FetchType.LAZY)
    @JoinColumn(name="ParentId",referencedColumnName = "Id",
            foreignKey = @ForeignKey(name="FK_ProductGroup_Parent",value = ConstraintMode.CONSTRAINT,
                                    foreignKeyDefinition = "FOREIGN KEY (ParentId) REFERENCES ProductGroup (Id) ON DELETE CASCADE"))
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private ProductGroup parent;

    @OneToMany(mappedBy = "productGroup",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
    private Set<Product> products=new HashSet<Product>();

    @OneToMany(mappedBy = "productGroup",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.EAGER,targetEntity = ProductGroupTextContainer.class)
    @org.hibernate.annotations.Filter(
            name = "LanguageFilter",
            condition="id.languageIsoCode = :languageParam"
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Set<AbstractTextContainer> textContainers=new HashSet<AbstractTextContainer>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProductGroup getParent() {
        return parent;
    }

    public void setParent(ProductGroup parent) {
        this.parent = parent;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product) {
        if (product.getProductGroup()==null ||
                !product.getProductGroup().equals(this)) {
            product.setProductGroup(this);
        }
        if (!products.contains(product)) {
            products.add(product);
        }
    }

    public void removeProduct(Product product) {
        if (product.getProductGroup()!=null &&
                product.getProductGroup().equals(this)) {
            products.remove(product);
            product.setProductGroup(null);
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
        ProductGroupTextContainer productGroupTextContainer= new ProductGroupTextContainer(this,language);
        getTextContainers().add(productGroupTextContainer);
        return productGroupTextContainer;
    }

}
