package me.xlui.spring.web;

import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value = "Hello World", description = "The first swagger base api!")
public class HelloController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	@ApiOperation(value = "Show hello world to user", response = String.class, httpMethod = "GET")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful"),
			@ApiResponse(code = 401, message = "You are not authorized to view the page"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@ApiImplicitParam(name = "name", value = "用户名", required = true, dataType = "String", example = "xlui")
	public String index(String name) {
		return "Hello " + name;
	}
}
