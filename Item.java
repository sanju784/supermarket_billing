/**
 * This program implements a class whose objects can be used to save 
 * the item details that are present in the supermarket.
 * <p>
 * The name of the items are saved as key in the Map,
 * and objects of this class are saved as its value.
 * @author sanjeevchaurasia
 *
 */
class Item {
   int item_price; //price of item
   int category_discount; //Category level discount
   int sub_category_discount; //Sub category level discount
   int item_discount; //real discount on item, 0 when giving free items on purchase
   boolean is_percent_discount; //to check if discount is of percentage type or free item on purchase
   int buy_item; //Number of items to be buied to get free item 
   int free_item; //Number of free items the customer can get
   int real_discount; //The real discount which the customer will get, which is maximum of the three

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

   /**
    *  This method calculates maximum discount from category branch
    */
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