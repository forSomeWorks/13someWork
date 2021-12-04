package ua.goit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    private Integer userId;
    private Integer id;
    private String title;
    private Boolean completed;

    public boolean isCompleted() {
        return completed;
    }
}
