package Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import App.Health;

public class HealthTest {
	
	Health h;
	
	@Before
	public void setHealth() {
		h = new Health("test", "test", 20, 20, 20);
	}
	@Test
	public void testHealth() {
		Assert.assertNotNull(h);
	}

	@Test
	public void testGetHealthValue() {
		Assert.assertEquals(20, h.getHealthValue());
	}

}
