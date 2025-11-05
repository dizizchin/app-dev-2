package com.rosal.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "product_data")
public class    ProductData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String productName;
    String imageFile;
    double quantity;
    double price;
    String status;
    String categoryName;

}
