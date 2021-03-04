
        package com.ale.gttt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;


        public class Event {

            @SerializedName("id")
            @Expose
            private int id;
            @SerializedName("idSubjet")
            @Expose
            private int idSubjet;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("typeId")
            @Expose
            private Integer typeId;
            @SerializedName("idUser")
            @Expose
            private Integer idUser;
            @SerializedName("title")
            @Expose
            private String title;
            @SerializedName("notes")
            @Expose
            private String notes;
            @SerializedName("priority")
            @Expose
            private Integer priority;
            @SerializedName("active")
            @Expose
            private Integer active;

            public Integer getTypeId() {
                return typeId;
            }

            public void setTypeId(Integer typeId) {
                this.typeId = typeId;
            }

            public int getIdSubjet() {
                return idSubjet;
            }

            public void setIdSubjet(int idSubjet) {
                this.idSubjet = idSubjet;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public Integer getIdUser() {
                return idUser;
            }

            public void setIdUser(Integer idUser) {
                this.idUser = idUser;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getNotes() {
                return notes;
            }

            public void setNotes(String notes) {
                this.notes = notes;
            }

            public Integer getPriority() {
                return priority;
            }

            public void setPriority(Integer priority) {
                this.priority = priority;
            }

            public Integer getActive() {
                return active;
            }

            public void setActive(Integer active) {
                this.active = active;
            }

        }