package matcha.dto;

import lombok.Data;

@Data
public class SignInRequest {
    String email;
    String password;
}
