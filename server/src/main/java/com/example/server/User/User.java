package com.example.server.User;

import javax.persistence.*;
import javax.validation.constraints.Email;


@Entity
@Table(name="USER")
public class User {
    //Primary key for user table
    @Id
    @Column(name = "USER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Generates our ID automatically
    private Long userId;

    @Column(name = "EMAIL")
    @Email
    private String email;

    @Column(name = "PASSWORD")
    private String password;



    public User(@Email String email, String password) {
        this.email = email;
        this.password = password;
    }
    //Do not delete this
    public User(){}

    //private ArrayList<Long> buildingList;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long id) {
        this.userId = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




}