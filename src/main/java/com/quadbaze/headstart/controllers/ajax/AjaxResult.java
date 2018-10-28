package com.quadbaze.headstart.controllers.ajax;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

/**
 * Created by olatunji Longe on 8/10/2016.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AjaxResult implements Serializable {

    @JsonProperty("type")
    private ResultType type;

    @JsonProperty("content")
    private Object content;

    @JsonProperty("status")
    private AjaxStatus status;

    @JsonProperty("target")
    private Target target;

    public AjaxResult(AjaxStatus status, ResultType type, Object content){
        this.setStatus(status);
        this.setType(type);
        this.setContent(content);
    }

    public AjaxResult(AjaxStatus status, ResultType type, Object content, Target target){
        this.setStatus(status);
        this.setType(type);
        this.setContent(content);
        this.setTarget(target);
    }

    public ResultType getType() {
        return type;
    }

    public void setType(ResultType type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = (content.getClass().isAssignableFrom(String.class)) ? StringUtils.trim(String.valueOf(content)) : content;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public AjaxStatus getStatus() {
        return status;
    }

    public void setStatus(AjaxStatus status) {
        this.status = status;
    }
}
