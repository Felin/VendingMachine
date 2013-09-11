import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class VendingMachine implements Serializable {
	private Dispenser[] dispensers;
	private String machineTitle;
	private BigDecimal money;
	/*
	 * If hourToTurnOff = 5, machine will turn off every day at 5 a.m. for 1
	 * hour, if hourToTurnOff<0 - machine will work all day long
	 */
	private int hourToTurnOff;
	private final static String separator = "=====================\n";

	public VendingMachine(){
		this(null,null,0);
		
	}
	
	public VendingMachine(Dispenser[] data, String title,
			int hourToTurnMachineOff) {
		this.dispensers = data;
		this.machineTitle = title;
		this.money = new BigDecimal(0.0);
		this.hourToTurnOff = hourToTurnMachineOff;
	}

	public String getMenu() {

		String menu = "";
		if (this.isWorking()) {
			if (dispensers != null) {
				for (Dispenser d : dispensers) {
					menu += d.toString() + ";\n";
				}
			}
			return menu;
		} else {
			return "Machine is turned off";
		}
	}
	
	public String getTotal(){
		BigDecimal totals = new BigDecimal(0);
		int itemsTotal = 0;
		String log = separator+machineTitle+"\n"+separator;
		for (Dispenser d: dispensers){
			log+=d.getTotals();
			itemsTotal+=d.getSold();
			totals = totals.add
					(d.getTotal());
		}
		log+="\n"+separator+"\nItems total: "+itemsTotal+" TOTAL: "+totals+"$";
		return log;
	}

	public boolean isWorking() {
		Date date = new Date(); // given date
		Calendar calendar = GregorianCalendar.getInstance(); // creates a new //
																// instance
		calendar.setTime(date); // assigns calendar to given date
		if (calendar.get(Calendar.HOUR_OF_DAY) < hourToTurnOff
				|| calendar.get(Calendar.HOUR_OF_DAY) > hourToTurnOff + 1||hourToTurnOff<0) {
			return true;
		} else {
			return false;
		}
	}

	public String getDetailedMenu() {
		String detailedMenu = separator + this.machineTitle + "\n" + separator;
		int i = 0;
		if (dispensers != null) {
			for (Dispenser d : dispensers) {
				detailedMenu += (++i) + "." + d.getDetailedInfo() + " [ x"
						+ d.getQuantity() + " in stock] \n";
			}
		}
		return detailedMenu;

	}

	public void addMoney(BigDecimal cash) {
		if (cash == null || cash.signum() == -1) {
			System.out.println("You can not steal from a machine!");
		} else {
			this.money = this.money.add(cash);
		}
	}

	public int getNumOfDispensers() {
		return dispensers.length;
	}

	public String getMachineTitle() {
		return machineTitle;
	}

	private void printReceipt(String item) {
		System.out.println(separator + machineTitle + "\n" + separator
				+ "Item bought: " + item + "\nCHANGE:" + money + "$");

	}

	public void purchase(int userChoice) {
		userChoice--;
		if (dispensers != null && dispensers.length > userChoice
				&& dispensers[userChoice] != null) {
			try {
				dispensers[userChoice].purchase(money);
				this.money = this.money.subtract(dispensers[userChoice]
						.getPrice());
				System.out.println("Successful! Your receipt is:");
				printReceipt(dispensers[userChoice].toString());
				this.money = new BigDecimal(0);
			} catch (NotEnoughMoneyException e) {
				BigDecimal price = dispensers[userChoice].getPrice();
				if (money.equals(BigDecimal.valueOf(0))) {
					System.out
							.println("No money. You need to put in more dollars.");
				} else if (money.compareTo(price) < 0) {
					System.out.println("You need to add "
							+ price.subtract(money) + "$ more to purchase it.");
				}
			} catch (OutOfStockException e) {
				System.out.println("Item's out of stock!");
			}
		}
	}

	public BigDecimal getMoney() {
		return money;
	}

}
