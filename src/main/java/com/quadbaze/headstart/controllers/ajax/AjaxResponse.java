package com.quadbaze.headstart.controllers.ajax;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by olatunji Longe on 8/10/2016.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResponse implements Serializable{

    @JsonProperty("result")
    private AjaxResult result;

    private AjaxResponse(AjaxResult result){
        this.setResult(result);
    }

    public AjaxResult getResult() {
        return result;
    }

    public void setResult(AjaxResult result) {
        this.result = result;
    }

    private static AjaxResponse build(ResultType type, Object content){
        return new AjaxResponse(new AjaxResult(AjaxStatus.success, type, content));
    }

    private static AjaxResponse build(ResultType type, Object content, Target target){
        return new AjaxResponse(new AjaxResult(AjaxStatus.success, type, content, target));
    }

    public static AjaxResponse swalInfo(String title, String message){
        return new AjaxResponse(new Swal(AjaxStatus.info, title, message));
    }

    public static AjaxResponse swalSuccess(String title, String message){
        return new AjaxResponse(new Swal(AjaxStatus.success, title, message));
    }

    public static AjaxResponse swalWarning(String title, String message){
        return new AjaxResponse(new Swal(AjaxStatus.warning, title, message));
    }

    public static AjaxResponse swalError(String title, String message){
        return new AjaxResponse(new Swal(AjaxStatus.error, title, message));
    }

    public static AjaxResponse swalErrorDefault(){
        return new AjaxResponse(new Swal(AjaxStatus.error, "Error!", "An internal Server Error Occured"));
    }

    public static AjaxResponse swalInput(String title, String message, String field, String postURL){
        return new AjaxResponse(new Swal(title, message, new AjaxPost(field, postURL)));
    }

    public static AjaxResponse swalRedirect(AjaxStatus status, String title, String message, String redirectURL){
        return new AjaxResponse(new Swal(status, title, message, new AjaxRedirect(redirectURL, RedirectType.simple)));
    }

    public static AjaxResponse swalRedirectAjax(AjaxStatus status, String title, String message, String redirectURL){
        return new AjaxResponse(new Swal(status, title, message, new AjaxRedirect(redirectURL, RedirectType.ajax)));
    }

    public static AjaxResponse swalRedirectToTab(AjaxStatus status, String title, String message, String redirectURL){
        return new AjaxResponse(new Swal(status, title, message, new AjaxRedirect(redirectURL, RedirectType.tab)));
    }

    public static AjaxResponse redirect(String url){
        return new AjaxResponse(new AjaxRedirect(url, RedirectType.simple));
    }

    public static AjaxResponse redirectByAjaxGet(String url){
        return new AjaxResponse(new AjaxRedirect(url, RedirectType.ajax));
    }

    public static AjaxResponse redirectToTab(String url){
        return new AjaxResponse(new AjaxRedirect(url, RedirectType.tab));
    }

    public static AjaxResponse modal(String content){
        return AjaxResponse.build(ResultType.modal, content);
    }

    public static AjaxResponse modalAlert(String content){
        return AjaxResponse.build(ResultType.modal_alert, content);
    }

    public static AjaxResponse page(String content){
        return AjaxResponse.build(ResultType.page, content);
    }

    public static AjaxResponse pageAlert(String content){
        return AjaxResponse.build(ResultType.page_alert, content);
    }

    public static AjaxResponse pageTarget(String content, String domTarget, TargetAction targetAction){
        Target target = new Target(domTarget, targetAction);
        return AjaxResponse.build(ResultType.page_target, content, target);
    }

    public static AjaxResponse pageTarget(String content, Target target){
        return AjaxResponse.build(ResultType.page_target, content, target);
    }

}
