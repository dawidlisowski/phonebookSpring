package pl.dawidlisowski.phonebookSpring.models.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.dawidlisowski.phonebookSpring.models.entities.NoteEntity;

import java.util.List;

@Repository
public interface NoteRepository extends CrudRepository<NoteEntity, Integer> {

    List<NoteEntity> findAllByContact_Id(int id);
}
