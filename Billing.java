//Assuming that all rates and discounts are mentioned in the form of Kg, Lt and Numbers
// Assuming that standard of maintaining of gms and ml

import java.util.*;
import java.io.*;

public class Billing {
	
	static Map<String, Item> itemMap = new HashMap<>();
	
	public Billing() {
		//itemMap = new HashMap<>();
	}
	public static void main(String args[]) {
		String input_line1 = "", input_line2 = "";
		
		/**
			When input data via text file is given
			- Assuming that that the order is written in 2 line format as mentioned in the problem statement demo input 
			- Where first line contains Customer name, second line has order details
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
			When no input data is provided via text file
			- Considering the same input as mentioned in problem statement
		*/
		} else {
			input_line1 = "Customer Anish Kumar buys following items";
			input_line2 = "Apple 6Kg, Orange 2Kg, Potato 14Kg, Tomato 3Kg, Cow Milk 8Lt, Gouda 2Kg";
		}
		System.out.println(input_line1);
		System.out.println(input_line2);
		
		init();
		Item tempItem;

		// To get customer name
		String customer_name = "";
		String customer_detail[] = input_line1.split(" ");
		
		for (int i=1;i < customer_detail.length; i++) {
			if (customer_detail[i].equalsIgnoreCase("buys"))
				break;
			customer_name += customer_detail[i] + " ";
		}
		
		String[] orders = input_line2.split(",");
		
		List<BuiedItems> list = new ArrayList<>();
		
		BuiedItems bi;
		
		for (String order : orders) {
			
			boolean flg_data_type_big = true;
			
			order = order.trim();
			
			bi = new BuiedItems();
			
			int pos = order.lastIndexOf(" ");
			bi.item = order.substring(0, pos);
			String quantity = order.substring(pos +1);

			String part[] = quantity.split("(?<=\\d)(?=\\D)");
			bi.quantity = Integer.parseInt(part[0]);
			String unit;
			if (part.length > 1) {
				bi.unit = part[1];
			}

			// Checking if unit is null, it occur in case when input is given as numbers and no unit is mentioned
			if ((bi.unit == null || bi.unit.length() == 0) == false) {
				// 
				if ((bi.unit.equalsIgnoreCase("kg") || bi.unit.equalsIgnoreCase("lt")) == false) {
					flg_data_type_big = false;
				}
			}
			tempItem = itemMap.get(bi.item);
			
			bi.real_amount = bi.quantity * tempItem.item_price;
			
			if (tempItem.is_percent_discount) {
				bi.billed_amount = bi.real_amount * (100 - tempItem.real_discount) / 100;
			} else {
				int discount_item = bi.quantity / (tempItem.buy_item + tempItem.free_item);
				int billed_quantity = bi.quantity - discount_item * tempItem.free_item;
				bi.billed_amount = billed_quantity * tempItem.item_price;
			}
			
			if (flg_data_type_big == false) {
				bi.real_amount *= Math.pow(10, -3);
				bi.billed_amount *= Math.pow(10, -3);
			}
			list.add(bi);
		}
		
		double real_amount = 0;
		double discounted_amount = 0;
		System.out.println("Customer: " + customer_name);
		System.out.println("Item \t\t Qty \t Amount");
		for(BuiedItems item : list) {
			System.out.println(item.item + "\t\t" + item.quantity + item.unit + "\t" + item.billed_amount);
			real_amount += item.real_amount;
			discounted_amount += item.billed_amount;
		}
		System.out.println("\nTotal Amount         " + discounted_amount + "Rs");
		System.out.println("\nYou saved      " + real_amount + " - " + discounted_amount + " = " +(real_amount - discounted_amount) + "Rs");
	}
	
	// ToDo - Put product discount at the end, in constructor add all items with 0 value as default.
	// ToDo - Add unit to the prices.
	private static void init() {
		itemMap.put("Apple", new Item(50, 10, 18, 0, false, 3, 1));
		itemMap.put("Orange", new Item(80, 10, 18, 20, true, 0, 0));
		itemMap.put("Potato", new Item(30, 10, 5, 0, false, 5, 2));
		itemMap.put("Tomato", new Item(70, 10, 5, 10, true, 0, 0));
		itemMap.put("Cow Milk", new Item(50, 15, 20, 0, false, 3, 1));
		itemMap.put("Soy Milk", new Item(40, 15, 20, 10, true, 0, 0));
		itemMap.put("Cheddar", new Item(50, 15, 20, 0, false, 2, 1));
		itemMap.put("Gouda", new Item(80, 15, 20, 10, true, 0, 0));
		itemMap.put("Banana", new Item(5, 10, 18, 0, false, 24, 12));
	}
}


class BuiedItems {
	String item;
	int quantity;
	String unit;
	double real_amount;
	double billed_amount;
	
	public String toString() {
		return item +" "+quantity+" "+unit+" "+real_amount+" "+billed_amount;
	}
}


class Item {
   int item_price;
   int category_discount;
   int sub_category_discount;
   int item_discount;
   boolean is_percent_discount;
   int buy_item;
   int free_item;
   int real_discount;
   
   
   public Item(int item_price, int category_discount, int sub_category_discount, int item_discount, boolean is_percent_discount, int buy_item, int free_item) {
	   this.item_price = item_price;
	   this.category_discount = category_discount;
	   this.sub_category_discount = sub_category_discount;
	   this.item_discount = item_discount;
	   this.is_percent_discount = is_percent_discount;
	   this.buy_item = buy_item;
	   this.free_item = free_item;
	   if (this.is_percent_discount) {
		   setRealDiscount();
	   }
   }
   
   // To get maximum discount from category branch
   private void setRealDiscount() {
	   if (this.item_discount > this.sub_category_discount && this.item_discount > this.category_discount) {
		   this.real_discount = this.item_discount;
	   } else if (this.sub_category_discount > this.category_discount) {
		   this.real_discount = this.sub_category_discount;
	   } else {
		   this.real_discount = this.category_discount;
	   }
   }
}