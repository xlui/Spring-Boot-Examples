package app.xlui.example.swagger.web;

import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(value = "Hello World", description = "The first swagger base api!")
public class HelloController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ApiOperation(
            value = "向用户展示 Hello 消息",
            response = String.class,
            httpMethod = "GET"
    )
    @ApiImplicitParams(
            @ApiImplicitParam(
                    name = "name",
                    value = "用户名",
                    defaultValue = "Stranger",
                    dataTypeClass = String.class,
                    paramType = "path",
                    // examples NOT works on springfox version swagger
                    examples = @Example(value = @ExampleProperty(
                            mediaType = "text/plain",
                            value = "xlui"
                    ))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the page"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public String index(@RequestParam(defaultValue = "Stranger") String name) {
        String head = "<html>\n" +
                "  <head>\n" +
                "    <title>Swagger UI</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    body_content\n" +
                "  </body>\n" +
                "</html>";
        String swagger = "\n<br/>\n<div><a href=\"http://localhost:8080/swagger-ui.html\">localhost:8080/swagger-ui.html</a></div>";
        return head.replace("body_content", "Hello " + name + swagger);
    }

    @RequestMapping("/t")
    @ApiIgnore
    public String t() {
        return "Test ApiIgnore";
    }
}
