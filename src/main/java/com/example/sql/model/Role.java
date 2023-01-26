package com.example.sql.model;

import lombok.Getter;

@Getter
public enum Role {
    OWNER("Owner"),
    ADMIN("Admin");

    private String value;

    public String getValue() {
        return value;
    }

    Role(String value) {
        this.value = value;
    }
}