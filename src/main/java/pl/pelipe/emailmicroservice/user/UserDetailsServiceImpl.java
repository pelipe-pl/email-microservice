package pl.pelipe.emailmicroservice.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static pl.pelipe.emailmicroservice.config.keys.Keys.LOG_USER_NOT_FOUND;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity = userRepository.getByUsername(username);
        if (userEntity == null) {
            logger.warn(String.format(LOG_USER_NOT_FOUND, username));
            throw new UsernameNotFoundException(String.format(LOG_USER_NOT_FOUND, username));
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (Role role : userEntity.getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getIsActive(),
                true,
                true,
                true,
                grantedAuthorities);
    }

    public void updateLastLogon(String username) {
        UserEntity userEntity = userRepository.getByUsername(username);
        if (userEntity != null) {
            userEntity.setLastLogon(LocalDateTime.now());
            userRepository.save(userEntity);
        }
    }
}