package com.quadbaze.headstart.controllers.utils;
/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
public class Alert {

    public static final String ATTRIBUTE = "alert";
    private final String flavor;
    private String message;

    private Alert(String flavor){
        this.flavor = flavor;
    }

    public static Alert primary(String message){
        return new Alert("alert-primary").setMessage(message);
    }

    public static Alert secondary(String message){
        return new Alert("alert-secondary").setMessage(message);
    }

    public static Alert light(String message){
        return new Alert("alert-light").setMessage(message);
    }

    public static Alert dark(String message){
        return new Alert("alert-dark").setMessage(message);
    }

    public static Alert info(String message){
        return new Alert("alert-info").setMessage(message);
    }

    public static Alert warning(String message){
        return new Alert("alert-warning").setMessage(message);
    }

    public static Alert success(String message){
        return new Alert("alert-success").setMessage(message);
    }

    public static Alert error(String message){
        return new Alert("alert-danger").setMessage(message);
    }

    private Alert setMessage(String message) {
        this.message = message;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public String getFlavor() {
        return flavor;
    }

}