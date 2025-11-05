package com.rosal.model;
import lombok.Data;

@Data
public class OrderItem {

    //User Data
    int id;
   // int orderId;
   int userId;
    String userName;
    int orderId;

    //Product Data
    int productId;
    String productName;
    String imageFile;
    double quantity;
    double price;
    String status;

}

