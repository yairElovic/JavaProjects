package App;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Weapon.class, name = "Weapon"),
    @JsonSubTypes.Type(value = Armor.class, name = "Armor"),
    @JsonSubTypes.Type(value = Health.class, name = "Health") }
)
public class SalableProduct{
	private String name;
	private String description;
	private double price;
	private int quantity;
	private int productExternalId;

	/**
	 * constructor
	 * 
	 * @param name
	 * @param description
	 * @param price
	 * @param quantity
	 */
	public SalableProduct(String name, String description, double price, int quantity) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.quantity = quantity;
		this.productExternalId = generateId();
	}

	public SalableProduct(SalableProduct p) {
		this.name = p.getName();
		this.description = p.getDescription();
		this.price = p.getPrice();
		this.quantity = p.getQuantity();
		this.productExternalId = p.getProductExternalId();
	}

	/**
	 * get quantity of product
	 * 
	 * @return
	 */
	public int getQuantity() {
		return quantity;
	}

	/**
	 * set the quantity of a product
	 * 
	 * @param quantity
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	/**
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * set the description of the product
	 * 
	 * @return String
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * get the price of the product
	 * 
	 * @return double
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * Gets the external Id of the product
	 * 
	 * @return int
	 */
	public int getProductExternalId() {
		return productExternalId;
	}

	/**
	 * Generates a random id for the product being created
	 * 
	 * @return int
	 */
	private int generateId() {
		return (int) (Math.random() * 90000) + 10000;
	}

	/**
	 * comparable override
	 */
//	@Override
//	public int compareTo(SalableProduct p) {
//		return this.getName().toLowerCase().compareTo(p.getName().toLowerCase());
//	}

}
