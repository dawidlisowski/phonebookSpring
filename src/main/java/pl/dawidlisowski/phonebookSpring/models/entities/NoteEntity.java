package pl.dawidlisowski.phonebookSpring.models.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "notes")
@Data
public class NoteEntity {
    private @Id @GeneratedValue int id;
    private String note;

    @ManyToOne
    @JoinColumn(name = "contact_id")
    private ContactEntity contact;

}
