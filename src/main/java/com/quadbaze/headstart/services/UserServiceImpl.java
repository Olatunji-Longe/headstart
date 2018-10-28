package com.quadbaze.headstart.services;

import com.quadbaze.headstart.domain.entities.User;
import com.quadbaze.headstart.domain.enums.RoleType;
import com.quadbaze.headstart.domain.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: Olatunji O. Longe
 * @created: 13 Oct, 2018, 1:52 PM
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Gets user by specified email and role
     * @param email
     * @param roleType
     * @return
     */
    @Override
    public User getUser(String email, RoleType roleType) {
        return userRepository.findUserByActiveIsTrueAndUsernameAndRoleRoleType(email, roleType);
    }
}
