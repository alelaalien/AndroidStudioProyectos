
        package com.ale.gttt.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

        public class Event {

            @SerializedName("id")
            @Expose
            private int id;

            @SerializedName("idSubjet")
            @Expose
            private Integer idSubjet;
            @SerializedName("date")
            @Expose
            private String date;
            @SerializedName("typeOf")
            @Expose
            private Integer typeOf;
            @SerializedName("idTeacher")
            @Expose
            private Integer idTeacher;
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



            public Integer getIdSubjet() {
                return idSubjet;
            }

            public void setIdSubjet(Integer idSubjet) {
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

            public Integer getTypeOf() {
                return typeOf;
            }

            public void setTypeOf(Integer typeOf) {
                this.typeOf = typeOf;
            }

            public Integer getIdTeacher() {
                return idTeacher;
            }

            public void setIdTeacher(Integer idTeacher) {
                this.idTeacher = idTeacher;
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