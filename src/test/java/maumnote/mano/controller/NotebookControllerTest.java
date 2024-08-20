package maumnote.mano.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import maumnote.mano.dto.ApiResponse;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.repository.NotebookRepository;
import maumnote.mano.service.NotebookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NotebookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private NotebookService notebookService; // Mock 객체 주입
    @Autowired
    private NotebookRepository NotebookRepository; // Mock 객체 주입
    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 注入

    @Test
    @Transactional
    public void save() throws Exception {
        // given
        String jsonRequest = "{\"name\":\"test\"}";
        // when
        ResultActions resultActions =  mockMvc.perform(post("/notebook")
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        // then
        // JSON으로 변환
        ApiResponse<ResponseNotebookDto> responseNotebookDto = objectMapper.readValue(responseBody,new TypeReference<ApiResponse<ResponseNotebookDto>>() {});

        // 응답 데이터 검증
        System.out.println(responseNotebookDto.toString());
        assertEquals(200, responseNotebookDto.getStatusCode());
    }
}