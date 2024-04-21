package br.com.fight.stock.app.configuration.security.principal;

import br.com.fight.stock.app.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Getter
public class UserPrincipal {
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();

        this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName()))).toList();
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }
}
