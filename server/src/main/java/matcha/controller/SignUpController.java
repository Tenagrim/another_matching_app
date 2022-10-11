package matcha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import matcha.dto.AuthResponse;
import matcha.dto.SignUpRequest;
import matcha.exceptions.AppException;
import matcha.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;



@Api
@Path("/signup")
@Produces("application/json")
@Controller
public class SignUpController extends BaseController {
    private AuthService authService;
    private ObjectMapper objectMapper;

    @POST
    @ApiOperation(value = "registration", nickname = "registration route", tags = "auth")
    @ApiImplicitParams({ //
         //   @ApiImplicitParam(required = true, dataType="string", name="auth", paramType = "header"), //
            @ApiImplicitParam(required = true, dataType = "matcha.dto.SignUpRequest", paramType = "body") //
    })
    @ApiResponses(value = { //
            @ApiResponse(code = 200, message = "Success", response = AuthResponse.class), //
    })
    @Override
    public Object handle(@ApiParam(hidden = true) Request request, @ApiParam(hidden = true) Response response) throws Exception {
        return handleWrapper(request,response);
    }

    @Override
    protected Object execute(Request request, Response response) throws Exception{
        return authService.signUp(objectMapper.readValue(request.body(), SignUpRequest.class), request.session());
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }
}
