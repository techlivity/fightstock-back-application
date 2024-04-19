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

@RestController
@RequestMapping("/users/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthenticationDTO authenticationDTO){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authenticationDTO.email(), authenticationDTO.password()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return new ResponseEntity<>("User signed-in successfully!.", HttpStatus.OK);
    }

    @PostMapping("/recover")
    public ResponseEntity<String> changePassword(@RequestBody AuthenticationDTO loginDto){
        User user = userRepository.findByUsername(loginDto.email()).orElseThrow(() -> new NotFoundUserException("User email not found"));

        user.setPassword(loginDto.password());
        userRepository.save(user);
        return new ResponseEntity<>("Your password has been successfully changed.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody User user){

        if(Boolean.TRUE.equals(userRepository.existsByUsername(user.getUsername()))){
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }
}
