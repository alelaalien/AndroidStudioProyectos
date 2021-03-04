package com.ale.gttt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dictates {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("subjetId")
    @Expose
    private int subjetId;
    @SerializedName("teacherId")
    @Expose
    private int teacherId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSubjetId() {
        return subjetId;
    }

    public void setSubjetId(int subjetId) {
        this.subjetId = subjetId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}
