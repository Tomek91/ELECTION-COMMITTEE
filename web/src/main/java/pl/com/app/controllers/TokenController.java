package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.com.app.service.TokenService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final TokenService tokenService;

    @GetMapping("/new/voter/{id}")
    public String generateToken(@PathVariable Long id, Model model) {
        tokenService.addTokenByVoterId(id);
        model.addAttribute("token", tokenService.getOneByVoterId(id));
        return "tokens/one";
    }

}
