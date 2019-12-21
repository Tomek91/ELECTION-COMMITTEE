package pl.com.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.*;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.parsers.json.*;
import pl.com.app.service.mappers.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
//@Transactional
public class DataInitializerService {
    private final ConstituencyService constituencyService;
    private final CandidateService candidateService;
    private final PoliticalPartyService politicalPartyService;
    private final VoterService voterService;
    private final TokenService tokenService;
    private final ResultService resultService;
    private final ElectionService electionService;
    private final CandidateConverter candidateConverter;
    private final PoliticalPartyConverter politicalPartyConverter;
    private final ConstituencyConverter constituencyConverter;
    private final TokenConverter tokenConverter;
    private final VoterConverter voterConverter;
    private final ModelMapper modelMapper;

    public void initData() {
        try {
            resultService.deleteAll();
            electionService.deleteAll();
            tokenService.deleteAll();
            voterService.deleteAll();
            candidateService.deleteAll();
            politicalPartyService.deleteAll();
            constituencyService.deleteAll();

            List<ConstituencyDTO> constituencyDTOList = constituencyConverter.fromJson().orElseThrow(NullPointerException::new);
            List<CandidateDTO> candidateDTOList = candidateConverter.fromJson().orElseThrow(NullPointerException::new);
            List<PoliticalPartyDTO> politicalPartyDTOList = politicalPartyConverter.fromJson().orElseThrow(NullPointerException::new);
            List<TokenDTO> tokenDTOList = tokenConverter.fromJson().orElseThrow(NullPointerException::new);
            List<VoterDTO> voterDTOList = voterConverter.fromJson().orElseThrow(NullPointerException::new);

            constituencyService.saveOrUpdateAll(constituencyDTOList);
            politicalPartyService.saveOrUpdateAll(politicalPartyDTOList);
            candidateService.saveOrUpdateAll(candidateDTOList
                    .stream()
                    .peek(c -> c.setPoliticalPartyDTO(politicalPartyService.findByName(c.getPoliticalPartyDTO().getName())))
                    .peek(c -> c.setConstituencyDTO(constituencyService.findByName(c.getConstituencyDTO().getName())))
                    .collect(Collectors.toList())
            );
            voterService.saveOrUpdateAll(voterDTOList
                    .stream()
                    .peek(v -> v.setConstituencyDTO(constituencyService.findByName(v.getConstituencyDTO().getName())))
                    .collect(Collectors.toList())
            );
            tokenService.saveOrUpdateAll(tokenDTOList
                    .stream()
                    .peek(t -> t.setVoterDTO(modelMapper.fromVoterToVoterDTO(
                            voterService
                            .findVoterByPesel(t.getVoterDTO().getPesel())
                            .orElseThrow(NullPointerException::new))
                    ))
                    .collect(Collectors.toList())
            );

        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.DATA_INITIALIZER, e.getMessage());
        }
    }
}
