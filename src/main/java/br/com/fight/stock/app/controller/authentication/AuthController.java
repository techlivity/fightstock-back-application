package br.com.fight.stock.app.controller.authentication;

import br.com.fight.stock.app.controller.authentication.dto.AuthenticationDTO;
import br.com.fight.stock.app.controller.authentication.dto.request.UserRequest;
import br.com.fight.stock.app.domain.Image;
import br.com.fight.stock.app.domain.User;
import br.com.fight.stock.app.exceptions.NotFoundUserException;
import br.com.fight.stock.app.repository.image.ImageRepository;
import br.com.fight.stock.app.repository.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static br.com.fight.stock.app.utils.ApiUtils.validatePassword;

@RestController
@RequestMapping("/users/auth")
@Validated
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageRepository imageRepository;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder, ImageRepository imageRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageRepository = imageRepository;
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
        User user = userRepository.findByEmail(loginDto.email()).orElseThrow(() -> new NotFoundUserException("User email not found"));
        user.setPassword(passwordEncoder.encode(validatePassword(loginDto.password())));
        userRepository.save(user);
        return new ResponseEntity<>("Your password has been successfully changed.", HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {

        if (Boolean.TRUE.equals(userRepository.existsByEmail(userRequest.getEmail()))) {
            return new ResponseEntity<>("Username is already taken!", HttpStatus.BAD_REQUEST);
        }
        userRequest.setPassword(passwordEncoder.encode(validatePassword(userRequest.getPassword())));

        userRepository.save(new User(userRequest.getEmail(), userRequest.getName(), userRequest.getPassword(), userRequest.getRoles()));

        return new ResponseEntity<>("User registered successfully", HttpStatus.OK);
    }

    @PostMapping("{email}")
    public ResponseEntity<String> insertImageOnUser(@NotNull @RequestParam(name = "file") MultipartFile file, @PathVariable(name = "email") String email) throws IOException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
        Image image = Image.createImage(file);
        imageRepository.save(image);
        user.setImage(image);
        userRepository.save(user);
        return ResponseEntity.ok("Image upload successfully !");
    }

    @GetMapping
    public ResponseEntity<?> getUser(@RequestParam(name = "email", required = false) String email,
                                     @RequestParam(name = "id", required = false) Long id,
                                     @RequestParam(name = "name", required = false) String name) {
        if (email != null)
            return ResponseEntity.ok(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not found !")));
        if (id != null)
            return ResponseEntity.ok(userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User Not found !")));
        if (name != null)
            return ResponseEntity.ok(userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User Not found !")));
        return ResponseEntity.badRequest().body("You must specie email, id or name");
    }
}
