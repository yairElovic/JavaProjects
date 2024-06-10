package App;

import java.io.IOException;

public class MilestoneRunner {

	public static void main(String[] args) throws IOException {
		StoreFront currentStore = new StoreFront("Yair's Weapons Depot");
		currentStore.replenishInventory("productsLoad.json");
		displayMenu(currentStore);
	}

	/**
	 * This is the main menu for the store and controls the flow of the application
	 * 
	 * @param calledStore
	 */
	public static void displayMenu(StoreFront calledStore) {
		calledStore.selectRole();
	}
}
