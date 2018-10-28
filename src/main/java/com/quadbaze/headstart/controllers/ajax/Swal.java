package com.quadbaze.headstart.controllers.ajax;

/**
 * Created by olatunji Longe on 8/10/2016.
 */
public class Swal extends AjaxResult {

    private String title;
    private AjaxRedirect redirect;
    private AjaxPost post;

    public Swal(AjaxStatus status, String title, Object content){
        super(status, ResultType.swal, content);
        this.setTitle(title);
    }

    /**
     * Use when a redirect should be done after receiving feedback
     * @param status
     * @param title
     * @param content
     * @param redirect
     */
    public Swal(AjaxStatus status, String title, Object content, AjaxRedirect redirect){
        super(status, ResultType.swal, content);
        this.setTitle(title);
        this.setRedirect(redirect);
    }

    /**
     * Use when input should be received on feedback
     * @param title
     * @param content
     * @param post
     */
    public Swal(String title, Object content, AjaxPost post){
        super(AjaxStatus.input, ResultType.swal, content);
        this.setTitle(title);
        this.setPost(post);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public AjaxRedirect getRedirect() {
        return redirect;
    }

    public void setRedirect(AjaxRedirect redirect) {
        this.redirect = redirect;
    }

    public AjaxPost getPost() {
        return post;
    }

    public void setPost(AjaxPost post) {
        this.post = post;
    }
}
