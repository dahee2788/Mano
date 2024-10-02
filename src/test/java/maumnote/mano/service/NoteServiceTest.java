package maumnote.mano.service;

import maumnote.mano.domain.Member;
import maumnote.mano.domain.Note;
import maumnote.mano.domain.NotePhoto;
import maumnote.mano.dto.RequestNoteDto;
import maumnote.mano.dto.ResponseNoteDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.repository.NotePhotoRepository;
import maumnote.mano.repository.NoteRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @Mock
    private NotePhotoRepository notePhotoRepository;

    @InjectMocks
    private NoteService noteService;

    @BeforeEach
    public void setUp() {

        // Mocking SecurityContext
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);

        // Mocking Principal
        Member member = Member.builder().id("test").build();

        // 해당 stubbing이 미사용될 수 있음을 표시
        lenient().when(authentication.getPrincipal()).thenReturn(member);
        lenient().when(securityContext.getAuthentication()).thenReturn(authentication);

        // SecurityContextHolder에 Mocked SecurityContext 설정
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    @DisplayName("일기 생성 성공")
    void createNoteSuccess() {

        // given
        given(noteRepository.save(any())).willReturn(Note.builder()
                .id(1L)
                .notebookId(1L)
                .memberId("test")
                .emotionScore(5)
                .content("content")
                .writeDate(LocalDate.of(2024, 10, 1))
                .build());
        given(notePhotoRepository.saveAll(any())).willReturn(new ArrayList<NotePhoto>());
        RequestNoteDto requestNoteDto = RequestNoteDto.builder()
                .notebookId(1L)
                .emotionScore(5)
                .content("content")
                .photos(List.of("photo1", "photo2", "photo3"))
                .build();

        // when
        ResponseNoteDto note = noteService.createNote(requestNoteDto);

        // then
        Assertions.assertEquals(note.getId(), 1);


    }

    @Test
    @DisplayName("일기 생성 실패")
    void createNoteFailure() {

        // given
        given(noteRepository.save(any())).willReturn(null);
        RequestNoteDto requestNoteDto = RequestNoteDto.builder()
                .notebookId(1L)
                .emotionScore(5)
                .content("content")
                .photos(List.of("photo1", "photo2", "photo3"))
                .build();

        // when
        ManoCustomException exception = Assertions.assertThrows(ManoCustomException.class, () -> noteService.createNote(requestNoteDto));

        // then
        Assertions.assertEquals(ErrorCode.NOTE_CREATE_FAIL, exception.getErrorCode());
    }

    @Test
    @DisplayName("일기 수정 성공")
    void updateNote() {

        // given
        given(noteRepository.save(any())).willReturn(Note.builder()
                .id(1L)
                .notebookId(1L)
                .memberId("test")
                .emotionScore(5)
                .content("content")
                .writeDate(LocalDate.of(2024, 10, 1))
                .build());

        // void 메서드를 Mock할 때 doNothing()을 사용
        given(notePhotoRepository.saveAll(any())).willReturn(List.of(NotePhoto.builder().build(), NotePhoto.builder().build()));

        // when
        RequestNoteDto requestNoteDto = RequestNoteDto.builder()
                .notebookId(1L)
                .emotionScore(5)
                .content("content")
                .photos(List.of("photo1", "photo2"))
                .build();
        ResponseNoteDto responseNoteDto = noteService.updateNote(1L, requestNoteDto);

        // then
        Assertions.assertEquals(responseNoteDto.getId(), 1);


    }

    @Test
    @DisplayName("일기 단건 조회")
    void findNote() {
        // given
        given(noteRepository.findById(any())).willReturn(Optional.of(Note.builder().id(1L).build()));
        given(notePhotoRepository.findAllByNoteId(any(Long.class))).willReturn(List.of(NotePhoto.builder().build(), NotePhoto.builder().build()));

        // when
        ResponseNoteDto note = noteService.findNote(1L);

        // then
        Assertions.assertEquals(note.getId(), 1);
    }

    @Test
    @DisplayName("일기 단건 조회 - null")
    void findNoteNull() {

        // given
        given(noteRepository.findById(any())).willReturn(Optional.empty());

        // when
        ResponseNoteDto note = noteService.findNote(1L);

        // then
        Assertions.assertNull(note);
    }

    @Test
    void findAllNoteByNotebookId() {
        // given
        given(noteRepository.findAllByNotebookIdOrderByIdDesc(any(Long.class)))
                .willReturn(List.of());
        // when
        List<ResponseNoteDto> allNoteByNotebookId = noteService.findAllNoteByNotebookId(1L);

        // then
        Assertions.assertEquals(allNoteByNotebookId.size(), 0);
    }

    @Test
    void deleteNote() {

        // given
        given(noteRepository.existsById(any())).willReturn(false);

        // when
        boolean deleteBoolean = noteService.deleteNote(1L);

        //then
        Assertions.assertTrue(deleteBoolean);
    }
}