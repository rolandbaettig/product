package ch.steponline.product.dao;

import ch.steponline.persistence.AbstractDAO;
import ch.steponline.persistence.PersistenceException;
import ch.steponline.product.model.Product;
import ch.steponline.product.model.ProductGroup;
import org.hibernate.Session;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 16:04
 * To change this template use File | Settings | File Templates.
 */
public class ProductDAO extends AbstractDAO {

    public List<Product> listProducts(String language) {
        ((Session)getEntityManager().getDelegate()).enableFilter("LanguageFilter").setParameter("languageParam", language);
        return getEntityManager()
                .createQuery("select p from Product p")
                .getResultList();
    }

    public Product save(Product product) throws PersistenceException {
        return saveEntity(product);
    }

    public ProductGroup save(ProductGroup productGroup) throws PersistenceException{
        return saveEntity(productGroup);
    }

    public Product createProduct() {
        Product product=new Product();
        product.setUnsaved(true);
        product.setId(generateIdentifier(product));
        return product;
    }

    public ProductGroup createProductGroup() {
        ProductGroup productGroup=new ProductGroup();
        productGroup.setUnsaved(true);
        productGroup.setId(generateIdentifier(productGroup));
        return productGroup;
    }






}
