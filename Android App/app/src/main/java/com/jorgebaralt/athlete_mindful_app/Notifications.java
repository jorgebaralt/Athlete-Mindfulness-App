package com.jorgebaralt.athlete_mindful_app;

/**
 * Created by User on 11/19/2017.
 */

public class Notifications {
    private int id;
    private String url;
    private String message;

    public Notifications(String url, String message){
        this.url = url;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getMessage() {
        return message;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
