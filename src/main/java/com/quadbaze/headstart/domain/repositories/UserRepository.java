package com.quadbaze.headstart.domain.repositories;

import com.quadbaze.headstart.domain.entities.User;
import com.quadbaze.headstart.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Olatunji O. Longe: Created on (22/07/2018)
 */

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserById(Long id);
    Boolean existsByUsername(String username);
    List<User> findAllByActiveIsTrue();
    User findUserByActiveIsTrueAndId(Long id);
    User findUserByActiveIsTrueAndUsername(String username);

    @Query("select u from User u, IN(u.roles) as r where u.active=true and u.username = :username and r.id = :roleId")
    User findUserByActiveIsTrueAndUsernameAndRoleId(@Param("username") String username, @Param("roleId") Long roleId);

    @Query("select u from User u, IN(u.roles) r where u.active=true and u.username = :username and r.roleType = :roleType")
    User findUserByActiveIsTrueAndUsernameAndRoleRoleType(@Param("username") String username, @Param("roleType") RoleType roleType);

    @Query("select u.createdBy from User u where u.id = :userId")
    User findCreatorOfUser(@Param("userId") Long userId);

    @Query("select case when (count(u) > 0) then true else false end from User u where u.id = :userId and u.createdBy = :username")
    Boolean isCreatorOfUser(@Param("username") Long username, @Param("userId") Long userId);

}
