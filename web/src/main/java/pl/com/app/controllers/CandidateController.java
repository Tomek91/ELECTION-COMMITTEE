package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.com.app.dto.CandidateDTO;
import pl.com.app.service.CandidateService;
import pl.com.app.service.ConstituencyService;
import pl.com.app.service.PoliticalPartyService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/candidates")
public class CandidateController {
    private final CandidateService candidateService;
    private final PoliticalPartyService politicalPartyService;
    private final ConstituencyService constituencyService;

    @GetMapping("/add")
    public String addCandidate(Model model) {
        model.addAttribute("candidate", new CandidateDTO());
        model.addAttribute("politicalParties", politicalPartyService.getAllPoliticalParties());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("errors", new HashMap<>());
        return "candidates/add";
    }

    @PostMapping("/add")
    public String addCandidate(@Valid @ModelAttribute CandidateDTO candidateDTO,
                               BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("candidate", candidateDTO);
            model.addAttribute("politicalParties", politicalPartyService.getAllPoliticalParties());
            model.addAttribute("constituencies", constituencyService.getAllConstituencies());
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "candidates/add";
        }

        candidateService.addCandidate(candidateDTO);
        return "redirect:/candidates";
    }

    @GetMapping(value = "/modify/{id}")
    public String editCandidate(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getOneCandidate(id));
        model.addAttribute("politicalParties", politicalPartyService.getAllPoliticalParties());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("errors", new HashMap<>());
        return "candidates/modify";
    }

    @PostMapping(value = "/modify")
    public String editCandidatePost(@Valid @ModelAttribute CandidateDTO candidateDTO,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("candidate", candidateDTO);
            model.addAttribute("politicalParties", politicalPartyService.getAllPoliticalParties());
            model.addAttribute("constituencies", constituencyService.getAllConstituencies());
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "candidates/modify";
        }
        candidateService.modifyCandidate(candidateDTO);
        return "redirect:/candidates";
    }

    @GetMapping
    public String getAllCandidates(Model model) {
        model.addAttribute("candidates", candidateService.getAllCandidates());
        return "candidates/all";
    }

    @GetMapping("/{id}")
    public String getOneCandidate(@PathVariable Long id, Model model) {
        model.addAttribute("candidate", candidateService.getOneCandidate(id));
        return "candidates/one";
    }

    @PostMapping("/delete")
    public String deleteCandidate(@RequestParam Long id) {
        candidateService.deleteCandidate(id);
        return "redirect:/candidates";
    }
}
