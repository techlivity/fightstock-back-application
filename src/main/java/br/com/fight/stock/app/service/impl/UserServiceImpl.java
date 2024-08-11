package br.com.fight.stock.app.service.impl;

import br.com.fight.stock.app.controller.authentication.dto.AuthenticationDTO;
import br.com.fight.stock.app.controller.authentication.dto.request.UserRequest;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.domain.Role;
import br.com.fight.stock.app.domain.User;
import br.com.fight.stock.app.exceptions.NotFoundUserException;
import br.com.fight.stock.app.repository.image.ImageRepository;
import br.com.fight.stock.app.repository.user.UserRepository;
import br.com.fight.stock.app.service.user.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static br.com.fight.stock.app.utils.ApiUtils.validatePassword;

@Service
public class UserServiceImpl implements UserService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;
    public UserServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder, ImageRepository imageRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
    }

    @Override
    public User getUser(Long id, String name, String email) {
        if (email != null)
            return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found !"));
        if (id != null)
            return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not found !"));
        if (name != null)
            return userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User Not found !"));
        throw new RuntimeException("You must specie email, id or name");
    }

    @Override
    public String insertImage(String email, MultipartFile file) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
        Image image = Image.createImage(file);
        imageRepository.save(image);
        user.setImage(image);
        userRepository.save(user);
        return "Image upload successfully !";
    }

    @Override
    public String register(UserRequest userRequest) {
        if (userRequest.getRoles().isEmpty()) {
            userRequest.setRoles(List.of(new Role(1, "USER")));
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
            return "Username is already taken!";
        }
        userRequest.setPassword(passwordEncoder.encode(validatePassword(userRequest.getPassword())));

        userRepository.save(new User(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword(), userRequest.getRoles()));
        return "User registered successfully";
    }

    @Override
    public String changePassword(AuthenticationDTO authenticationDTO) {
        User user = userRepository.findByEmail(authenticationDTO.email()).orElseThrow(() -> new NotFoundUserException("User email not found"));
        user.setPassword(passwordEncoder.encode(validatePassword(authenticationDTO.password())));
        userRepository.save(user);
        return "Your password has been successfully changed.";
    }

    @Override
    public String authenticateUser(AuthenticationDTO authenticationDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationDTO.email(), authenticationDTO.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return "User signed-in successfully!.";
    }
}
