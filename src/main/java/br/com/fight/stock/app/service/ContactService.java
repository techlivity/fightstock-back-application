package br.com.fight.stock.app.service;

import br.com.fight.stock.app.domain.Contact;

public interface ContactService {

    Contact saveContact(Contact contact);

    Contact getContact();

    String deleteContact(Long id);
}
