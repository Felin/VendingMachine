import java.io.Serializable;
import java.math.BigDecimal;


/**
 * Holds info about food and its quantity in the machine
 * 
 * 
 */
public class Dispenser implements Serializable {
	private static final int MAX_SIZE = 20;
	
	private FoodInformation foodInfo;
	private int quantity;
	private transient BigDecimal total;
	private transient int sold;
	
	
	public Dispenser(FoodInformation foodInfo, int quantity) {
		super();
		this.foodInfo = foodInfo;
		this.quantity = quantity;
		this.total = new BigDecimal(0);
		this.sold = 0;
	}
	
	public Dispenser() {
		this(null,0);
		this.total = new BigDecimal(0);
	}
	
	String getTotals(){
		return "\nDispenser for \""+foodInfo.getName()+"\" Items sold: "+sold+" Total cash: "+getTotal()+"$";
	}
	
	public BigDecimal getTotal() {
	if (total==null){
		total = new BigDecimal(0);
	}
		return total;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getPrice() {
		return foodInfo.getPrice();
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public String toString(){
		return String.format("%15s - %5s$", foodInfo.getName(), foodInfo.getPrice());
	}
	
	public String getDetailedInfo(){
		return this.toString()+String.format(" - %5d calories", foodInfo.getCalories());
	}
	
	public void purchase(BigDecimal money) throws NotEnoughMoneyException, OutOfStockException {
		if (total==null){
			total = new BigDecimal(0);
		}
		if (money.compareTo(foodInfo.getPrice())<0){
			throw new NotEnoughMoneyException();
		} else { if (quantity==0){
			throw new OutOfStockException();
		} else{
			quantity--;
			sold++;
			total = total.add(
					foodInfo.getPrice());
		    System.out.println("....");
		}
		}
	}

	
	
}
