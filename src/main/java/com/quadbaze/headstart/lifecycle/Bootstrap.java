package com.quadbaze.headstart.lifecycle;

import com.quadbaze.headstart.components.Localizer;
import com.quadbaze.headstart.domain.entities.Role;
import com.quadbaze.headstart.domain.entities.User;
import com.quadbaze.headstart.domain.enums.RoleType;
import com.quadbaze.headstart.domain.repositories.RoleRepository;
import com.quadbaze.headstart.domain.repositories.UserRepository;
import com.quadbaze.headstart.logging.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */

@Transactional
@Component
public class Bootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private LoggerService LOGGER;

    @Autowired
    private Localizer localizer;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.onApplicationStartup();
    }

    private void onApplicationStartup() {
        this.initDefaultRolesIfRequired();
        this.initDefaultUserIfRequired();
    }

    /**
     * Generates default role on application startup
     * @return
     */
    private boolean initDefaultRolesIfRequired() {
        List<Role> roles = new ArrayList<>();
        try {
            if (roleRepository.findAll().isEmpty()) {
                for (RoleType roleType : RoleType.values()) {
                    Role role = new Role();
                    role.setRoleType(roleType);
                    role.setDisplayName(roleType.displayName());
                    roles.add(role);
                }
                roles = roleRepository.saveAll(roles);

                LOGGER.info(localizer.getMessage("app.bootstrap.roles-init.success"));
            }else{
                LOGGER.error(localizer.getMessage("app.bootstrap.roles-init.skipped"));
            }
        } catch (Exception ex) {
            LOGGER.error(localizer.getMessage("app.bootstrap.roles-init.failed"), ex);
        }
        return !roles.isEmpty() && roles.size() == RoleType.values().length && roles.stream().allMatch(role -> role.getId() != null);
    }

    /**
     * Generates default user on application startup
     * @return
     */
    private boolean initDefaultUserIfRequired() {
        User user = new User();
        try {
            if (userRepository.findUserByActiveIsTrueAndUsernameAndRoleRoleType(InitConstants.BOOTSRAPPED_USER.EMAIL, RoleType.ROLE_BOOTSTRAPPED_USER) == null) {
                Role role = roleRepository.findRoleByRoleType(RoleType.ROLE_BOOTSTRAPPED_USER);
                if(role != null){
                    user.setUsername(InitConstants.BOOTSRAPPED_USER.EMAIL);
                    user.setPassword(passwordEncoder.encode(InitConstants.BOOTSRAPPED_USER.PASSWORD));
                    user.setRoles(Stream.of(role).collect(Collectors.toList()));
                    user.setActive(true);
                    user = userRepository.save(user);

                    LOGGER.info(localizer.getMessage("app.bootstrap.user.init.success"));
                }
            }else{
                LOGGER.info(localizer.getMessage("app.bootstrap.user.init.skipped"));
            }
        } catch (Exception ex) {
            LOGGER.error(localizer.getMessage("app.bootstrap.user.init.failed"), ex);
        }
        return user.getId() != null;
    }

}
