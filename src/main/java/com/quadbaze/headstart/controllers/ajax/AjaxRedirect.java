package com.quadbaze.headstart.controllers.ajax;

/**
 * Created by olatunji Longe on 9/19/2016.
 */
public class AjaxRedirect extends AjaxResult {

    private RedirectType redirectType;
    private String url;

    public AjaxRedirect(String url, RedirectType redirectType) {
        super(AjaxStatus.none, ResultType.url, url);
        this.setRedirectType(redirectType);
        this.setUrl(url);
    }

    public RedirectType getRedirectType() {
        return redirectType;
    }

    public void setRedirectType(RedirectType redirectType) {
        this.redirectType = redirectType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
