package br.com.fight.stock.app.controller.authentication;

import br.com.fight.stock.app.controller.authentication.dto.AuthenticationDTO;
import br.com.fight.stock.app.domain.User;
import br.com.fight.stock.app.exceptions.NotFoundUserException;
import br.com.fight.stock.app.repository.user.RoleRepository;
import br.com.fight.stock.app.repository.user.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static br.com.fight.stock.app.utils.ApiUtils.validatePassword;

@RestController
@RequestMapping("/users/auth")
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthenticationDTO authenticationDTO) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationDTO.email(), authenticationDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/recover")
    public ResponseEntity<String> changePassword(@RequestBody AuthenticationDTO loginDto) {
        User user = userRepository.findByUsername(loginDto.email()).orElseThrow(() -> new NotFoundUserException("User email not found"));
        user.setPassword(passwordEncoder.encode(validatePassword(loginDto.password())));
        userRepository.save(user);
        return new ResponseEntity<>("Your password has been successfully changed.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()))) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(validatePassword(user.getPassword())));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
