package matcha.exceptions;

import org.eclipse.jetty.http.HttpStatus;

public class AppException extends RuntimeException {
    private Integer httpStatus;

    public AppException(String message,  HttpStatus httpStatus) {
        super(message);
        httpStatus = httpStatus;
    }

    public AppException(Integer httpStatus) {
        this.httpStatus = httpStatus;
    }

    public AppException() {
        httpStatus = 500;
    }


    public Integer getHttpStatus() {
        return httpStatus;
    }
}
