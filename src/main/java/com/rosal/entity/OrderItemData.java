package com.rosal.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "orderItem_data")
public class OrderItemData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //User Data
    int id;
    //int orderId;
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
