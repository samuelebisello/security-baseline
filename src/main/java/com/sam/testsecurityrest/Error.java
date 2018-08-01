package com.sam.testsecurityrest;

import com.google.gson.Gson;

public class Error {

    private String timestamp;
    private String method;
    private int status;
    private String message;
    private String path;

    public Error(String timestamp, String method, int status,String message,  String path) {
        this.timestamp = timestamp;
        this.message = message;
        this.method = method;
        this.status = status;
        this.path = path;
    }

    public Error() {
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
