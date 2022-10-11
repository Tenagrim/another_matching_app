package matcha.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import matcha.SwaggerParser;
import matcha.constants.Const;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Api
@Path("/swagger")
@Controller
public class SwaggerController implements Route {

    // Build swagger json description
    private static final String swaggerJson;

    static {
        try {
            swaggerJson = SwaggerParser.getSwaggerJson((String) Const.get("basePackage"));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @GET
    @ApiOperation(value = "swagger definition", nickname = "swagger", tags = "swagger")
    @Override
    public Object handle(Request request, Response response) throws Exception {
        return swaggerJson;
    }
}
