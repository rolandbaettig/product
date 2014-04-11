package ch.steponline.product.model;

import ch.steponline.core.model.AbstractTextContainer;
import ch.steponline.product.model.ProductGroup;
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
@Table(name="ProductGroupTextContainer",
        schema="product",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"SourceId","LanguageIsoCode"})},
        indexes = {
                @Index(name="IDX_ProductGroupTextContainer_ProductGroup",
                        columnList = "SourceId"
                ),
                @Index(name="IDX_ProductGroupTextContainer",
                        columnList = "LanguageIsoCode"
                )
        }
)
@Audited
@AuditTable(value="ProductGroupTextContainer_AUDIT",schema = "audit")

public class ProductGroupTextContainer extends AbstractTextContainer {

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="SourceId",referencedColumnName = "Id",
            insertable = false,updatable = false,
            foreignKey = @ForeignKey(name="FK_ProductGroupTextContainer",foreignKeyDefinition ="FOREIGN KEY (SourceId) REFERENCES ProductGroup (Id) ON DELETE CASCADE",value = ConstraintMode.CONSTRAINT)
    )
    @OptimisticLock(excluded = true)
    private ProductGroup productGroup;

    public ProductGroupTextContainer(){};

    public ProductGroupTextContainer(ProductGroup productGroup, String language) {
        super(productGroup.getId(), language);
        this.productGroup=productGroup;
    }

    public ProductGroup getProductGroup() {
        return productGroup;
    }

    public void setProductGroup(ProductGroup productGroup) {
        this.productGroup = productGroup;
    }
}
