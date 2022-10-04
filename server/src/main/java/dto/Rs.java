package dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Rs {
    Object data;
    Integer code;
    String message;
}
