package br.com.fight.stock.app.controller.contact;

import br.com.fight.stock.app.domain.Contact;
import br.com.fight.stock.app.exceptions.ExcessContactException;
import br.com.fight.stock.app.repository.contact.ContactRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('USER_ADMIN')")
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        if(contactRepository.findAll().isEmpty()){
            return ResponseEntity.status(HttpStatus.CREATED).body(contactRepository.save(contact));
        }
        throw new ExcessContactException("Excedeu a quantidade permitida de contatos");
    }

    @GetMapping
    public ResponseEntity<List<Contact>> getAllContacts() {
        return ResponseEntity.status(HttpStatus.OK).body(contactRepository.findAll());
    }
}
