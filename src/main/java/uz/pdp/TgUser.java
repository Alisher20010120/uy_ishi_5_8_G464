package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class TgUser {
    private Long chatId;
    private State state=State.START;
    private User selectedUser;
    private Post selectedPost;
}
