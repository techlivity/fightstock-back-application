package br.com.fight.stock.app.repository.contact;

import br.com.fight.stock.app.domain.ContactModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<ContactModel, Long> {
}
