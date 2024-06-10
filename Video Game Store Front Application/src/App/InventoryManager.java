package App;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;

public class InventoryManager implements ProductActionsInterface {
	private ArrayList<SalableProduct> inventoryProducts;
	private boolean sortByName = true;
	private boolean reverseOrder = false;

	/**
	 * Constructor no params
	 */
	public InventoryManager() {
		this.inventoryProducts = new ArrayList<SalableProduct>();
	}

	/**
	 * method to return all products in inventory
	 * 
	 * @return ArrayList<SalableProduct>
	 */
	public ArrayList<SalableProduct> getInventoryProducts() {
		sortProducts();
		return this.inventoryProducts;
	}

	/**
	 * adds products from shopping cart to inventory
	 * overloaded add product method
	 * 
	 * @param inputProducts
	 */
	public void addProduct(ArrayList<SalableProduct> inputProducts) {
		for (SalableProduct p : inputProducts) {
			for (SalableProduct innerP : this.inventoryProducts) {
				if (innerP.getProductExternalId() == p.getProductExternalId()) {
					innerP.setQuantity(p.getQuantity() + innerP.getQuantity());
				}
			}
		}
		sortProducts();
	}

	/**
	 * removes the contents of a shopping cart from inventory
	 * overloaded remove product method
	 * 
	 * @param deleteProducts
	 */
	public void removeProduct(ArrayList<SalableProduct> deleteProducts) {
		for (SalableProduct p : deleteProducts) {
			for (SalableProduct innerP : this.inventoryProducts) {
				if (innerP.getProductExternalId() == p.getProductExternalId()) {
					if (innerP.getQuantity() >= p.getQuantity()) {
						innerP.setQuantity(innerP.getQuantity() - p.getQuantity());
					} else {
						throw new IllegalArgumentException(
								"You cannot purchase more than we have available in the store");
					}
				}
			}
		}
		sortProducts();
	}

	/**
	 * Method to add products to inventory in bulk
	 * 
	 * @param products
	 */
	public void createProducts(ArrayList<SalableProduct> products) {
		this.inventoryProducts.addAll(products);
	}

	/**
	 * Ability to create inventory from a provided file string
	 * 
	 * @param fileName 
	 */
	public void createProductsFromFile(String fileName) throws IOException {
		try {
			File file = new File(fileName);
			Scanner scanner = new Scanner(file);

			while (scanner.hasNext()) {
				String json = scanner.nextLine();
				ObjectMapper objectMapper = new ObjectMapper();
				SalableProduct sp = objectMapper.readValue(json, SalableProduct.class);
				this.inventoryProducts.add(sp);
				
			}
			scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Exception: " + e.getMessage());
		}
	}

	/**
	 * Returns the product at different indexes of the inventory list
	 * 
	 * @param index
	 * @return SalableProduct
	 */
	public SalableProduct getProductAtIndex(int index) {
		return inventoryProducts.get(index);
	}

	@Override
	public void addProduct(SalableProduct p) {
		this.inventoryProducts.add(p);
		sortProducts();

	}

	@Override
	public void removeProduct(SalableProduct p) {
		this.inventoryProducts.remove(p);
		sortProducts();
	}

	@Override
	public void clearAll() {
		this.inventoryProducts.clear();
	}
	
	/**
	 * Sorts products based on the sortByName and reverse Order variables
	 */
	@Override
	public void sortProducts() {
		Collections.sort(this.inventoryProducts, new SalableProductsComparator(this.sortByName, this.reverseOrder));
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
