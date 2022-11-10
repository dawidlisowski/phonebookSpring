package pl.dawidlisowski.phonebookSpring.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "contact")
@Data
public class ContactEntity {
    @Id
    @GeneratedValue
    private int id;

    private String name;
    private String surname;

    @Column(name = "phone")
    private String number;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @OneToMany(mappedBy = "contact", fetch = FetchType.EAGER)
    private List<NoteEntity> notes;

    @Override
    public String toString() {
        return "name: " + name + '\n' +
                " surname: " + surname + '\n' +
                " number: " + number + '\n';
    }
}