package com.quadbaze.headstart.services.authentication;

import com.quadbaze.headstart.components.Localizer;
import com.quadbaze.headstart.domain.entities.User;
import com.quadbaze.headstart.domain.repositories.UserRepository;
import com.quadbaze.headstart.logging.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */

@Transactional
@Service("authenticationUserDetailsService")
public class AuthenticationUserDetailsService implements UserDetailsService {

    @Autowired
    private LoggerService LOGGER;

    private UserRepository userRepository;
    private Localizer localizer;

    @Autowired
    public AuthenticationUserDetailsService(
            UserRepository userRepository,
            Localizer localizer){
        this.userRepository = userRepository;
        this.localizer = localizer;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try{
            LOGGER.info(localizer.getProperty("app.user.basic-authentication.intro", username));
            User client = userRepository.findUserByActiveIsTrueAndUsername(username);
            if(client != null){
                LOGGER.info(localizer.getProperty("app.user.basic-authentication.success", username));
                return new AuthenticationUserPrincipal(client);
            }
            throw new UsernameNotFoundException(username);
        }catch (Exception ex){
            LOGGER.error(localizer.getProperty("app.user.basic-authentication.failed", username), ex);
            throw ex;
        }
    }
}
