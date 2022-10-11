package matcha.controller;

import matcha.dto.Message;
import matcha.dto.Rs;
import matcha.exceptions.AppException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

public abstract class BaseController implements Route {
    @Override
    public Object handle(Request request, Response response) throws Exception {
        try {
            Object result = execute(request, response);
            return Rs.builder()
                    .data(result)
                    .code(HttpStatus.OK_200)
                    .message(HttpStatus.getMessage(200))
                    .build();
        }catch (AppException e){
            return getErrorResponse(e);
        }
        catch (Exception e){
            return getErrorResponse(e);
        }
    }

    private Rs getErrorResponse(Exception e){
        return Rs.builder()
                .data(new Message("Getting sign up"))
                .code(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .message(HttpStatus.getMessage(500))
                .build();
    }

    protected abstract Object execute(Request request, Response response) throws Exception;
}
