package pl.krzysh.androiddevicepairing.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
	@RequestMapping("/")
    public ModelAndView handleIndex()
            throws ServletException, IOException {
        return new ModelAndView("index");
    }
}
