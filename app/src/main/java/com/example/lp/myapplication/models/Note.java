package com.example.lp.myapplication.models;

/**
 * Created by lp on 25/01/2018.
 */

public class Note {

    private Integer notes;
    private Integer coeff;
    private Integer quotient;
    private String matiere;
    private Integer id;
    private Integer id_matiere;

    public Note(Integer id, Integer notes, Integer coeff, Integer quotient, String matiere, Integer id_matiere) {
        this.id = id;
        this.notes = notes;
        this.coeff = coeff;
        this.quotient = quotient;
        this.matiere = matiere;
        this.id_matiere = id_matiere;
    }

    public Integer getId_matiere() {
        return id_matiere;
    }

    public Integer getId() {
        return id;
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
