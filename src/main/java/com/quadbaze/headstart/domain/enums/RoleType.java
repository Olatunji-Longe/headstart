package com.quadbaze.headstart.domain.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
public enum RoleType {

    //Bootstrapped User Roles
    ROLE_BOOTSTRAPPED_USER("Bootstrapped-User"),

    //Simple User Roles
    ROLE_USER_ADMIN("Admin-User"),
    ROLE_USER_CUSTOMER("Customer-User");


    private String displayName;

    RoleType(String displayName){
        this.displayName = displayName;
    }

    public String displayName() {
        return this.displayName;
    }

    public static List<String> allRoleNames(){
        List<String> list = new ArrayList<>();
        RoleType[] roles = RoleType.values();
        for(RoleType role : roles){
            list.add(role.name());
        }
        return list;
    }

    public static List<String> roleNames(List<RoleType> roles){
        List<String> list = new ArrayList<>();
        for(RoleType role : roles){
            list.add(role.name());
        }
        return list;
    }
}
