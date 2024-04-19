package br.com.fight.stock.app.configuration;

import br.com.fight.stock.app.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;

@Getter
public class UserPrincipal {
    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();

        this.authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_".concat(role.getName()))).toList();
    }

    public static UserPrincipal create(User user) {
        return new UserPrincipal(user);
    }
}
