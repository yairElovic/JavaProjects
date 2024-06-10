package Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import App.Weapon;

public class WeaponTest {
 Weapon w;
	@Before
	public void setWeapon() {
		w = new Weapon("test", "test", 20, 20, 20);
	}
	
	@Test
	public void testWeapon() {
		Assert.assertNotNull(w);
	}

	@Test
	public void testGetDamage() {
		Assert.assertEquals(20,w.getDamage());
	}

}
