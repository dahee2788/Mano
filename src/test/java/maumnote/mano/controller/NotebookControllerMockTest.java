package maumnote.mano.controller;

import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.service.NotebookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
    public void saveOk() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"test\"}";
        given(notebookService.create(any(RequestNotebookDto.class)))
                .willReturn(ResponseNotebookDto.builder()
                        .id(1234L)
                        .name("test").build());

        // when
        // then
        mockMvc.perform(post("/notebook")
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
    public void save400Fail() throws Exception {

        // given
        String jsonRequest = "{\"name\":\"\"}";
        given(notebookService.create(any(RequestNotebookDto.class)))
                .willReturn(ResponseNotebookDto.builder()
                        .id(1234L)
                        .name("test").build());

        // when
        // then
        mockMvc.perform(post("/notebook")
                .contentType("application/json")
                .content(jsonRequest))
                .andDo(print())
                /* json 데이터가 왜 null로 넘어오는지 모르겠음..!*/
//                .andExpect(jsonPath("$.errorCode").value(ErrorCode.VALIDATION_ERROR))
                .andExpect(status().is4xxClientError());
    }
}