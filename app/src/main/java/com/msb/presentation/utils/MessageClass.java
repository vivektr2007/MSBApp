package com.msb.presentation.utils;

public class MessageClass {
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeStamp() {
        return TimeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        TimeStamp = timeStamp;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String message;
    private String TimeStamp;
    private String userName;


    public MessageClass(String message, String timeStamp, String userName) {
        this.message = message;
        TimeStamp = timeStamp;
        this.userName = userName;
    }

}
