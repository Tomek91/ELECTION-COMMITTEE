package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import pl.com.app.dto.VoterDTO;
import pl.com.app.model.enums.EEducation;
import pl.com.app.model.enums.EGender;
import pl.com.app.service.ConstituencyService;
import pl.com.app.service.VoterService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/voters")
public class VoterController {
    private final VoterService voterService;
    private final ConstituencyService constituencyService;

    @GetMapping("/add")
    public String addVoter(Model model) {
        model.addAttribute("voter", new VoterDTO());
        model.addAttribute("genders", EGender.values());
        model.addAttribute("educations", EEducation.values());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("errors", new HashMap<>());
        return "voters/add";
    }

    @PostMapping("/add")
    public String addVoter(@Valid @ModelAttribute VoterDTO voterDTO,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("voter", voterDTO);
            model.addAttribute("genders", EGender.values());
            model.addAttribute("educations", EEducation.values());
            model.addAttribute("constituencies", constituencyService.getAllConstituencies());
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(
                            FieldError::getField,
                            FieldError::getDefaultMessage,
                            (v1, v2) -> v1)));
            return "voters/add";
        }
        voterDTO = voterService.addVoter(voterDTO);
        return "redirect:/token/new/voter/" + voterDTO.getId();
    }

    @GetMapping("/modify/{id}")
    public String editVoter(@PathVariable Long id, Model model) {
        model.addAttribute("voter", voterService.getOneVoter(id));
        model.addAttribute("genders", EGender.values());
        model.addAttribute("educations", EEducation.values());
        model.addAttribute("constituencies", constituencyService.getAllConstituencies());
        model.addAttribute("errors", new HashMap<>());
        return "voters/modify";
    }

    @PostMapping("/modify")
    public String editVoterPost(@Valid @ModelAttribute VoterDTO voterDTO,
                                BindingResult bindingResult,
                                Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("voter", voterDTO);
            model.addAttribute("genders", EGender.values());
            model.addAttribute("educations", EEducation.values());
            model.addAttribute("constituencies", constituencyService.getAllConstituencies());
            model.addAttribute("errors", bindingResult
                    .getFieldErrors()
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage)));
            return "voters/modify";
        }
        voterService.modifyVoter(voterDTO);
        return "redirect:/voters";
    }

    @GetMapping
    public String getAllVoters(Model model) {
        model.addAttribute("voters", voterService.getAllVoters());
        return "voters/all";
    }

    @GetMapping("/{id}")
    public String getOneVoter(@PathVariable Long id, Model model) {
        model.addAttribute("voter", voterService.getOneVoter(id));
        return "voters/one";
    }

    @PostMapping("/delete")
    public String deleteVoter(@RequestParam Long id) {
        voterService.deleteVoter(id);
        return "redirect:/voters";
    }
}