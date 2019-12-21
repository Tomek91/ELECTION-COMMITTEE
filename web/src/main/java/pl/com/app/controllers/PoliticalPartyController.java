package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.com.app.dto.PoliticalPartyDTO;
import pl.com.app.service.PoliticalPartyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/political-parties")
public class PoliticalPartyController {
    private final PoliticalPartyService politicalPartyService;

    @GetMapping("/add")
    public String addPoliticalParty(Model model) {
        model.addAttribute("politicalParty", new PoliticalPartyDTO());
        model.addAttribute("errors", new HashMap<>());
        return "politicalParties/add";
    }

    @PostMapping("/add")
    public String addPoliticalParty(@Valid @ModelAttribute PoliticalPartyDTO politicalPartyDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("politicalParty", politicalPartyDTO);
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "politicalParties/add";
        }

        politicalPartyService.addPoliticalParty(politicalPartyDTO);
        return "redirect:/political-parties";
    }

    @GetMapping("/modify/{id}")
    public String editPoliticalParty(@PathVariable Long id, Model model) {
        model.addAttribute("politicalParty", politicalPartyService.getOnePoliticalParty(id));
        model.addAttribute("errors", new HashMap<>());
        return "politicalParties/modify";
    }

    @PostMapping("/modify")
    public String editPoliticalPartyPost(@Valid @ModelAttribute PoliticalPartyDTO politicalPartyDTO,
                                         BindingResult bindingResult,
                                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("politicalParty", politicalPartyDTO);
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "politicalParties/modify";
        }
        politicalPartyService.modifyPoliticalParty(politicalPartyDTO);
        return "redirect:/political-parties";
    }

    @GetMapping
    public String getAllPoliticalParties(Model model) {
        model.addAttribute("politicalParties", politicalPartyService.getAllPoliticalParties());
        return "politicalParties/all";
    }

    @GetMapping("/{id}")
    public String getOnePoliticalParty(@PathVariable Long id, Model model) {
        model.addAttribute("politicalParty", politicalPartyService.getOnePoliticalParty(id));
        return "politicalParties/one";
    }

    @PostMapping("/delete")
    public String deletePoliticalParty(@RequestParam Long id) {
        politicalPartyService.deletePoliticalParty(id);
        return "redirect:/political-parties";
    }
}
