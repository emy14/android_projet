package com.example.lp.myapplication.models;

/**
 * Created by lp on 25/01/2018.
 */

public class Note {

    private Integer notes;
    private Integer coeff;
    private Integer quotient;
    private String matiere;

    public Note(Integer notes, Integer coeff, Integer quotient, String matiere) {
        this.notes = notes;
        this.coeff = coeff;
        this.quotient = quotient;
        this.matiere = matiere;
    }

    public Integer getNotes() {
        return notes;
    }

    public Integer getCoeff() {
        return coeff;
    }

    public Integer getQuotient() {
        return quotient;
    }

    public String getMatiere() {
        return matiere;
    }
}
