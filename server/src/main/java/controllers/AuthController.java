package controllers;

import dto.Message;
import dto.Rs;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;

@Slf4j
public abstract class AuthController {

    public static Rs serveHomePage(Request request, Response response) {
        return Rs.builder()
                .data(new Message("This is Homepage"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }

    public static Rs serveSignIn(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Getting sign data"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }


    public static Rs serveSignUp(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Getting sign up"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }

    public static Rs handleSignIn(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Trying Sign In"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }

    public static Rs handleSignUp(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Tying Sign Up"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }

    public static Rs handleSignOut(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Trying Sign Out"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }

    public static Rs handleAuth(Request request, Response response) {
        return Rs.builder()
                .data(new Message("Trying Authorization"))
                .code(HttpStatus.OK_200)
                .message(HttpStatus.getMessage(200))
                .build();
    }
}
