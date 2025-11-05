package com.rosal.model;

import lombok.Data;

@Data
public class Product {
    int id;
    String productName;
    String imageFile;
    double quantity;
    double price;
    String status;
    String categoryName;

}
