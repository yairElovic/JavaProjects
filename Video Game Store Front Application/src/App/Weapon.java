package App;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Weapon extends SalableProduct{
	private int damage;
	@JsonCreator
	public Weapon(  @JsonProperty("name")String name, 
					@JsonProperty("description")String description, 
					@JsonProperty("price")double price, 
					@JsonProperty("quantity")int quantity, 
					@JsonProperty("damage")int damage) {
		super(name, description, price, quantity);
		if(damage > 0) {
			this.damage = damage;
		}else {
			throw new IllegalArgumentException("Damage must be greater than 0");
		}
	}
	
	public int getDamage() {
		return this.damage;
	}
}
