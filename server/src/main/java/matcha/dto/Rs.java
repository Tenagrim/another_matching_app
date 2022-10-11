package matcha.dto;

import matcha.base_repo.models.Entity;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rs extends Entity {
    Object data;
    Integer code;
    String message;
}
