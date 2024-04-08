package com.batch.entity;


import lombok.Data;

import javax.persistence.*;

@Table(name = "users")
@Data
public class DataUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String city;
    private Integer code;


    public DataUser(String name, String city, Integer code) {
        this.name = name;
        this.city = city;
        this.code = code;
    }
}
