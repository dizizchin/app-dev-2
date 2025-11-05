package com.rosal.model;

import lombok.Data;

import java.util.Date;
@Data
public class User {
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
