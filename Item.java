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
   
   public String toString() {
	   return this.real_discount + " ";
   }
}