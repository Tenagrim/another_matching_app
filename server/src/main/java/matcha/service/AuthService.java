package matcha.service;

import matcha.dto.AuthResponse;
import matcha.dto.SignInRequest;
import matcha.dto.SignUpRequest;
import spark.Session;

public interface AuthService {
    AuthResponse signUp(SignUpRequest request, Session session);

    public AuthResponse signIn(SignInRequest request, Session session);
}
