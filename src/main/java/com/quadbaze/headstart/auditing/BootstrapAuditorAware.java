package com.quadbaze.headstart.auditing;

import com.quadbaze.headstart.lifecycle.InitConstants;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */
public class BootstrapAuditorAware implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(InitConstants.BOOTSRAPPED_USER.EMAIL);
    }
}
