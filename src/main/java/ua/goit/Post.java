package ua.goit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private Integer userId;
    private Integer id;
    private String title;
    private String body;

}
