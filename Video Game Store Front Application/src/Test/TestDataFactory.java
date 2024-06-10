package Test;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;

import App.SalableProduct;
import App.StoreFront;

public class TestDataFactory {

	@BeforeClass
	public static StoreFront createStoreFront() throws IOException{
		StoreFront store = new StoreFront("Yairs Test Data");
		return store;
	}
	
	@Before
	public static ArrayList<SalableProduct> createProducts() throws IOException{
		SalableProduct sp1 = new SalableProduct("Dog Toys", "way to play with your buddy", 10, 50);
		SalableProduct sp2 = new SalableProduct("Dog food", "way to feed your buddy", 75, 50);
		SalableProduct sp3 = new SalableProduct("Leash", "Way to walk your dog", 25, 50);
		SalableProduct sp4 = new SalableProduct("Dog beds", "comfy place for your dog to sleep", 100, 50);
		SalableProduct sp5 = new SalableProduct("Dog training books", "books to help train your friend", 5, 75);
		ArrayList<SalableProduct> rtnProducts= new ArrayList<SalableProduct>();
		rtnProducts.add(sp1);
		rtnProducts.add(sp2);
		rtnProducts.add(sp3);
		rtnProducts.add(sp4);
		rtnProducts.add(sp5);

		return rtnProducts;
	}
	

}
