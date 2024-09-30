package maumnote.mano.service;

import lombok.RequiredArgsConstructor;
import maumnote.mano.domain.Member;
import maumnote.mano.domain.Notebook;
import maumnote.mano.domain.NotebookPermission;
import maumnote.mano.dto.RequestNotebookDto;
import maumnote.mano.dto.ResponseNotebookDto;
import maumnote.mano.exception.ErrorCode;
import maumnote.mano.exception.ManoCustomException;
import maumnote.mano.global.util.SecurityContextUtil;
import maumnote.mano.repository.NotebookPermissionRepository;
import maumnote.mano.repository.NotebookRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotebookService {
    private final NotebookRepository notebookRepository;
    private final NotebookPermissionRepository notebookPermissionRepository;


    public ResponseNotebookDto createNotebook(RequestNotebookDto requestNotebookDto) {

        Notebook requestNotebook = Notebook.fromRequestDto(requestNotebookDto);
        Notebook saveNotebook = notebookRepository.save(requestNotebook);

        if (ObjectUtils.isEmpty(saveNotebook)) {
            throw new ManoCustomException(ErrorCode.NOTEBOOK_CREATE_FAIL);
        } else {
            NotebookPermission notebookPermission = NotebookPermission.from(saveNotebook.getId());
            notebookPermissionRepository.save(notebookPermission);
        }

        return Notebook.toResponseDto(saveNotebook);
    }

    public List<ResponseNotebookDto> findAllNotebook() {

        Member principal = SecurityContextUtil.getAuthenticationMember();
        List<NotebookPermission> notebookPermissions = notebookPermissionRepository.findByMemberId(principal.getId());

        List<Notebook> notebooks = notebookRepository.findAllByIdOrderedByCreateDate(notebookPermissions.stream()
                .map(NotebookPermission::getNotebookId)
                .toList());

        return notebooks.stream().map(Notebook::toResponseDto).toList();
    }

    public ResponseNotebookDto updateNotebook(long id, RequestNotebookDto requestNotebookDto) {

        Notebook requestNotebook = Notebook.fromRequestDto(id, requestNotebookDto);
        Notebook saveNotebook = notebookRepository.save(requestNotebook);

        return Notebook.toResponseDto(saveNotebook);
    }

    public boolean deleteNotebook(long id) {

        notebookRepository.deleteById(id);

        List<NotebookPermission> notebookPermissions = notebookPermissionRepository.findAllByNotebookId(id);
        notebookPermissionRepository.deleteAll(notebookPermissions);

        return notebookRepository.findById(id).isEmpty();
    }

}
