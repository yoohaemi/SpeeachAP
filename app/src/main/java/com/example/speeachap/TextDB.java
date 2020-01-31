package com.example.speeachap;

public class TextDB {

    private String message;

    public TextDB() {}
    public TextDB( String message) {
        this.message = message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
