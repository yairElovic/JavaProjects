package Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import App.SalableProduct;
import App.InventoryManager;

public class InventoryManagerTest {
	
	InventoryManager im;
	ArrayList<SalableProduct> testProducts;
	
	@Before
	public void test() throws IOException {
		im = new InventoryManager();
		testProducts = TestDataFactory.createProducts();
		im.addProduct(testProducts.get(0));
		im.addProduct(testProducts.get(1));
		im.addProduct(testProducts.get(2));
	}
	
	@Test
	public void testInventoryManager() {
		Assert.assertNotNull(im);
	}
	
	@Test
	public void testAddProductAndGetProductAtIndex() {
		Assert.assertEquals(testProducts.get(1),im.getProductAtIndex(0));
	}
	
	@Test
	public void testGetRemoveClearProducts() {
		Assert.assertEquals(3, im.getInventoryProducts().size());
		im.removeProduct(testProducts.get(0));
		Assert.assertEquals(2, im.getInventoryProducts().size());
		im.clearAll();
		Assert.assertTrue(im.getInventoryProducts().isEmpty());
	}

	@Test
	public void testAddProductsBulk() {
		im.addProduct(testProducts);
		Assert.assertFalse(im.getInventoryProducts().isEmpty());
	}

	@Test
	public void testCreateProductsFromFile() throws IOException {
		im.createProductsFromFile("productsLoad.json");
		Assert.assertFalse(im.getInventoryProducts().isEmpty());
	}

}
