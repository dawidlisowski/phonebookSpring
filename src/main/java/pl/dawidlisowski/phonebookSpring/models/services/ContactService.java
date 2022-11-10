package pl.dawidlisowski.phonebookSpring.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawidlisowski.phonebookSpring.models.UserSession;
import pl.dawidlisowski.phonebookSpring.models.entities.ContactEntity;
import pl.dawidlisowski.phonebookSpring.models.forms.ContactForm;
import pl.dawidlisowski.phonebookSpring.models.repositories.ContactRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {
    final ContactRepository contactRepository;
    final UserSession userSession;

    @Autowired
    public ContactService(ContactRepository contactRepository, UserSession userSession) {
        this.contactRepository = contactRepository;
        this.userSession = userSession;
    }

    public boolean checkIfContactExists(String surname) {
        return contactRepository.existsBySurname(surname);
    }
    public void addContact(ContactForm contactForm) {
        ContactEntity newContact = new ContactEntity();
        newContact.setName(contactForm.getName());
        newContact.setSurname(contactForm.getSurname());
        newContact.setNumber(contactForm.getPhone());
        newContact.setUser(userSession.getUserEntity());

        contactRepository.save(newContact);
    }

    public Optional<ContactEntity> findOneContact(int id) {
        return contactRepository.findById(id);
    }

    public Optional<ContactEntity> findOneContact(String surname) {
        return contactRepository.findContactBySurname(surname);
    }

    public List<ContactEntity> getContacts() {
        return contactRepository.findAllContacts();
    }

    public List<ContactEntity> getContactsForLoginUser() {
        return contactRepository.findAllByUser_Id(userSession.getUserEntity().getId());
    }

    public void deleteContact(int id) {
        if (userSession.isLogin()) {
            if (userSession.getUserEntity().getContacts().stream()
                    .anyMatch(s -> s.getId() == id)) {
                contactRepository.deleteById(id);
            }
        }
    }

    public ContactEntity getAllContactData(int id) {
        return contactRepository.findById(id).get();
    }

}
