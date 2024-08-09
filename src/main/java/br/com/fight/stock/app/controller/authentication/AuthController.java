package br.com.fight.stock.app.controller.authentication;

import br.com.fight.stock.app.controller.authentication.dto.AuthenticationDTO;
import br.com.fight.stock.app.controller.authentication.dto.request.UserRequest;
import br.com.fight.stock.app.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/users/auth")
@Validated
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signin")
    @Operation(summary = "Loga usuário")
    public ResponseEntity<String> authenticateUser(@Valid @RequestBody AuthenticationDTO authenticationDTO) {

        return new ResponseEntity<>(userService.authenticateUser(authenticationDTO), HttpStatus.OK);
    }

    @PostMapping("/recover")
    @Operation(summary = "Altera senha de usuário")
    public ResponseEntity<String> changePassword(@RequestBody AuthenticationDTO loginDto) {
        return new ResponseEntity<>(userService.changePassword(loginDto), HttpStatus.OK);
    }

    @PostMapping("/signup")
    @Operation(summary = "Cadastra usuário")
    public ResponseEntity<String> registerUser(@RequestBody UserRequest userRequest) {
        return new ResponseEntity<>(userService.register(userRequest), HttpStatus.OK);
    }

    @PostMapping(value = "{email}", consumes = "multipart/form-data")
    @Operation(summary = "Insere uma imagem em um usuário")
    public ResponseEntity<String> insertImageOnUser(@NotNull @RequestParam(name = "file") MultipartFile file,
                                                    @PathVariable(name = "email") String email) throws IOException {
        return ResponseEntity.ok(userService.insertImage(email, file));
    }

    @GetMapping
    @Operation(summary = "Recupera usuario com parametro")
    public ResponseEntity<br.com.fight.stock.app.domain.User> getUserWithParam(@RequestParam(name = "email", required = false) String email,
                                                                               @RequestParam(name = "id", required = false) Long id,
                                                                               @RequestParam(name = "name", required = false) String name) {
        return ResponseEntity.ok().body(userService.getUser(id, name, email));
    }
}
