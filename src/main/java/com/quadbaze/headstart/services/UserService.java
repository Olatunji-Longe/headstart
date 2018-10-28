package com.quadbaze.headstart.services;

import com.quadbaze.headstart.domain.entities.User;
import com.quadbaze.headstart.domain.enums.RoleType;

/**
 * @author: Olatunji O. Longe
 * @created: 13 Oct, 2018, 1:50 PM
 */
public interface UserService {
    User getUser(String email, RoleType roleType);
}
