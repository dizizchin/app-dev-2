package com.rosal.model;

import lombok.Data;

import java.util.Date;

@Data
public class Order {
    int id;
    int orderId;
    int userId;
    String userName;

    //Product Data
    int productId;
    String productName;
    String imageFile;
    double quantity;
    double price;
    String status;
    String Address;

    //TRANSACTION DATA
    String transactionId;
    Date transactionDate;
    double totalAmount;
}
