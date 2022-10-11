package matcha.dto;

import lombok.Data;

@Data
public class SignUpRequest {
    String firstName;
    String lastName;
    String email;
    String password;

}
