package Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ArmorTest.class, HealthTest.class, InventoryManagerTest.class, ShoppingCartTest.class,
		StoreFrontTest.class, WeaponTest.class })
public class AllTests {

}
