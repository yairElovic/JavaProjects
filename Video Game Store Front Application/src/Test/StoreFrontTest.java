package Test;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import App.StoreFront;

public class StoreFrontTest {
	StoreFront sf;
	
	@Before
	public void setupTest() throws IOException {
		sf = TestDataFactory.createStoreFront();
	}

	@Test
	public void testStoreFront() {
		Assert.assertNotNull(sf);
	}

}
