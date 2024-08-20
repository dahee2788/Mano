package maumnote.mano.controller;

import jakarta.transaction.Transactional;
import maumnote.mano.repository.NotebookRepository;
import maumnote.mano.service.NotebookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotebookController.class)
class NotebookControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private NotebookService notebookService; // Mock 객체 주입
    @MockBean
    private NotebookRepository NotebookRepository; // Mock 객체 주입

    @Test
    public void save() throws Exception {
        // given
        String jsonRequest = "{\"name\":\"test\"}";
        // when
        ResultActions resultActions =  mockMvc.perform(post("/notebook")
                .contentType("application/json")
                .content(jsonRequest))
                .andExpect(status().isOk());
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        // then
        System.out.println(responseBody);
        assertEquals("", responseBody);
    }
}