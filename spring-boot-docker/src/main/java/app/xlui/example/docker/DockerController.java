package app.xlui.example.docker;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DockerController {
	@RequestMapping("/")
	@ResponseBody
	public String index() {
		return "hello, docker!<br>run in docker!<br>new";
	}
}
