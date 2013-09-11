import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;



public class MainClass {
	static VendingMachine machines[];

	public static void printMenu() {
		int i = 0;
		System.out.println("List of machines available:");
		for (VendingMachine machine : machines) {
			System.out.println(machine.getMachineTitle() + " #" + (++i)
					+ ": \n" + machine.getMenu());
		}
	}

	/**
	 * Saves items sold info into file called like 'current date-time.txt'
	 */
	public static void saveIncomeAndStuff(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyy-mm-dd_hh_mm_ss");
		FileWriter fw = null;
		try {
			fw = new FileWriter(new File(sdf.format(date))+".txt");
			for (VendingMachine m: machines){
				fw.write(m.getTotal()+"\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	/**
	 * Saves inventory to file 'data.dat' in classpath root
	 */
	 private static void saveAllToFile() {
		ObjectOutputStream out = null;
		try {
			out = new ObjectOutputStream(new FileOutputStream("data.dat"));
			out.writeObject(machines);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}



	}

	private static void loadAllFromFile() {
		ObjectInputStream in = null;
		try {
			in = new ObjectInputStream(new FileInputStream("data.dat"));
			machines = (VendingMachine[]) in.readObject();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void init() {
		

				 Dispenser[] testDispensers = {
				 new Dispenser(new FoodInformation("Coca-Cola",
				 new BigDecimal(2), 140), 1),
				 new Dispenser(new FoodInformation("Cherry Coke",
				 new BigDecimal(2.5), 150), 20),
				 new Dispenser(new FoodInformation("Barqs Root Beer",
				 new BigDecimal(3.75), 150), 20),
				 new Dispenser(new FoodInformation("Barqs Root Beer (big)",
				 new BigDecimal(5), 220), 20),
				 new Dispenser(new FoodInformation("Capri Sun Orange (200ml)",
				 new BigDecimal(1), 89), 20),
				 new Dispenser(new FoodInformation("Fanta Orange Zero",
				 new BigDecimal(1), 3), 20),
				 new Dispenser(new FoodInformation("Innocent Kiwis",
				 new BigDecimal(0.99), 122), 20),
				 new Dispenser(new FoodInformation("Irn Bru", new BigDecimal(
				 0.99), 142), 20),
				 new Dispenser(new FoodInformation("Irn Bru Diet",
				 new BigDecimal(1.2), 2), 20),
				 new Dispenser(new FoodInformation("J2O Orange ",
				 new BigDecimal(1.2), 89), 20),
				 new Dispenser(new FoodInformation("Lemonade Cloudy",
				 new BigDecimal(1.33), 8), 20) };
				 Dispenser[] testDispensers2 = {
				 new Dispenser(new FoodInformation("Mars", new BigDecimal(0.99),
				 512), 20),
				 new Dispenser(new FoodInformation("Cadbury's Dairy Milk",
				 new BigDecimal(0.99), 520), 20),
				 new Dispenser(new FoodInformation("Bassett's Jelly Babies",
				 new BigDecimal(1.20), 335), 20),
				 new Dispenser(new FoodInformation("Milky Way", new BigDecimal(
				 1.20), 445), 20),
				 new Dispenser(new FoodInformation("Apple Slices",
				 new BigDecimal(0.55), 82), 20),
				 new Dispenser(new FoodInformation("Oil-Popped Popcorn",
				 new BigDecimal(1), 110), 20),
				 new Dispenser(new FoodInformation("  Raisins", new BigDecimal(
				 1.1), 123), 20),
				 new Dispenser(new FoodInformation("BBQ Chips", new BigDecimal(
				 0.75), 445), 20) };
				
				 machines = new VendingMachine[2];
				 machines[0] = new VendingMachine(testDispensers, "Drinks Machine",
				 -1);
				 machines[1] = new VendingMachine(testDispensers2,
				 "Chocolates and snacks", 9);
				 saveAllToFile();
				
		printMenu();

	}

	public static void addMoney(String in, Scanner input, VendingMachine machine) {
		in = in.replaceAll("\\+", "");
		while (!in.matches("([1-9]\\d*|0)(\\.\\d)*$")
				|| new BigDecimal(in).compareTo(BigDecimal.valueOf(0)) <= 0) {
			System.out
					.println("Please enter the amount of money you want to insert. (Right format is ##.##)");
			in = input.nextLine();
		}
		machine.addMoney(new BigDecimal(in));
		System.out.println(machine.getMachineTitle() + ": "
				+ machine.getMoney() + "$");

	}

	public static void menu() {

		Scanner input = new Scanner(System.in);
		String in = "";
		Random random = new Random();
		int machine = random.nextInt(machines.length);
		// the machine is chosen

		VendingMachine currentMachine = machines[machine];
		System.out.println("Machine chosen: "
				+ currentMachine.getMachineTitle());

		System.out.println(currentMachine.getDetailedMenu());
		if (!currentMachine.isWorking()) {
			System.out
					.println("You can not buy anything from the turned off machine =(");
			return;
		}

		while (!in.toLowerCase().equals("x")) {
			System.out
					.println("Enter '+3' (no quotes) to insert $3.00, then, enter the number of item to purchase or 'x' (no quotes) to exit");
			in = input.nextLine();
			if (in.matches("\\d+")) {
				if (Integer.parseInt(in) < 1
						|| Integer.parseInt(in) > currentMachine
								.getNumOfDispensers()) {
					System.out.println("No such item!");
				} else {
					currentMachine.purchase(Integer.parseInt(in));
					continue;
				}
			} else if (in.contains("+")) {
				addMoney(in, input, currentMachine);
				continue;
			} else {
				if (in.toLowerCase().equals("x")) {
					return;
				} else {
					System.out.println("Wrong input!");
					continue;
				}
			}

		}

	}

	public static void main(String[] args) {
		init();
		menu();
		saveAllToFile();
		saveIncomeAndStuff();
		System.out.println("All done! Bye!");
	}
}
