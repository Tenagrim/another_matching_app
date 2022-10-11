package matcha.controller;

import lombok.extern.slf4j.Slf4j;
import matcha.dto.Message;
import matcha.dto.Rs;
import matcha.exceptions.AppException;
import org.eclipse.jetty.http.HttpStatus;
import spark.Request;
import spark.Response;
import spark.Route;

@Slf4j
public abstract class BaseController implements Route {
    @Override
    public abstract Object handle(Request request, Response response) throws Exception;

    protected Object handleWrapper(Request request, Response response) throws Exception {
        try {
            Object result = execute(request, response);
            return Rs.builder()
                    .data(result)
                    .code(HttpStatus.OK_200)
                    .message(HttpStatus.getMessage(200))
                    .build();
        }catch (AppException e){
            log.error("Excepcion",e);
            return getErrorResponse(e, response);
        }
        catch (Exception e){
            log.error("Excepcion",e);
            return getErrorResponse(e, response);
        }
    }

    private Rs getErrorResponse(AppException e, Response response){
        response.status(e.getHttpStatus());
        return Rs.builder()
                .data(new Message( e.getMessage() == null ? "Something went wrong" : e.getMessage()))
                .code(e.getHttpStatus())
                .message(HttpStatus.getMessage(e.getHttpStatus()))
                .build();
    }
    private Rs getErrorResponse(Exception e, Response response){

        response.status(500);
        return Rs.builder()
                .data(new Message("Something went wrong"))
                .code(HttpStatus.INTERNAL_SERVER_ERROR_500)
                .message(HttpStatus.getMessage(500))
                .build();
    }

    protected abstract Object execute(Request request, Response response) throws Exception;
}
