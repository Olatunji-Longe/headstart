package com.quadbaze.headstart.controllers.ajax;

/**
 * Created by olatunji Longe on 7/26/17.
 */
public class AjaxPost {
    private String field;
    private String url;

    public AjaxPost(String field, String url) {
        this.field = field;
        this.url = url;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
