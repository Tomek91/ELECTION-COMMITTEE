package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import pl.com.app.service.DataInitializerService;

import javax.servlet.ServletContext;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final DataInitializerService dataInitializerService;
    private final ServletContext servletContext;

    @GetMapping({"/", ""})
    public String welcome() {
        return "index";
    }

    @GetMapping("/default-data")
    public String getDefaultData() {
        dataInitializerService.initData();
        return "redirect:/";
    }

}
