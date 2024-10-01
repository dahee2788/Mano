package maumnote.mano.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotePhoto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long noteId;
    private String image;

    public static List<NotePhoto> fromRequestDto(long noteId, List<String> images) {
        return images.stream()
                .map(image -> NotePhoto.builder()
                        .noteId(noteId)
                        .image(image)
                        .build())
                .toList();
    }

}
