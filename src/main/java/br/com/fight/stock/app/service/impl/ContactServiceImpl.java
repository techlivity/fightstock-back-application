package br.com.fight.stock.app.service.impl;

import br.com.fight.stock.app.domain.Contact;
import br.com.fight.stock.app.exceptions.CepNotFoundException;
import br.com.fight.stock.app.exceptions.ContactNotFoundException;
import br.com.fight.stock.app.exceptions.ErrorCallApiException;
import br.com.fight.stock.app.exceptions.ExcessContactException;
import br.com.fight.stock.app.repository.contact.ContactRepository;
import br.com.fight.stock.app.service.contact.ContactService;
import br.com.fight.stock.app.service.cep.ViaCepService;
import org.springframework.stereotype.Service;

import static br.com.fight.stock.app.utils.ApiUtils.formatMessage;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final ViaCepService viaCepService;

    public ContactServiceImpl(ContactRepository contactRepository, ViaCepService viaCepService) {
        this.contactRepository = contactRepository;
        this.viaCepService = viaCepService;
    }

    public Contact saveContact(Contact contact) {
        validateCepForAddress(contact);
        if (contactRepository.findAll().isEmpty()) {
            return contactRepository.save(contact);
        }
        throw new ExcessContactException("Exceeded the allowed number of contacts");
    }

    @Override
    public Contact getContact() {
        return contactRepository
                .findAll()
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ContactNotFoundException("Contacts not found!"));
    }

    @Override
    public String deleteContact(Long id) {
        contactRepository.deleteById(id);
        return "Deleted successfully !";
    }

    private void validateCepForAddress(Contact contact) {
        validateCep(contact);
    }

    private void validateCep(Contact contact) {
        try{
            String cep = viaCepService.getCep(contact.getAddress().getCep());
            if(cep == null) {
                throw new CepNotFoundException(formatMessage("CEP : %s not found", cep));
            }
        }catch (Exception e) {
            throw new ErrorCallApiException(formatMessage("Error on callApi viacep stacktrace: %s", e.getMessage()));
        }
    }
}
