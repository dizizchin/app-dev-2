package com.rosal.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "Customer_Data")
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //id
    int id;
    int userId;

    //credentials
    String userName;
    String userPassword;
    String userEmail;

    //personal details
    String firstName;
    String middleName;
    String lastName;
    Date dateOfBirth;
    String userGender;
    String phoneNumber;
    String userAddress;

}

