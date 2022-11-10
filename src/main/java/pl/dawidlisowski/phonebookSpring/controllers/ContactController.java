package pl.dawidlisowski.phonebookSpring.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.dawidlisowski.phonebookSpring.models.UserSession;
import pl.dawidlisowski.phonebookSpring.models.forms.ContactForm;
import pl.dawidlisowski.phonebookSpring.models.forms.NoteForm;
import pl.dawidlisowski.phonebookSpring.models.services.ContactService;
import pl.dawidlisowski.phonebookSpring.models.services.NoteService;

import javax.validation.Valid;

@Controller
public class ContactController {

//    @Autowired
//    ContactService contactService;

    final ContactService contactService;
    final UserSession userSession;
    final NoteService noteService;

    @Autowired
    public ContactController(ContactService contactService, UserSession userSession, NoteService noteService) {
        this.contactService = contactService;
        this.userSession = userSession;
        this.noteService = noteService;
    }

    @GetMapping("/contact/add")
    public String showAddContactForm (Model model) {
        model.addAttribute("contactForm", new ContactForm());
        return "addContact";
    }

    @PostMapping("/contact/add")
    public String getDataFromAddForm (@ModelAttribute @Valid ContactForm contactForm,
                                      BindingResult bindingResult,
                                      Model model) {
        if (!userSession.isLogin()) {
            return "redirect:/user/login";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("contactInfo", "Błędne dane");
            return "addContact";
        }
        contactService.addContact(contactForm);
        return "addContact"; //todo change after save data
    }

    @GetMapping("/contact/show")
    public String showAllContacts(Model model) {
        if (!userSession.isLogin()) {
            return "redirect:/user/login";
        }
        model.addAttribute("contacts", contactService.getContactsForLoginUser());
        return "contactsList";
    }

    @GetMapping("/contact/show/id/{id}")
    @ResponseBody
    public String showOneContact(@PathVariable("id") int contactId) {
        return contactService.findOneContact(contactId)
                .map(s -> s.toString())
                .orElse("Contact with this Id not exist");
    }

    @GetMapping("/contact/show/surname/{surname}")
    @ResponseBody
    public String showOneContactSurname(@PathVariable("surname") String surname) {
        return contactService.findOneContact(surname)
                .map(s -> s.toString())
                .orElse("Contact with this surname not exist");
    }

    @GetMapping("/contact/delete/{contactId}")
    public String deleteContact(@PathVariable("contactId") int id) {
        contactService.deleteContact(id);
        return "redirect:/contact/show";
    }

    @GetMapping("/contact/note/{id}")
    public String showAllNotes(@PathVariable("id") int id,
                               Model model) {
        if (!userSession.isLogin()) {
            return "redirect:/user/login";
        }
        model.addAttribute("contactData", contactService.getAllContactData(id));
        model.addAttribute("noteForm", new NoteForm());
        return "showNotes";
    }

    @PostMapping("/note/{id}")
    public String addNote(@PathVariable("id") int contactId,
                          @ModelAttribute("noteForm") NoteForm note) {
        if (!userSession.isLogin()) {
            return "redirect:/user/login";
        }
        noteService.addNote(note, contactId);
        return "redirect:/contact/note/" + contactId;
    }

    @GetMapping("/contact/note/delete/{contactId}/{noteId}")
    public String deleteNote(@PathVariable("noteId") int noteId,
                             @PathVariable("contactId") int contactId) {
        noteService.deleteNote(noteId);
        return "redirect:/contact/note/" + contactId;
    }
}
