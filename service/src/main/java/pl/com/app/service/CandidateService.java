package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.CandidateDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Candidate;
import pl.com.app.model.Constituency;
import pl.com.app.model.PoliticalParty;
import pl.com.app.repository.CandidateRepository;
import pl.com.app.repository.ConstituencyRepository;
import pl.com.app.repository.PoliticalPartyRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CandidateService {
    private final CandidateRepository candidateRepository;
    private final PoliticalPartyRepository politicalPartyRepository;
    private final ConstituencyRepository constituencyRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;
    private final ResultService resultService;

    public void addCandidate(CandidateDTO candidateDTO) {
        try {
            if (candidateDTO == null) {
                throw new NullPointerException("CANDIDATE IS NULL");
            }

            if (candidateDTO.getPoliticalPartyDTO() == null) {
                throw new NullPointerException("POLITICAL PARTY IS NULL");
            }

            if (candidateDTO.getPoliticalPartyDTO().getId() == null) {
                throw new NullPointerException("POLITICAL PARTY ID IS NULL");
            }

            if (candidateDTO.getConstituencyDTO() == null) {
                throw new NullPointerException("CONSTITUENCY IS NULL");
            }

            if (candidateDTO.getConstituencyDTO().getId() == null) {
                throw new NullPointerException("CONSTITUENCY ID IS NULL");
            }

            PoliticalParty politicalParty = politicalPartyRepository
                    .findById(candidateDTO.getPoliticalPartyDTO().getId())
                    .orElseThrow(NullPointerException::new);

            Constituency constituency = constituencyRepository
                    .findById(candidateDTO.getConstituencyDTO().getId())
                    .orElseThrow(NullPointerException::new);

            Candidate candidate = modelMapper.fromCandidateDTOToCandidate(candidateDTO);
            String filename = fileService.addFile(candidateDTO.getFile());
            candidate.setPhoto(filename);
            candidate.setConstituency(constituency);
            candidate.setPoliticalParty(politicalParty);
            candidateRepository.saveOrUpdate(candidate);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void modifyCandidate(CandidateDTO candidateDTO) {
        try {
            if (candidateDTO == null) {
                throw new NullPointerException("CANDIDATE IS NULL");
            }

            if (candidateDTO.getPoliticalPartyDTO() == null) {
                throw new NullPointerException("POLITICAL PARTY IS NULL");
            }

            if (candidateDTO.getPoliticalPartyDTO().getId() == null) {
                throw new NullPointerException("POLITICAL PARTY ID IS NULL");
            }

            if (candidateDTO.getConstituencyDTO() == null) {
                throw new NullPointerException("CONSTITUENCY IS NULL");
            }

            if (candidateDTO.getConstituencyDTO().getId() == null) {
                throw new NullPointerException("CONSTITUENCY ID IS NULL");
            }

            PoliticalParty politicalParty = politicalPartyRepository
                    .findById(candidateDTO.getPoliticalPartyDTO().getId())
                    .orElseThrow(NullPointerException::new);

            Constituency constituency = constituencyRepository
                    .findById(candidateDTO.getConstituencyDTO().getId())
                    .orElseThrow(NullPointerException::new);

            Candidate candidate = candidateRepository
                    .findById(candidateDTO.getId())
                    .orElseThrow(NullPointerException::new);

            candidate.setConstituency(constituency);
            candidate.setPoliticalParty(politicalParty);
            String filename = fileService.updateFile(candidateDTO.getFile(), candidateDTO.getPhoto());
            candidate.setName(candidateDTO.getName() == null ? candidate.getName() : candidateDTO.getName());
            candidate.setSurname(candidateDTO.getSurname() == null ? candidate.getSurname() : candidateDTO.getSurname());
            candidate.setBirthday(candidateDTO.getBirthday() == null ? candidate.getBirthday() : candidateDTO.getBirthday());

            candidateRepository.saveOrUpdate(candidate);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public CandidateDTO getOneCandidate(Long candidateId) {
        try {
            if (candidateId == null) {
                throw new NullPointerException("CANDIDATE ID IS NULL");
            }

            return candidateRepository
                    .findById(candidateId)
                    .map(modelMapper::fromCandidateToCandidateDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public List<CandidateDTO> getAllCandidates() {
        try {
            return candidateRepository
                    .findAll()
                    .stream()
                    .map(modelMapper::fromCandidateToCandidateDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteCandidate(Long candidateId) {
        try {
            if (candidateId == null) {
                throw new NullPointerException("CANDIDATE ID IS NULL");
            }
            CandidateDTO candidateDTO = getOneCandidate(candidateId);
            resultService.deleteResultsByCandidate(candidateDTO);
            candidateRepository.delete(candidateId);
            fileService.deleteFile(candidateDTO.getPhoto());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void saveOrUpdateAll(List<CandidateDTO> list) {
        try {
            if (list == null){
                throw new NullPointerException("LIST IS NULL");
            }

            candidateRepository.saveOrUpdateAll(
                    list
                            .stream()
                            .map(modelMapper::fromCandidateDTOToCandidate)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            resultService.deleteAll();
            candidateRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public List<CandidateDTO> findAll() {
        try {
            return candidateRepository.findAll()
                    .stream()
                    .map(modelMapper::fromCandidateToCandidateDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}