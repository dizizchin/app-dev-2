export class OrderItem {

    //Table ID
    id: number = 0;

    //User Data
    orderId: number = 0;
    userId: number = 0;
    userName?: string = "";

    //Product Data
    productId: number = 0;
    productName: string = "";
    imageFile: string = "";
    quantity: number = 0;
    price: string = "0.0";
  
    //SKU??

    //status
    status: string ="not_selected";

    //For UI
    showConfirm?: boolean; //For UI
}
