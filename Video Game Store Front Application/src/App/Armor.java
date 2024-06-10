package App;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Armor extends SalableProduct{
	private int strength;
	@JsonCreator
	public Armor(@JsonProperty("name")String name, 
			@JsonProperty("description")String description, 
			@JsonProperty("price")double price, 
			@JsonProperty("quantity")int quantity, 
			@JsonProperty("strength") int strength) {
		super(name,description,price,quantity);
		if(strength > 0) {
			this.strength = strength ;
		}else {
			throw new IllegalArgumentException("Strength must be a positive number");
		}
	}
	
	public int getStrength() {
		return this.strength;
	}
}
