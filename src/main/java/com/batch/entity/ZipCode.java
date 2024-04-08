package com.batch.entity;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ZipCode {



    private Long id;

    private String zipCode ;
    private String cityName;
    private String stateName;

    public ZipCode(String zipCode, String cityName, String stateName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
        this.stateName = stateName;
    }
//
//    public ZipCode(Long id, String zipCode, String cityName, String stateName) {
//        this.id = id;
//        this.zipCode = zipCode;
//        this.cityName = cityName;
//        this.stateName = stateName;
//    }
}
