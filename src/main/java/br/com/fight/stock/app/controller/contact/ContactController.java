package br.com.fight.stock.app.controller.contact;

import br.com.fight.stock.app.domain.ContactModel;
import br.com.fight.stock.app.repository.contact.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    private final ContactRepository contactRepository;

    public ContactController(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @PostMapping
    public ResponseEntity<ContactModel> createContact(@RequestBody ContactModel contactModel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactRepository.save(contactModel));
    }

    @GetMapping
    public ResponseEntity<List<ContactModel>> getAllContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(contactRepository.findAll());
    }
}
