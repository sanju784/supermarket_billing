import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

/**
 * This program implements an application that generates 
 * invoice for customers in a supermarket.
 * Here using Collection type List and Map for temporary storage.
 * To execute this application input data with text file can be passed,
 * otherwise it will execute with default input data as mentioned in problem_statement.
 * <p>
 * <b>Note:</b>The input file must be written in below format
 * Customer Anish Kumar buys following items
 * Apple 6Kg, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg
 * <p>
 * First line containing the customer detail, and second line containing the customer shopping details.
 * @author sanjeevchaurasia
 */
public class Billing {

	// Map to save supermarket product data
	private Map<String, Item> itemMap;

	// List to save bought items name, quantity and cost
	private static List<BuiedItems> list;

	private static DecimalFormat df2;

	public Billing() {
		itemMap = new HashMap<>();
		list = new ArrayList<>();
		df2 = new DecimalFormat("#.00");
		init();
	}

	public static void main(String args[]) {
		Billing t = new Billing();
		//t.init();
		String input_line1 = "", input_line2 = "";

		/**
		 * When input data via text file is given
		 *	- Assuming that the order is written in 2 line format as mentioned in the problem statement demo input 
		 *	- Where first line contains Customer name, second line has order details
		 */
		if (args.length > 0) {
			try {
				String path = System.getProperty("user.dir");
				String file_name = args[0];
				BufferedReader reader = new BufferedReader(new FileReader(path + "/" + file_name));
				input_line1 = reader.readLine();
				input_line2 = reader.readLine();
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		/**
		 *	When no text file is passed as argument
		 *	- Considering the same input as mentioned in problem statement
		 */
		} else {
			input_line1 = "Customer Anish Kumar buys following items";
			input_line2 = "Apple 6Kg, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg";
		}

		// get customer name
		String customer_name = t.getCustomerName(input_line1);

		// generating customer invoice
		t.generateCustomerInvoice(input_line2);

		// Total amount before discount
		double total_real_amount = t.getTotalRealAmount();

		// Total amount to be paid by customer after discount
		double total_billed_amount = t.getTotalBilledAmount();

		// Total discounted amount
		double saved_amount = t.getSavedAmount(total_real_amount, total_billed_amount);

		// Printing invoice
		System.out.println("Customer: " + customer_name);
		System.out.println("Item \t\t Qty \t Amount");
		for(BuiedItems item : list) {
			System.out.println(item.item + "\t\t" + item.quantity + item.unit + "\t" + df2.format(item.billed_amount));
		}
		System.out.println("\nTotal Amount         " + df2.format(total_billed_amount) + " Rs");
		System.out.println("\nYou saved      " + df2.format(total_real_amount) + " - " + df2.format(total_billed_amount) + " = " +df2.format(saved_amount) + " Rs");
	}

	/**
	 * This method saves all supermarket product details in a hashmap
	 */
	private void init() {
		itemMap.put("Apple", new Item(50, 10, 18, 0, false, 3, 1));
		itemMap.put("Orange", new Item(80, 10, 18, 20, true, 0, 0));
		itemMap.put("Potato", new Item(30, 10, 5, 0, false, 5, 2));
		itemMap.put("Tomato", new Item(70, 10, 5, 10, true, 0, 0));
		itemMap.put("Cow Milk", new Item(50, 15, 20, 0, false, 3, 1));
		itemMap.put("Soy Milk", new Item(40, 15, 20, 10, true, 0, 0));
		itemMap.put("Cheddar", new Item(50, 15, 20, 0, false, 2, 1));
		itemMap.put("Gouda", new Item(80, 15, 20, 10, true, 0, 0));
	}
	
	/**
	 * This method reads the order string, then save
	 * all required details in a list to prepare invoice
	 * @param String order_list The second line of input.
	 */
	void generateCustomerInvoice(String order_list) {
		
		// To get customer order
		String[] orders = order_list.split(",");
		
		// to get an item detail from item hashmap
		Item currItem;
		// to store details of bought items
		BuiedItems bi;
		// Loop through customer order and generating invoice data
		for (String order : orders) {
			// flag to check if unit is mentioned as Kg, Lt
			boolean flg_unit_type_big = true;
			// flag to check if unit is mentioned as dozen
			boolean flg_unit_type_dozen = false;
			
			order = order.trim();
			
			bi = new BuiedItems();
			
			// separating item name, quantity and unit
			int pos = order.lastIndexOf(" ");
			bi.item = order.substring(0, pos);
			String quantity = order.substring(pos +1);

			String part[] = quantity.split("(?<=\\d)(?=\\D)");
			bi.quantity = Integer.parseInt(part[0]);
			//String unit;
			if (part.length > 1) {
				bi.unit = part[1];
			} else {
				bi.unit = "";
			}
			
			// Checking if unit is null, it occur in case when input is given as numbers and no unit is mentioned
			if ((bi.unit == null || bi.unit.length() == 0) == false) {
				// 
				if (!(bi.unit.equalsIgnoreCase("kg") || bi.unit.equalsIgnoreCase("lt")) && !bi.unit.equalsIgnoreCase("dozen")) {
					flg_unit_type_big = false;
				}
				if (bi.unit.equalsIgnoreCase("dozen")) {
					bi.quantity *= 12;
					flg_unit_type_dozen = true;
				}
			}
			currItem = itemMap.get(bi.item);
			
			bi.real_amount = bi.quantity * currItem.item_price;
			
			if (currItem.is_percent_discount) {
				bi.billed_amount = bi.real_amount * (100 - currItem.real_discount) / 100;
			} else {
				int discount_item = bi.quantity / (currItem.buy_item + currItem.free_item);
				int billed_quantity = bi.quantity - discount_item * currItem.free_item;
				bi.billed_amount = billed_quantity * currItem.item_price;
			}
			
			if (flg_unit_type_big == false) {
				bi.real_amount *= Math.pow(10, -3);
				bi.billed_amount *= Math.pow(10, -3);
			}
			if (flg_unit_type_dozen) {
				bi.quantity /= 12;
			}
			list.add(bi);
		}
	}

	/**
	 * This method loops through the list containing purchased items
	 * and calculates the total amount before discount
	 * @return double This returns the total amount before discount.
	 */
	double getTotalRealAmount() {
		double total_real_amount = 0;
		for(BuiedItems item : list) {
			total_real_amount += item.real_amount;
		}
		return total_real_amount;
	}

	/**
	 * This method loops through the list containing purchased items
	 * and calculates the total amount that has to be paid
	 * @return double This returns the total amount to be paid by customer.
	 */
	double getTotalBilledAmount() {
		double total_billed_amount = 0;
		for(BuiedItems item : list) {
			total_billed_amount += item.billed_amount;
		}
		return total_billed_amount;
	}

	/**
	 * This method is used to calculate the amount saved by customer.
	 * @param double real_amount Amount to be paid before discount.
	 * @param double billed_amount Amount to be paid after discount.
	 * @return double This returns the difference between real amount and the amount to be paid.
	 */
	double getSavedAmount(double real_amount, double billed_amount) {
		return real_amount - billed_amount;
	}
	
	/**
	 * This method is used to get customer name to print in invoice.
	 * @param String input_line1 First input line that contains customer detail.
	 * @return String customer_name Customer name to be printed on invoice.
	 */
	String getCustomerName(String input_line1) {
		String customer_detail[] = input_line1.split(" ");
		String customer_name = "";
		for (int i=1;i < customer_detail.length; i++) {
			if (customer_detail[i].equalsIgnoreCase("buys"))
				break;
			customer_name += customer_detail[i] + " ";
		}
		return customer_name;
	}
}


