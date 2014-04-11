package ch.steponline.product.model;

import ch.steponline.core.model.AbstractVersionedEntity;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Group of Products
 * User: Roland Bättig
 * Date: 03.04.14
 * Time: 20:31
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(name = "ProductStyling",schema="product")
@XmlRootElement
@XmlAccessorType(value = XmlAccessType.PROPERTY)
@Audited
@AuditTable(value="ProductStyling_AUDIT",schema = "audit")
public class ProductStyling extends AbstractVersionedEntity<Long,ProductStyling> {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
