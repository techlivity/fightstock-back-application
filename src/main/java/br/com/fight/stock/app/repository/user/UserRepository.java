package br.com.fight.stock.app.repository.user;

import br.com.fight.stock.app.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN FETCH u.roles where u.username = :username")
    Optional<User> findByUsernameFetchRoles(@Param("username") String username);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
