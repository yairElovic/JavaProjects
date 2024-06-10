package App;

import java.util.Scanner;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.io.File;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreFront {
	private InventoryManager storeInventory;
	private ShoppingCart cart;
	private Scanner scanner;
	private String storeName;
	private final int PORT = 12346;
	private ServerSocket serverSocket; 
	private Socket clientSocket;
	private PrintWriter out; 
	private BufferedReader in; 
	/**
	 * Store front constructor, initializes inventory and the scanner
	 * @throws IOException 
	 * 
	 */
	public StoreFront(String name) throws IOException {
		this.storeInventory = new InventoryManager();
		this.cart = new ShoppingCart();
		this.storeName = name;
		this.scanner = new Scanner(System.in);
		AdminFunctionsThread thread = new AdminFunctionsThread(this.PORT, this);
		thread.start();
		//AdminFunctionsClientThread clientThread = new AdminFunctionsClientThread(PORT,  IP);
		
	}

	/**
	 * returns the name of the store
	 * 
	 * @return String
	 */
	private String getStoreName() {
		return this.storeName;
	}

	/**
	 * Calls the store inventory display
	 */
	private void showStoreInventory() {
		this.displayProducts(this.storeInventory.getInventoryProducts());
		this.storeMenu();
	}
	/**
	 * Initializes the server and handles the responses
	 * @throws IOException
	 */
	public void initServer() throws IOException {
		//System.out.println("Waiting for a Client connection ");
		this.serverSocket = new ServerSocket(this.PORT);
		this.clientSocket = serverSocket.accept();
		ObjectMapper objectMapper = new ObjectMapper();
		
		//System.out.println("Recieved a client connection on port" + clientSocket.getLocalPort());
		
		this.out = new PrintWriter(clientSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		out.println(this.getStoreName());
		
		String inputLine; 
		while ((inputLine = in.readLine()) != null) {
			if(inputLine.startsWith("U")) {
				this.replenishInventory(inputLine.substring(2));
			}else if(inputLine.startsWith("R")){
				objectMapper.writeValue(new File(inputLine.substring(2)),this.storeInventory.getInventoryProducts());
			}else if(inputLine.equals("Q")) {
				break;
			}
		}
		in.close();
        out.close();
        clientSocket.close();
	}

	/**
	 * Store role selector
	 */
	public void selectRole() {
		System.out.println("Welcome to " + this.getStoreName() + " please select an option below:");
		System.out.println("1) Manager Functions");
		System.out.println("2) Shopper Functions");
		System.out.println("9) End your session");
		int selected = scanner.nextInt();
		if (selected == 1)
			this.managerMenu();
		else if (selected == 2)
			this.storeMenu();
		else
			try {
				this.leaveMessage();
			} catch (IOException e) {
				e.printStackTrace();
			}

	}

	/**
	 * Menu that contains manager functions
	 */
	private void managerMenu() {
		System.out.println("Welcome to the Manager menu at " + this.getStoreName() + " please select an option below:");
		boolean keepMenu = true;
		while (keepMenu) {
			// change
			System.out.println("1) Display Inventory");
			System.out.println("2) Return selected inventory");
			System.out.println("3) Return all inventory");
			System.out.println("4) Add products to inventory");
			System.out.println("5) Change inventory display settings");
			System.out.println("6) Return to previous menu");
			int selectedItem = scanner.nextInt();
			if (selectedItem == 1) {
				this.displayProducts(this.storeInventory.getInventoryProducts());
			}else if (selectedItem == 2) {
				this.returnInventory();
			} else if (selectedItem == 3) {
				this.storeInventory.clearAll();
				System.out.println("All inventory has been succesfully removed");
			} else if (selectedItem == 4) {
				this.addProductsToInventory();
			} else if (selectedItem == 5) {
				this.changeInventoryDisplaySettings();
			} else if (selectedItem == 6){
				keepMenu = false;
			}
		}
		this.selectRole();
	}

	/**
	 * This method is used to change the inventories display settings
	 */
	private void changeInventoryDisplaySettings() {
		System.out.println("1) Sort by name");
		System.out.println("2) Sort by price");
		int sortType = scanner.nextInt();
		if(sortType == 1) {
			this.storeInventory.setSortByName(true);
		}else {
			this.storeInventory.setSortByName(false);
		}
		
		System.out.println("1) sort Aescending");
		System.out.println("2) sort Dscending");
		int reverse = scanner.nextInt();
		if(reverse == 1) {
			this.storeInventory.setReverseOrder(false);
		}else {
			this.storeInventory.setReverseOrder(true);
		}
		this.storeInventory.sortProducts();
		System.out.println("Your sort settings have now been saved");
	}

	/**
	 * Displays the main menu of the store
	 */
	private void storeMenu() {
		if (this.storeInventory.getInventoryProducts().isEmpty()) {
			System.out.println(this.getStoreName() + " is all sold out, please come back later to try shopping again");
			if (scanner.hasNext())
				scanner.nextLine();
			scanner.close();
		} else {
			boolean keepMenu = true;
			while (keepMenu) {
				System.out.println("1) Shop for products");
				System.out.println("2) Return products");
				System.out.println("3) View current inventory");
				System.out.println("4) Return to previous menu");
				System.out.println("9) End your session");
				int selectedItem = scanner.nextInt();
				if (selectedItem == 1) {
					this.shopForSalableProducts();
				} else if (selectedItem == 2) {
					this.returnSalableProducts();
				} else if (selectedItem == 3) {
					this.showStoreInventory();
				} else if (selectedItem == 4) {
					this.selectRole();
				} else if (selectedItem == 9)
					try {
						this.leaveMessage();
						break;
					} catch (IOException e) {
						e.printStackTrace();
					}
				break;
			}
		}
	}

	/**
	 * contains the leave message for customers when they have completed their
	 * transaction
	 * @throws IOException 
	 */
	private void leaveMessage() throws IOException {
		System.out.print("Thank you for shopping with us come again soon!");
		if(this.in != null) 		
			this.in.close();

		if(this.out != null)
			this.out.close();
		
		if(this.clientSocket != null)
			this.clientSocket.close();
		
		if(this.serverSocket != null)
			this.serverSocket.close();
	}

	/**
	 * this method is the controller for the shopping for salable products
	 * functionality
	 */
	private void shopForSalableProducts() {
		// display shopping experience
		System.out.println();
		int nextAction = 1;
		boolean continueDisplay = true;
		String action = "purchase";

		while (continueDisplay) {
			if (nextAction == 1) {
				addToCartUI(action);
			} else if (nextAction == 2) {
				this.displayProducts(this.cart.getCartProducts());
				System.out.println();
			} else if (nextAction == 3) {
				try {
					storeInventory.removeProduct(this.cart.getCartProducts());
					this.cart.clearAll();
					System.out.println("You have succesfully purchased your products!");
					System.out.println();
					break;
				} catch (IllegalArgumentException e) {
					System.out.println("Error! " + e.getMessage());
					this.displayEndOfAdd(action);
				}
			} else if (nextAction == 4) {
				this.editCart();
			}else if (nextAction == 5) {
				this.changeCartDisplaySettings();
			} else if (nextAction == 6) {
				this.cancelShopping();
				break;
			}
			nextAction = displayEndOfAdd("purchase");
		}
		this.selectRole();
		

	}

	/**
	 * This method is the controller for the return salable products functionality
	 */
	private void returnSalableProducts() {
		int nextAction = 1;
		boolean continueDisplay = true;

		while (continueDisplay) {
			if (nextAction == 1) {
				addToCartUI("return");
			} else if (nextAction == 2) {
				this.displayProducts(this.cart.getCartProducts());
			} else if (nextAction == 3) {
				storeInventory.addProduct(this.cart.getCartProducts());
				this.cart.clearAll();
				System.out.println("You have succesfully returned your products!");
				System.out.println();
				break;
			} else if (nextAction == 4) {
				this.editCart();
			}else if (nextAction == 5) {
				this.changeCartDisplaySettings();
			} else if (nextAction == 6) {
				this.cancelShopping();
				break;
			}
			nextAction = displayEndOfAdd("return");
		}
		this.selectRole();

	}
	
	/**
	 * This method is used to change the carts display settings
	 * 
	 */
	private void changeCartDisplaySettings() {
		System.out.println("1) Sort by name");
		System.out.println("2) Sort by price");
		int sortType = scanner.nextInt();
		if(sortType == 1) {
			this.cart.setSortByName(true);
		}else {
			this.cart.setSortByName(false);
		}
		
		System.out.println("1) sort Aescending");
		System.out.println("2) sort Descending");
		int reverse = scanner.nextInt();
		if(reverse == 1) {
			this.cart.setReverseOrder(false);
		}else {
			this.cart.setReverseOrder(true);
		}
		this.cart.sortProducts();
		System.out.println("Your sort settings have now been saved");
	}

	/**
	 * Used to replenish the inventory held in a store front
	 * 
	 * @param products
	 */
	public void replenishInventory(String products) throws IOException {
		this.storeInventory.createProductsFromFile(products);
	}

	/**
	 * This method is the helper for the cancel shopping functionality
	 */
	private void cancelShopping() {
		System.out.println("You have succesfully canceled this session");
		this.cart.clearAll();
	}

	/**
	 * This method is the helper for the main action display menu for both shopping
	 * and returning products
	 * 
	 * @param action
	 * @return int
	 */
	private int displayEndOfAdd(String action) {
		System.out.println("1) Add more enter to cart");
		System.out.println("2) View your cart");
		System.out.println("3) " + action + " cart");
		System.out.println("4) Edit cart");
		System.out.println("5) Change cart display settings");
		System.out.println("6) Cancel cart");
		return scanner.nextInt();
	}

	/**
	 * this method allows users to edit the contents of their cart
	 */
	private void editCart() {
		boolean continueEditing = true;
		while (continueEditing) {
			if (this.cart.getCartProducts().isEmpty()) {
				System.out.println("Your cart is empty, to edit the cart please add more products");
				continueEditing = false;
				break;
			}
			System.out.printf("%32s", "Your Cart");
			this.displayProducts(this.cart.getCartProducts());
			System.out.print("Enter the Id of the product you would like to edit: ");
			int productNum = scanner.nextInt() - 1;
			System.out.println();
			System.out.println("1) edit quantity of product");
			System.out.println("2) remove product from cart");
			int nextStep = scanner.nextInt();
			if (nextStep == 1) {
				System.out.print("Enter the new quantity you would like to have: ");
				int quantity = scanner.nextInt();
				this.cart.getProductAtIndex(productNum).setQuantity(quantity);
				System.out.println();
				System.out.println("The quantity of this product has been succesfully updated");
				continueEditing = this.continueAdding("editing");
			} else if (nextStep == 2) {
				this.cart.removeProduct(this.cart.getProductAtIndex(productNum));
				System.out.println("This product has been succesfully removed from your cart");
				continueEditing = this.continueAdding("editing");
			}
		}
	}

	/**
	 * This method is used to add products to the shopping cart, and is used as a
	 * helper for the shopForSalableProducts method
	 * 
	 * @param action
	 */
	private void addToCartUI(String action) {
		this.displayProducts(this.storeInventory.getInventoryProducts());
		boolean continueAdding = true;

		while (continueAdding) {
			System.out.print("Enter the id of the product you would like to " + action + ": ");
			int currIndex = scanner.nextInt() - 1;
			SalableProduct product = new SalableProduct(storeInventory.getProductAtIndex(currIndex));
			System.out.println();
			System.out
					.print("Select the quantity of " + product.getName() + " that you would like to " + action + ": ");
			product.setQuantity(scanner.nextInt());
			this.cart.addProduct(product);
			scanner.nextLine();
			continueAdding = continueAdding("adding");
		}
	}

	/**
	 * This method is used to return product from inventory
	 */
	private void returnInventory() {
		this.displayProducts(this.storeInventory.getInventoryProducts());
		boolean continueAdding = true;

		while (continueAdding) {
			System.out.print("Enter the id of the product you would like to return: ");
			int currIndex = scanner.nextInt() - 1;
			this.storeInventory.removeProduct(storeInventory.getProductAtIndex(currIndex));
			System.out.println("This product has been successfully removed from inventory");
			continueAdding = continueAdding("returning");
		}
		this.managerMenu();
	}

	/**
	 * Adds products to inventory
	 */
	private void addProductsToInventory() {
		boolean keepRunning = true;
		while (keepRunning) {
			System.out.println("Select the type of product you would like to add");
			System.out.println("1) Weapon");
			System.out.println("2) Armor");
			System.out.println("3) Health");
			int selectedItem = scanner.nextInt();
			String itemType = "";
			String levelStr = "";
			switch (selectedItem) {
				case 1:
					itemType = "Weapon";
					levelStr = "Damage";
					break;
				case 2:
					itemType = "Armor";
					levelStr = "Strength";
					break;
				case 3:
					itemType = "Health";
					levelStr = "Health Value";
					break;
			}
			keepRunning = this.createProduct(itemType, levelStr);
		}

	}

	/**
	 * creates products for adding to inventory manager
	 * 
	 * @param type
	 * @param levelName
	 * @return boolean
	 */
	private boolean createProduct(String type, String levelName) {
		scanner.nextLine();
		System.out.print("Enter the name of the " + type + " you would like to add: ");
		String name = scanner.nextLine();
		System.out.print("Enter a brief decription of your " + type + ": ");
		String description = scanner.nextLine();
		System.out.print("Enter the price per unit of your " + type + ": ");
		double price = scanner.nextDouble();
		System.out.print("Enter the quantity of your " + type + ": ");
		int quantity = scanner.nextInt();
		System.out.print("Enter the " + levelName + " of your " + type + ": ");
		int level = scanner.nextInt();
		if (type.equals("Weapon")) {
			this.storeInventory.addProduct(new Weapon(name, description, price, quantity, level));
		} else if (type.equals("Armor")) {
			this.storeInventory.addProduct(new Armor(name, description, price, quantity, level));
		} else if (type.equals("Health")) {
			this.storeInventory.addProduct(new Health(name, description, price, quantity, level));
		}
		System.out.println("This product has been successfully added to inventory");
		return continueAdding("adding");

	}

	/**
	 * This method is a helper for the continue adding functionality and is
	 * referenced at the end of each add to cart operation both for return
	 * 
	 * @return boolean
	 */
	private boolean continueAdding(String action) {
		System.out.println("Continue " + action + " products? Y/N");
		String nextAction = scanner.next();
		return nextAction.equals("Y") || nextAction.equals("y") ? true : false;
	}

	/**
	 * This method allows users to see their products in a nice fashion
	 * 
	 * @param products
	 */
	private void displayProducts(ArrayList<SalableProduct> products) {
		System.out.println();
		int counter = 1;
		System.out.println("---------------------------------------------------------------------------");
		System.out.printf("| %-10s | %-20s | %-10s | %-9s | %-10s |\n", "Identifier", "Product Name", "Quantity",
				"Price", "Type");
		System.out.println("---------------------------------------------------------------------------");
		for (SalableProduct p : products) {
			System.out.printf("| %-10s | %-20s | %-10d | $%-8.2f | %-10s |\n", counter, p.getName(), p.getQuantity(),
					p.getPrice(), p.getClass().getSimpleName());
			counter++;
		}
		System.out.println();
	}

}
