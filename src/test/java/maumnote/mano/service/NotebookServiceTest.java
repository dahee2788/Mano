package maumnote.mano.service;

import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NotebookServiceTest {

    @Autowired
    private NotebookService notebookService;

    @Test
    void save() {
        // given
        RequestNotebookDto dto = new RequestNotebookDto();
        dto.setName("하이");
        // when
        ResponseNotebookDto responseNotebookDto = notebookService.save(dto);
        // then
        System.out.println(responseNotebookDto.toString());
        Assertions.assertNotNull(responseNotebookDto);
    }

}