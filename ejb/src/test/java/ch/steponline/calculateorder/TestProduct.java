package ch.steponline.calculateorder;

import ch.steponline.product.model.Product;
import ch.steponline.product.model.ProductGroup;
import ch.steponline.persistence.PersistenceException;
import ch.steponline.product.dao.ProductDAO;
import ch.steponline.service.ValidatorService;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;


import javax.validation.ConstraintViolation;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Roland
 * Date: 08.04.14
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */

public class TestProduct  {
    private static Weld weld;
    private static WeldContainer weldContainer;
    private ProductDAO productDAO;

    @BeforeClass
    public static void setupClass() {
        weld = new Weld();
        weldContainer = weld.initialize();
        Connection c;

            try {
                Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
                c = DriverManager.getConnection("jdbc:derby:testdb;create=true", "root", "root");
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            try {
                createSchema("audit",c);
                createSchema("product", c);
                createSchema("core",c);
                createSequenceTable("product.Product_Sequence",c);
            } catch (Exception e) {
                // Do nothing in this case the schema or table exists
            }


    }

    private static void createSequenceTable(String name,Connection connection) {
        String sql="CREATE TABLE "+name+"(Sequence_Name varchar(50),next_val numeric(19,0))";
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute(sql);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createSchema(String schema,Connection connection) {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.execute("create schema "+schema);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @AfterClass
    public static void teardownClass() {
        weld.shutdown();
    }

    private ProductDAO getProductDAO() {
        if (productDAO==null) {
            productDAO = weldContainer.instance().select(ProductDAO.class).get();
        }
        return productDAO;
    }

    public void productInsert() throws PersistenceException{
        ProductDAO productDAO = getProductDAO();
        ProductGroup productGroup=productDAO.createProductGroup();
        productGroup.setLongName("de","TestProdukt Gruppe");
        productGroup.setAbbreviation("de","Test");
        Set<ConstraintViolation<ProductGroup>> constraints= ValidatorService.validate(productGroup);
        Assert.assertTrue(constraints.isEmpty());
        productGroup=productDAO.save(productGroup);

        Product product=productDAO.createProduct();
        product.setNumber("test");
        product.setAbbreviation("de","test");
        product.setLongName("de","Test Produkt");
        product.setProductGroup(productGroup);
        product.setApproximateInstallHour(0.0);
        product.setApproximateMachineHour(1.0);
        product.setApproximateMaterialPrice(150.0);
        product.setApproximateWorkHour(0.5);
        product.setApproximateWorkPrepareHour(0.5);

        Set<ConstraintViolation<Product>> constraintsProduct= ValidatorService.validate(product);
        Assert.assertTrue(constraintsProduct.isEmpty());

        product=productDAO.save(product);
    }

    @Test()
    public void dummyTest() {
        try {
            productInsert();
        } catch (PersistenceException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        ProductDAO productDAO = getProductDAO();
        List<Product> productList=productDAO.listProducts("de");
        Assert.assertTrue(!productList.isEmpty());
    }
}
