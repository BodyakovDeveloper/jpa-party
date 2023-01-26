package com.example.sql.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {

    @Column(name = "country", length = 32)
    private String country;

    @Column(name = "city", length = 32)
    private String city;

    @Column(length = 32, name = "street_and_house")
    private String streetAndHouse;
}