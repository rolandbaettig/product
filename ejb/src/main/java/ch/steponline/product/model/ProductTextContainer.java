package ch.steponline.product.model;

import ch.steponline.core.model.AbstractTextContainer;
import ch.steponline.product.model.Product;
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
@Table(name="ProductTextContainer",
        schema="product",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId","LanguageIsoCode"})},
        indexes = {
                @Index(name="IDX_ProductTextContainer",
                        columnList = "SourceId"
                ),
                @Index(name="IDX_ProductTextContainer",
                        columnList = "LanguageIsoCode"
                )
        }
)
@Audited
@AuditTable(value="ProductTextContainer_AUDIT",schema = "audit")

public class ProductTextContainer extends AbstractTextContainer {

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="SourceId",referencedColumnName = "Id",
            insertable = false,updatable = false,
            foreignKey = @ForeignKey(name="FK_ProductTextContainer",foreignKeyDefinition ="FOREIGN KEY (SourceId) REFERENCES Product (Id) ON DELETE CASCADE",value = ConstraintMode.CONSTRAINT)
    )
    @org.hibernate.annotations.OptimisticLock(excluded = true)
    private Product product;

    public ProductTextContainer(){};

    public ProductTextContainer(Product product,String language) {
        super(product.getId(),language);
        this.product=product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
