package id.go.lan.ikk.exception;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileNotFoundHandler implements ErrorController {
    @RequestMapping(path="/error",method = RequestMethod.GET)
    public Object handleError(HttpServletResponse response) {
        if (response.getStatus() == HttpStatus.NOT_FOUND.value()) {
            return "redirect:http://ikk.lan.go.id/not-found";
        } else {
            return response;
        }
    }
}
