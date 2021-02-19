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
    @SerializedName("celphone")
    @Expose
    private Integer celphone;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("subjet")
    @Expose
    private Integer subjet;

    public Integer getSubjet() {
        return subjet;
    }

    public void setSubjet(Integer subjet) {
        this.subjet = subjet;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }


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

    public Integer getCelphone() {
        return celphone;
    }

    public void setCelphone(Integer celphone) {
        this.celphone = celphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}
