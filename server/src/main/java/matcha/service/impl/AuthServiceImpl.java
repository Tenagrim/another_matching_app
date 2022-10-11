package matcha.service.impl;

import matcha.dto.AuthResponse;
import matcha.dto.SignInRequest;
import matcha.dto.SignUpRequest;
import matcha.dto.entity.User;
import matcha.dto.entity.UserAuth;
import matcha.exceptions.UserNotFoundException;
import matcha.repository.UserAutnRepository;
import matcha.repository.UserRepository;
import matcha.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import spark.Request;
import spark.Session;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private UserRepository userRepository;
    private UserAutnRepository userAutnRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public AuthResponse signUp(SignUpRequest request, Session session) {
        User newUser = mapFromRequest(request);
        UserAuth auth = saveUserAuth(newUser); // TODO make a transaction
        newUser.setUserAuthId(auth.getId());
        Long userId = (Long)userRepository.saveAndReturnKey(newUser);
        newUser.setId(userId);

        session.attribute("user",newUser);
        return new AuthResponse(auth.getToken());
    }

    @Override
    public AuthResponse signIn(SignInRequest request, Session session) {
        User user = userRepository.getByField("email", request.getEmail()).orElseThrow(UserNotFoundException::new);
        UserAuth auth = new UserAuth(user.getUserAuthId(), UUID.randomUUID().toString(), new Date());
        userAutnRepository.update(auth);
        return new AuthResponse(auth.getToken());
    }

    private UserAuth saveUserAuth(User user){
        String token = UUID.randomUUID().toString();
        UserAuth auth = new UserAuth(token);
        Long userAuthId = (Long)userAutnRepository.saveAndReturnKey(auth);
        user.setUserAuth(auth);
        auth.setId(userAuthId);
        return auth;
    }

    private User mapFromRequest(SignUpRequest request){
        User newUser = new User();
        newUser.setFirstName(request.getFirstName());
        newUser.setLastName(request.getLastName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        return newUser;
    }





    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Autowired
    public void setUserAutnRepository(UserAutnRepository userAutnRepository) {
        this.userAutnRepository = userAutnRepository;
    }
}
