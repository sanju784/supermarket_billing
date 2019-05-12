/**
 * This program implements an object to save the item details
 * purchased by the customer.
 * <p>
 * In main class creating a list of these objects.
 * The generated list will be used to prepare invoice.
 * @author sanjeevchaurasia
 * 
 */
class BuiedItems {
	String item; //name of buied item
	int quantity; //quantity of buied item
	String unit; //unit of buied item (kg, lt, dozen etc.)
	double real_amount; //real cost of item without discount
	double billed_amount; //cost of item after discount

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public double getReal_amount() {
		return real_amount;
	}

	public void setReal_amount(double real_amount) {
		this.real_amount = real_amount;
	}

	public double getBilled_amount() {
		return billed_amount;
	}

	public void setBilled_amount(double billed_amount) {
		this.billed_amount = billed_amount;
	}
}