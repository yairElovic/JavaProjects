package App;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Health extends SalableProduct{
	private int healthValue;
	@JsonCreator
	public Health(@JsonProperty("name")String name, 
			@JsonProperty("description")String description, 
			@JsonProperty("price")double price, 
			@JsonProperty("quantity")int quantity, 
			@JsonProperty("healthValue")int healthValue) {
		
		super(name,description,price,quantity);
		if(healthValue > 0) {
			this.healthValue = healthValue;
		}else {
			throw new IllegalArgumentException("healthValue must be a positive number");
		}
	}
	
	public int getHealthValue() {
		return this.healthValue;
	}
}	
