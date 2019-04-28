package pl.pelipe.emailmicroservice.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public void save(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userEntity.setRoles(new HashSet<>(roleRepository.findAll()));
        userEntity.setCreatedAt(LocalDateTime.now());
        userEntity.setLastChange(LocalDateTime.now());
        userEntity.setIsActive(false);

        userRepository.save(userEntity);
    }

    public void update(UserEntity updatedUser) {
        UserEntity userEntity = userRepository.getById(updatedUser.getId());
        if (updatedUser.getFirstName() != null) userEntity.setFirstName(updatedUser.getFirstName());
        if (updatedUser.getLastName() != null) userEntity.setLastName(updatedUser.getLastName());
        userEntity.setLastChange(LocalDateTime.now());
        userRepository.save(userEntity);
    }

    public UserEntity getById(Long id) {
        return userRepository.getById(id);
    }

    public UserEntity getByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}