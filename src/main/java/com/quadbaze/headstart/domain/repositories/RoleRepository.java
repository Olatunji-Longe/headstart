package com.quadbaze.headstart.domain.repositories;

import com.quadbaze.headstart.domain.entities.Role;
import com.quadbaze.headstart.domain.enums.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Olatunji O. Longe: Created on (14/08/2018)
 */

@Transactional(readOnly = true)
public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findRoleById(Long id);
    Role findRoleByRoleType(RoleType roleType);
    List<Role> findRolesByIdIn(List<Long> roleIds);
    List<Role> findRolesByRoleTypeIn(List<RoleType> roleTypes);

    @Query("select u.roles from User u where u.id = :userId")
    List<Role> findRolesForUser(@Param("userId") Long userId);

}
