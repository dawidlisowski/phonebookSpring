package pl.dawidlisowski.phonebookSpring.models.forms;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class NoteForm {
    @Size(max = 250)
    private String note;

    @Override
    public String toString() {
        return note;
    }
}
