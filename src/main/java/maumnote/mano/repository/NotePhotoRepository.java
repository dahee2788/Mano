package maumnote.mano.repository;

import maumnote.mano.domain.NotePhoto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotePhotoRepository extends JpaRepository<NotePhoto, Long> {
    void deleteAllByNoteId(long noteId);

    List<NotePhoto> findAllByNoteId(long noteId);
}
