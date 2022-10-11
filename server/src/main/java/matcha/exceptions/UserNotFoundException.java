package matcha.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class UserNotFoundException extends AppException{
    public UserNotFoundException() {
        super(HttpStatus.UNAUTHORIZED_401);
    }
}
