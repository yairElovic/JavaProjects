package App;

import java.util.ArrayList;
import java.util.Collections;

public class ShoppingCart implements ProductActionsInterface {
	private ArrayList<SalableProduct> cartProducts;
	private boolean sortByName = true;
	private boolean reverseOrder = false;
	
	public ShoppingCart() {
		this.cartProducts = new ArrayList<SalableProduct>();
	}

	/**
	 * getter for cart products
	 * 
	 * @return ArrayList<SalableProduct>
	 */
	public ArrayList<SalableProduct> getCartProducts() {
		this.sortProducts();
		return cartProducts;
	}

	/**
	 * method to add products to the shopping cart
	 * 
	 * @param product
	 */
	@Override
	public void addProduct(SalableProduct product) {
		this.cartProducts.add(product);
		this.sortProducts();
	}

	/**
	 * method to remove products from the shopping cart
	 * @param product
	 */
	@Override
	public void removeProduct(SalableProduct product) {
		if (this.cartProducts.contains(product)) {
			this.cartProducts.remove(product);
		}
		this.sortProducts();
	}

	/**
	 * gets the product at a certain index in the cart
	 * 
	 * @param index
	 * @return SalableProduct
	 */
	public SalableProduct getProductAtIndex(int index) {
		return cartProducts.get(index);
	}

	/**
	 * clears the full contents of a cart
	 */
	@Override
	public void clearAll() {
		this.cartProducts.clear();
	}
	
	/**
	 * Sorts products based on the sortByName and reverse Order variables
	 */
	
	@Override
	public void sortProducts() {
		Collections.sort(this.cartProducts, new SalableProductsComparator(this.sortByName, this.reverseOrder));
	}

	/**
	 * @param sortByName the sortByName to set
	 */
	public void setSortByName(boolean sortByName) {
		this.sortByName = sortByName;
	}
	
	/**
	 * @param reverseOrder the reverseOrder to set
	 */
	public void setReverseOrder(boolean reverseOrder) {
		this.reverseOrder = reverseOrder;
	}

}
