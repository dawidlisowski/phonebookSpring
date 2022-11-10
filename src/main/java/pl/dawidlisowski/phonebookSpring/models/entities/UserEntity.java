package pl.dawidlisowski.phonebookSpring.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "user")
@Data
public class UserEntity {
    private @Id @GeneratedValue int id;
    private String login;
    private String password;
    private @Column(name = "creation_time") LocalDateTime creationTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<ContactEntity> contacts;

}
