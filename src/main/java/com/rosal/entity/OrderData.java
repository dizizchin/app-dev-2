package com.rosal.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class OrderData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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