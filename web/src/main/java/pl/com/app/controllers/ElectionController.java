package pl.com.app.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.com.app.dto.CandidateDTO;
import pl.com.app.model.enums.ETour;
import pl.com.app.service.CandidateService;
import pl.com.app.service.ElectionService;
import pl.com.app.service.ResultService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/elections")
public class ElectionController {

    private final ElectionService electionService;
    private final ResultService resultService;
    private final CandidateService candidateService;

    @GetMapping("/first-tour/start-election")
    public String startFirstTourElection() {
        electionService.addElection(ETour.FIRST_TOUR);
        resultService.deleteAll();
        List<CandidateDTO> candidateDTOList = candidateService.findAll();
        resultService.saveCandidates(ETour.FIRST_TOUR, candidateDTOList);
        return "redirect:/";
    }

    @GetMapping("/first-tour/end-election")
    public String endFirstTourElection() {
        electionService.endElection(ETour.FIRST_TOUR);
        List<CandidateDTO> candidateDTOList = resultService.findCandidatesForSecondTour();
        resultService.saveCandidates(ETour.SECOND_TOUR, candidateDTOList);
        return "redirect:/";
    }

    @GetMapping("/reset")
    public String resetElections () {
        electionService.deleteAll();
        return "redirect:/";
    }

    @GetMapping("/first-tour/results")
    public String resultsFirstTour(Model model) {
        model.addAttribute("tourResults", resultService.findResultsByTour(ETour.FIRST_TOUR));
        model.addAttribute("title", "RESULTS OF FIRST TOUR ELECTION");
        return "elections/tourResults";
    }

    @GetMapping("/second-tour/start-election")
    public String startSecondTourElection() {
        electionService.addElection(ETour.SECOND_TOUR);
        return "redirect:/";
    }

    @GetMapping("/second-tour/end-election")
    public String endSecondTourElection() {
        electionService.endElection(ETour.SECOND_TOUR);
        return "redirect:/";
    }

    @GetMapping("/second-tour/results")
    public String resultsSecondTour(Model model) {
        model.addAttribute("tourResults", resultService.findResultsByTour(ETour.SECOND_TOUR));
        model.addAttribute("title", "RESULTS OF SECOND TOUR ELECTION");
        return "elections/tourResults";
    }

    @GetMapping("/manage")
    public String manage(Model model) {
        boolean isFirstTourResults = electionService.isEndElection(ETour.FIRST_TOUR);
        boolean isSecondTour = electionService.isActiveElection(ETour.SECOND_TOUR);
        model.addAttribute("electionFirstTour", electionService.isActiveElection(ETour.FIRST_TOUR));
        model.addAttribute("electionFirstTourResults", isFirstTourResults);
        model.addAttribute("electionSecondTour", isSecondTour);
        model.addAttribute("electionSecondTourResults", electionService.isEndElection(ETour.SECOND_TOUR));
        if (isFirstTourResults && !isSecondTour){
            model.addAttribute("constituencies",
                    resultService.findCandidatesForSecondTour()
                            .stream()
                            .collect(Collectors.groupingBy(CandidateDTO::getConstituencyDTO))
                            .keySet());
        }
        return "elections/manage";
    }

    @GetMapping("/first-tour/statistics")
    public String statisticsFirstTour(Model model) {
        model.addAttribute("statisticsByGender", resultService.findStatisticsByGenderInTour(ETour.FIRST_TOUR));
        model.addAttribute("statisticsByConstituency", resultService.findStatisticsByConstituencyInTour(ETour.FIRST_TOUR));
        model.addAttribute("statisticsByEducation", resultService.findStatisticsByEducationInTour(ETour.FIRST_TOUR));
        return "elections/statistics";
    }

    @GetMapping("/second-tour/statistics")
    public String statisticsSecondTour(Model model) {
        model.addAttribute("statisticsByGender", resultService.findStatisticsByGenderInTour(ETour.SECOND_TOUR));
        model.addAttribute("statisticsByConstituency", resultService.findStatisticsByConstituencyInTour(ETour.SECOND_TOUR));
        model.addAttribute("statisticsByEducation", resultService.findStatisticsByEducationInTour(ETour.SECOND_TOUR));
        return "elections/statistics";
    }

}
