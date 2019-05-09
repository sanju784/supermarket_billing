import java.util.*;

public class Billing {
	
	static Map<String, Item> itemMap;
	
	public Billing() {
		itemMap = new HashMap<>();
	}
	public static void main(String args[]) {
		init();
		int originalPrice = 0;
		int discountPrice = 0;
		Item tempItem;
		String[] orders = {"Apple 6Kg", "Orange 2Kg", "Potato 14Kg", "Tomato 3Kg", "Cow Milk 8Lt", "Gouda 2Kg"};
		for (String order : orders) {
			String [] o = order.split(" ");
			tempItem = itemMap.get(o[0]);
			System.out.println(o);
			System.out.println(tempItem.item_price);
		}
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
	}
}