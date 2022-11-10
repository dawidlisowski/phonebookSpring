package pl.dawidlisowski.phonebookSpring.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.dawidlisowski.phonebookSpring.models.UserSession;
import pl.dawidlisowski.phonebookSpring.models.entities.ContactEntity;
import pl.dawidlisowski.phonebookSpring.models.entities.NoteEntity;
import pl.dawidlisowski.phonebookSpring.models.forms.NoteForm;
import pl.dawidlisowski.phonebookSpring.models.repositories.NoteRepository;

import java.util.List;

@Service
public class NoteService {

    final NoteRepository noteRepository;

    final UserSession userSession;

    @Autowired
    public NoteService(NoteRepository noteRepository, UserSession userSession) {
        this.noteRepository = noteRepository;
        this.userSession = userSession;
    }

    public void addNote(NoteForm noteForm, int contactId) {
        NoteEntity newNote = createNoteEntity(noteForm, contactId);
        noteRepository.save(newNote);
    }

    private NoteEntity createNoteEntity(NoteForm noteForm, int contactId) {
        NoteEntity newNote = new NoteEntity();
        ContactEntity contactEntity = new ContactEntity();
        contactEntity.setId(contactId);

        newNote.setNote(noteForm.getNote());
        newNote.setContact(contactEntity);
        return newNote;
    }

    public void deleteNote(int id) {
        if (userSession.isLogin()) {
            noteRepository.deleteById(id);
        }
    }
}
