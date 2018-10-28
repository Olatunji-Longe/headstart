package com.quadbaze.headstart.controllers.ajax;

/**
 * Created by olatunji Longe on 8/10/17.
 */
public class Target {

    private String element;
    private String action;

    public Target (String domElement, TargetAction action){
        this.element = domElement;
        this.action = action.name();
    }

    public String getElement() {
        return element;
    }

    public String getAction() {
        return action;
    }
}
