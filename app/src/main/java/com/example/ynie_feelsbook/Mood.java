package com.example.ynie_feelsbook;

import java.util.Date;

public abstract class Mood {
    private Date date;
    private String message;
    private String name;

    public Mood(){

    }

    public Mood(Date date, String name){
        this.date = date;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) throws tooLongMessageException{
        if (message.length()>100){
            throw new tooLongMessageException();
        }
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
