package com.example.lp.myapplication.models;

/**
 * Created by lp on 25/01/2018.
 */

public class Matiere {
    private Integer id;
    private String name;

    public Matiere(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
