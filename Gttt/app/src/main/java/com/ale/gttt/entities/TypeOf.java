package com.ale.gttt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TypeOf {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("idUser")
    @Expose
    private int idUser;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @SerializedName("description")
    @Expose
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
