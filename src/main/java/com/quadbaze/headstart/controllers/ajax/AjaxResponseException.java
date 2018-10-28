package com.quadbaze.headstart.controllers.ajax;

/**
 * Created by olatunji Longe on 8/10/2016.
 */
public class AjaxResponseException extends RuntimeException {

    private String title = "Error!";

    public AjaxResponseException(String title, String message) {
        super(message);
        this.setTitle(title);
    }

    public AjaxResponseException(Throwable cause, String title, String message) {
        super(message, cause);
        this.setTitle(title);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if(title != null){
            this.title = title;
        }
    }
}
