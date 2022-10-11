package matcha.controller;

import matcha.dto.entity.User;

import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;
import spark.Route;


@Component
public class UsersGetController implements Route {

    @Override
    public Object handle(Request request, Response response) throws Exception {
        return null;
    }
}
