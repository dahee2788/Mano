package maumnote.mano.service;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Note;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.repository.NoteRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {

    private final NoteRepository noteRepository;

    // 일기 등록
    public ResponseNoteDto createNote(Note note) {
        noteRepository.save(note);
        return new ResponseNoteDto();
    }
    // 일기 수정
    // 일기 조회
    // 일기 목록 조회
    // 일기 삭제
    // 통계
}
