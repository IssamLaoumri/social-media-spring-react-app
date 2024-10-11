package com.laoumri.springbootbackend.servicesImpl;

import com.laoumri.springbootbackend.Exceptions.EmailAddressAlreadyExists;
import com.laoumri.springbootbackend.Exceptions.RoleNotFoundException;
import com.laoumri.springbootbackend.dto.requests.LoginRequest;
import com.laoumri.springbootbackend.dto.requests.RegisterRequest;
import com.laoumri.springbootbackend.dto.responses.AuthResponse;
import com.laoumri.springbootbackend.entities.Role;
import com.laoumri.springbootbackend.entities.User;
import com.laoumri.springbootbackend.enums.ERole;
import com.laoumri.springbootbackend.repositories.RoleRepository;
import com.laoumri.springbootbackend.repositories.UserRepository;
import com.laoumri.springbootbackend.security.services.JwtUtils;
import com.laoumri.springbootbackend.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authManager;

    @Override
    public String register(RegisterRequest registerRequest) {
        if(userRepository.existsByEmail(registerRequest.getEmail())) {
            throw new EmailAddressAlreadyExists();
        }
        Set<Role> roles = new HashSet<>();
        if(registerRequest.getRoles() == null) {
            Role role = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(()-> new RoleNotFoundException(ERole.ROLE_USER.name()));
            roles.add(role);
        } else  {
            registerRequest.getRoles().forEach(role -> {
                try {
                    Role roleEntity = roleRepository.findByName(ERole.valueOf(role))
                            .orElseThrow(()-> new RoleNotFoundException(role));
                    roles.add(roleEntity);
                }catch (IllegalArgumentException e){
                    throw new RoleNotFoundException(role);
                }
            });
        }
        User user = User.builder()
                .email(registerRequest.getEmail())
                ._username(generateUniqueUsername(registerRequest.getFirstname(), registerRequest.getLastname()))
                .firstname(registerRequest.getFirstname())
                .lastname(registerRequest.getLastname())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);
        return "user registered successfully.";
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        Authentication authentication = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        User user = (User) authentication.getPrincipal();
        Set<String> roles = user.getRoles().stream().map(Role::getAuthority).collect(Collectors.toSet());
        return AuthResponse.builder()
                .email(user.getEmail())
                .roles(roles)
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .token(jwt)
                .username(user.get_username())
                .build();
    }

    private String generateUniqueUsername(String firstname, String lastname) {
        boolean a;
        String username = (firstname+"."+lastname).toLowerCase();
        do{
            boolean check = userRepository.existsBy_username(username);
            if(check){
                username += String.valueOf(System.currentTimeMillis() * Math.random()).substring(0, 1);
                a = true;
            } else {
                a = false;
            }
        }while(a);
        return username;
    }
}
