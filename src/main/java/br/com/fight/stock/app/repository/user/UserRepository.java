package br.com.fight.stock.app.repository.user;

import br.com.fight.stock.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles where u.email = :email")
    Optional<User> findByEmailFetchRoles(@Param("email") String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByName(String name);
    Boolean existsByEmail(String email);
}
