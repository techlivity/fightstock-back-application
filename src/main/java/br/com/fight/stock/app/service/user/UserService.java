package br.com.fight.stock.app.service.user;

import br.com.fight.stock.app.controller.authentication.dto.AuthenticationDTO;
import br.com.fight.stock.app.controller.authentication.dto.request.UserRequest;
import br.com.fight.stock.app.domain.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User getUser(Long id, String name, String email);
    String insertImage(String email, MultipartFile file) throws IOException;
    String register(UserRequest userRequest);
    String changePassword(AuthenticationDTO authenticationDTO);
    String authenticateUser(AuthenticationDTO authenticationDTO);
}
