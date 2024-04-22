package br.com.fight.stock.app.controller.contact;

import br.com.fight.stock.app.domain.Contact;
import br.com.fight.stock.app.exceptions.ContactNotFoundException;
import br.com.fight.stock.app.exceptions.ExcessContactException;
import br.com.fight.stock.app.repository.contact.ContactRepository;
import br.com.fight.stock.app.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contact")
public class ContactController {

    private final ContactService contactService;

    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @PostMapping
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<Contact> createContact(@RequestBody Contact contact) {
        return ResponseEntity.ok().body(contactService.saveContact(contact));
    }

    @GetMapping
    public ResponseEntity<Contact> getContact() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(contactService.getContact());
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('USER_ADMIN')")
    @Operation(security = { @SecurityRequirement(name = "basicScheme") })
    public ResponseEntity<String> deleteContact(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok().body(contactService.deleteContact(id));
    }
}
