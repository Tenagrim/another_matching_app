import com.google.gson.Gson;
import controllers.AuthController;
import dto.User;
import lombok.extern.slf4j.Slf4j;
import constants.Path;
import spark.debug.DebugScreen;

import static spark.Spark.*;

@Slf4j
public class Main {

    private static final String JSON_MIME_TYPE = "application/json";
    private static final String SERVER_HOST = "127.0.0.1";
    private static final Integer SERVER_PORT = 8080;

    public static User user;

    public static void main(String[] args) {

//      Server configuration
        ipAddress(SERVER_HOST);
        port(SERVER_PORT);
        DebugScreen.enableDebugScreen();

//      Init
        user = new User();
        Gson gson = new Gson();

//      Routes
        before("/*",  (q, a) -> log.info("New request"));

        get(Path.HOME, AuthController::serveHomePage, gson::toJson);

        path(Path.API, () -> {
            post(Path.Web.AUTH, AuthController::handleAuth, gson::toJson);
            path(Path.Web.SIGN,() -> {
                get(Path.Web.SIGN_IN,   AuthController::serveSignIn,    gson::toJson);
                get(Path.Web.SIGN_UP,   AuthController::serveSignUp,    gson::toJson);
                get(Path.Web.SIGN_OUT,  AuthController::handleSignOut,  gson::toJson);
                post(Path.Web.SIGN_IN,  AuthController::handleSignIn,   gson::toJson);
                post(Path.Web.SIGN_UP,  AuthController::handleSignUp,   gson::toJson);
            });
        });

        after((request, response) -> response.type(JSON_MIME_TYPE));
    }
}
