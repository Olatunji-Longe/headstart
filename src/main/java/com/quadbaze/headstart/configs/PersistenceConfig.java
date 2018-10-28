package com.quadbaze.headstart.configs;

import com.quadbaze.headstart.auditing.BootstrapAuditorAware;
import com.quadbaze.headstart.auditing.UsernameAuditorAware;
import com.quadbaze.headstart.domain.enums.RoleType;
import com.quadbaze.headstart.lifecycle.InitConstants;
import com.quadbaze.headstart.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorProvider")
@EntityScan(basePackages = {"com.quadbaze.headstart.domain.entities"})
@EnableJpaRepositories(basePackages = {"com.quadbaze.headstart.domain.repositories"})
public class PersistenceConfig {

    @Autowired
    private UserService userService;

    @Bean
    public AuditorAware<String> auditorProvider() {
        return userService.getUser(InitConstants.BOOTSRAPPED_USER.EMAIL, RoleType.ROLE_BOOTSTRAPPED_USER) != null ? new UsernameAuditorAware() : new BootstrapAuditorAware();
    }

}
