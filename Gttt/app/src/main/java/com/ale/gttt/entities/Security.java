package com.ale.gttt.entities;

import com.google.gson.annotations.SerializedName;

public class Security {
    @SerializedName("user")

    private String user ;
    @SerializedName("password")
    private String password ;
    private String token;
    public String getPassword() {
        return password;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public void setPassword(String pass) {
        this.password = pass;
    }
    public String getUser() {
        return user;
    }
    public  Security(String pass, String name){
        this.password=pass;
        this.user=name;
    }
    public String GetToke(){
        return token;
    }

}
