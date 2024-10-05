package maumnote.mano.service;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Note;
import maumnote.mano.domain.NotePhoto;
import maumnote.mano.dto.RequestNoteDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.NotePhotoRepository;
import maumnote.mano.repository.NoteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;
    private final NotePhotoRepository notePhotoRepository;

    @Transactional
    public ResponseNoteDto createNote(RequestNoteDto requestNoteDto) {

        Note requestNote = Note.fromRequestDto(requestNoteDto);
        Note saveNote = noteRepository.save(requestNote);
        if (ObjectUtils.isEmpty(saveNote)) {
            throw new ManoCustomException(ErrorCode.NOTE_CREATE_FAIL);
        } else {
            if (!requestNoteDto.getPhotos().isEmpty()) {
                List<NotePhoto> requestPhotos = NotePhoto.fromRequestDto(saveNote.getId(), requestNoteDto.getPhotos());
                notePhotoRepository.saveAll(requestPhotos);
            }
        }

        return Note.toSaveResponseDto(saveNote);
    }

    @Transactional
    public ResponseNoteDto updateNote(long id, RequestNoteDto requestNoteDto) {

        Note requestNote = Note.fromRequestDto(id, requestNoteDto);
        Note saveNote = noteRepository.save(requestNote);

        if (!requestNoteDto.getPhotos().isEmpty()) {
            notePhotoRepository.deleteAllByNoteId(id);

            List<NotePhoto> requestPhotos = NotePhoto.fromRequestDto(saveNote.getId(), requestNoteDto.getPhotos());
            notePhotoRepository.saveAll(requestPhotos);
        }

        return Note.toSaveResponseDto(saveNote);
    }

    @Transactional
    public ResponseNoteDto findNote(long id) {

        return noteRepository.findById(id)
                .map(note -> {
                    List<NotePhoto> notePhotos = notePhotoRepository.findAllByNoteId(id);
                    return Note.toFindResponseDto(note, notePhotos);
                })
                .orElse(null);
    }

    @Transactional
    public List<ResponseNoteDto> findAllNoteByNotebookId(long notebookId) {

        List<Note> notes = noteRepository.findAllByNotebookIdOrderByIdDesc(notebookId);
        return Note.toFindListResponseDto(notes);
    }

    @Transactional
    public boolean deleteNote(long id) {

        noteRepository.deleteById(id);
        notePhotoRepository.deleteAllByNoteId(id);
        return !noteRepository.existsById(id);
    }
    // 통계
}
