package com.ale.gttt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Teacher {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("surname")
    @Expose
    private String surname;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("subjets")
    @Expose
    private String subjets;
    @SerializedName("celephone")
    @Expose
    private Integer celephone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("notes")
    @Expose
    private String notes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getSubjets() {
        return subjets;
    }

    public void setSubjets(String subjets) {
        this.subjets = subjets;
    }

    public Integer getCelephone() {
        return celephone;
    }

    public void setCelephone(Integer celephone) {
        this.celephone = celephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
