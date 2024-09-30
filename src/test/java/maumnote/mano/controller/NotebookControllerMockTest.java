package maumnote.mano.controller;

import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.service.NotebookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotebookController.class)
class NotebookControllerMockTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotebookService notebookService; // Mock 객체 주입

    @Test
    @DisplayName("일기장 저장 성공")
    @WithMockUser("testUser")
    public void createOk() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"test\"}";
        given(notebookService.createNotebook(any(RequestNotebookDto.class)))
                .willReturn(ResponseNotebookDto.builder()
                        .id(1234L)
                        .name("test").build());

        // when
        // then
        mockMvc.perform(post("/notebook")
                        .with(csrf())
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data.id").value(1234L))
                .andExpect(jsonPath("$.data.name").value("test"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("일기장 저장 실패")
    @WithMockUser("testUser")
    public void create400Fail() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"\"}";

        // when
        // then
        mockMvc.perform(post("/notebook")
                        .with(csrf())
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                /* json 데이터가 왜 null로 넘어오는지 모르겠음..! => ErrorResponse @getter가 없어서 접근 불가 */
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR.name()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @DisplayName("일기장 조회 성공")
    @WithMockUser("testUser")
    void getNotebooksOk() throws Exception {
        ResponseNotebookDto ResponseNotebook1 = ResponseNotebookDto.builder()
                .id(1234L)
                .name("test1")
                .build();
        ResponseNotebookDto ResponseNotebook2 = ResponseNotebookDto.builder()
                .id(5678L)
                .name("test2")
                .build();
        // given
        List<ResponseNotebookDto> list = List.of(ResponseNotebook1, ResponseNotebook2);
        given(notebookService.findAllNotebook())
                .willReturn(list);

        // when
        // then
        mockMvc.perform(get("/notebook"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2));

    }

    @Test
    @DisplayName("일기장 수정 성공")
    @WithMockUser("testUser")
    void updateNotebookOk() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"일기장 수정 성공\"}";
        long pathParameter = 1234L;
        given(notebookService.updateNotebook(anyLong(), any(RequestNotebookDto.class)))
                .willReturn(ResponseNotebookDto.builder()
                        .id(1234L)
                        .name("일기장 수정 성공")
                        .build());
        // when
        // then
        mockMvc.perform(patch("/notebook/" + pathParameter)
                        .with(csrf())
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("일기장 수정 성공"));
    }

    @Test
    @DisplayName("일기장 수정 실패")
    @WithMockUser("testUser")
    void updateNotebook400Fail() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"일기장 수정 성공\"}";
        String pathParameter = "테스트";

        // when
        // then
        mockMvc.perform(patch("/notebook/" + pathParameter)
                        .with(csrf())
                        .contentType("application/json")
                        .content(jsonRequest))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.INVALID_FORMAT.name()));
    }

    @Test
    @DisplayName("일기장 삭제 성공")
    @WithMockUser("testUser")
    void deleteNotebookOk() throws Exception {

        // given
        long pathParameter = 1234L;

        // when
        // then
        mockMvc.perform(delete("/notebook/" + pathParameter)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk());
        verify(notebookService, times(1)).deleteNotebook(pathParameter);
    }

    @Test
    @DisplayName("일기장 삭제 실패")
    @WithMockUser("testUser")
    void deleteNotebook400Fail() throws Exception {

        // given
        String pathParameter = "테스트";

        // when
        // then
        mockMvc.perform(delete("/notebook/" + pathParameter)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.errorCode").value(ErrorCode.INVALID_FORMAT.name()));
    }
}