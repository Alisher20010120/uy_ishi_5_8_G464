package uz.pdp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Comment {
    private Integer post_id;
    private Integer id;
    private String name;
    private String email;
    private String body;
}
