package com.telco.venta.serviceImpl;

import com.telco.venta.entity.User;
import com.telco.venta.enums.UserRole;
import com.telco.venta.exception.BusinessException;
import com.telco.venta.exception.enums.BusinessExceptionReason;
import com.telco.venta.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User createUser(String username, String password, String role, Integer supervisorId) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new BusinessException( BusinessExceptionReason.ENTITY_EXITS,"EL usuario " + username );
        }
        UserRole userRole = UserRole.valueOf(role.toUpperCase());
        User user = User.builder()
                .username(username)
                .passwordHash(this.passwordEncoder.encode(password))
                .role(userRole)
                .supervisorId( supervisorId != null ? Long.valueOf(supervisorId) : null)
                .activo(true)
                .build();
        return userRepository.save(user);
    }

    public Long getUserIdByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException( BusinessExceptionReason.ENTITY_NOT_FOUND,"El usuario: " + username));
        return user.getId();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException( BusinessExceptionReason.ENTITY_NOT_FOUND,"El usuario con el id: " + id));
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return getUserIdByUsername(authentication.getName());

    }

//    public Long getSupervisorId(Long agenteId) {
//        User user = getUserById(agenteId);
//        return user.getSupervisorId();
//    }

}
