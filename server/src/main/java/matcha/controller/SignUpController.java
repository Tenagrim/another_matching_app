package matcha.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import matcha.dto.AuthResponse;
import matcha.dto.SignUpRequest;
import matcha.dto.entity.User;
import matcha.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spark.Request;
import spark.Response;


@Component
@Tag(name = "auth", description = "sign up")
public class SignUpController extends BaseController{
    private AuthService authService;
    private ObjectMapper objectMapper;

    @Operation(summary = "Gets all users", tags = "auth")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Found the users",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthResponse.class))
                    })
    })
    @Parameter(schema = @Schema(implementation = SignUpRequest.class))
    @Override
    protected Object execute(Request request, Response response) throws Exception {
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
