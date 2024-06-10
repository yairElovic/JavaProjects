package Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import App.Armor;
import App.SalableProduct;

public class ArmorTest {

	Armor a;
	
	@Before
	public void createProduct() {
		a = new Armor("test", "TEST", 20, 20, 10);
	}
	@Test
	public void testArmor() {
		Assert.assertNotNull(a);
	}

	@Test
	public void testGetStrength() {
		Assert.assertEquals(10, a.getStrength());
	}


	@Test
	public void testSalableProduct() {
		SalableProduct p = new SalableProduct(a);
		Assert.assertNotNull(p);
	}

	@Test
	public void testGetQuantity() {
		Assert.assertEquals(20,a.getQuantity());
	}

	@Test
	public void testSetQuantity() {
		a.setQuantity(10);
		Assert.assertEquals(10, a.getQuantity());
	}

	@Test
	public void testGetName() {
		Assert.assertEquals("test", a.getName());
	}

	@Test
	public void testGetDescription() {
		Assert.assertEquals("TEST", a.getDescription());
	}

	@Test
	public void testGetPrice() {
		Assert.assertEquals(20, a.getPrice(), 0);
	}

	@Test
	public void testGetProductExternalId() {
		Assert.assertNotNull(a.getProductExternalId());
	}

}
