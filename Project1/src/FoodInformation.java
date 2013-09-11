import java.io.Serializable;
import java.math.BigDecimal;


public class FoodInformation implements Serializable {
	
	private String name;
	private BigDecimal price;
	private int calories;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getCalories() {
		return calories;
	}
	public void setCalories(int calories) {
		this.calories = calories;
	}
	public FoodInformation(String name, BigDecimal price, int calories) {
		super();
		this.name = name;
		this.price = price.setScale(2, BigDecimal.ROUND_UP);
		this.calories = calories;
	}
	
	public FoodInformation() {
		super();
		this.name = null;
		this.price = null;
		this.calories = 0;
	}
	
	

}
