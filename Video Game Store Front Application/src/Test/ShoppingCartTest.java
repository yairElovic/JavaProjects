package Test;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import App.ShoppingCart;
import java.util.ArrayList;
import App.SalableProduct;

public class ShoppingCartTest {
	ShoppingCart sc;
	ArrayList<SalableProduct> testProducts;
	
	@Before
	public void test() throws IOException {
		sc = new ShoppingCart();
		testProducts = TestDataFactory.createProducts();
		sc.addProduct(testProducts.get(0));
		sc.addProduct(testProducts.get(1));
		sc.addProduct(testProducts.get(2));
	}
	
	@Test
	public void testShoppingCart() {
		Assert.assertNotNull(sc);
	}
	
	@Test
	public void testAddProductAndGetProductAtIndex() {
		Assert.assertEquals(testProducts.get(1),sc.getProductAtIndex(0));
	}
	
	@Test
	public void testGetRemoveClearProducts() {
		Assert.assertEquals(3, sc.getCartProducts().size());
		sc.removeProduct(testProducts.get(0));
		Assert.assertEquals(2, sc.getCartProducts().size());
		sc.clearAll();
		Assert.assertTrue(sc.getCartProducts().isEmpty());
	}
	
	

}
