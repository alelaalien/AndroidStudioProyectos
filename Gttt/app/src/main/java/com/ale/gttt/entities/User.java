package com.ale.gttt.entities;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class User  {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("nick")
    @Expose
    private String nick;
    @SerializedName("email")
    @Expose
    private String email;

    public int getIdSecurity() {
        return idSecurity;
    }

    public void setIdSecurity(int idSecurity) {
        this.idSecurity = idSecurity;
    }

    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("idSecurity")
    @Expose
    private int idSecurity;
    public List<User> list;


    public User() {
    }

    public User(int id, String nick, String email,  String password) {
        this.id=id;
        this.nick = nick;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}