package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.PoliticalPartyDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.PoliticalParty;
import pl.com.app.repository.CandidateRepository;
import pl.com.app.repository.PoliticalPartyRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PoliticalPartyService {
    private final PoliticalPartyRepository politicalPartyRepository;
    private final CandidateRepository candidateRepository;
    private final FileService fileService;
    private final ModelMapper modelMapper;

    public void addPoliticalParty(PoliticalPartyDTO politicalPartyDTO) {
        try {
            if (politicalPartyDTO == null) {
                throw new NullPointerException("POLITICAL PARTY IS NULL");
            }
            PoliticalParty politicalParty = modelMapper.fromPoliticalPartyDTOToPoliticalParty(politicalPartyDTO);
            String filename = fileService.addFile(politicalPartyDTO.getFile());
            politicalParty.setPhoto(filename);
            politicalPartyRepository.saveOrUpdate(politicalParty);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void modifyPoliticalParty(PoliticalPartyDTO politicalPartyDTO) {
        try {
            if (politicalPartyDTO == null) {
                throw new NullPointerException("CANDIDATE IS NULL");
            }

            PoliticalParty politicalParty = politicalPartyRepository
                    .findById(politicalPartyDTO.getId())
                    .orElseThrow(NullPointerException::new);

            String filename = fileService.updateFile(politicalPartyDTO.getFile(), politicalPartyDTO.getPhoto());
            politicalParty.setName(politicalPartyDTO.getName() == null ? politicalParty.getName() : politicalPartyDTO.getName());
            politicalParty.setSetYear(politicalPartyDTO.getSetYear() == null ? politicalParty.getSetYear() : politicalPartyDTO.getSetYear());

            politicalPartyRepository.saveOrUpdate(politicalParty);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public PoliticalPartyDTO getOnePoliticalParty(Long politicalPartyId) {
        try {
            if (politicalPartyId == null) {
                throw new NullPointerException("POLITICAL PARTY ID IS NULL");
            }

            return politicalPartyRepository
                    .findById(politicalPartyId)
                    .map(modelMapper::fromPoliticalPartyToPoliticalPartyDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public List<PoliticalPartyDTO> getAllPoliticalParties() {
        try {
            return politicalPartyRepository
                    .findAll()
                    .stream()
                    .map(modelMapper::fromPoliticalPartyToPoliticalPartyDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deletePoliticalParty(Long politicalPartyId) {
        try {
            if (politicalPartyId == null) {
                throw new NullPointerException("POLITICAL PARTY ID IS NULL");
            }
            Long candidatesNumbersByPoliticalParty = candidateRepository.candidatesNumbersByPoliticalParty(politicalPartyId);
            if (candidatesNumbersByPoliticalParty > 0){
                throw new IllegalArgumentException("NIE MOZNA USUNAC REKORDU POLITICAL PARTY 0 ID: " + politicalPartyId);
            }
            politicalPartyRepository.delete(politicalPartyId);
            PoliticalPartyDTO politicalPartyDTO = getOnePoliticalParty(politicalPartyId);
            fileService.deleteFile(politicalPartyDTO.getPhoto());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void saveOrUpdateAll(List<PoliticalPartyDTO> list) {
        try {
            if (list == null){
                throw new NullPointerException("LIST IS NULL");
            }

            politicalPartyRepository.saveOrUpdateAll(
                    list
                            .stream()
                            .map(modelMapper::fromPoliticalPartyDTOToPoliticalParty)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public PoliticalPartyDTO findByName(String name) {
        try {
            if (name == null){
                throw new NullPointerException("NAME IS NULL");
            }
            return politicalPartyRepository.findByName(name)
                    .map(modelMapper::fromPoliticalPartyToPoliticalPartyDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            politicalPartyRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
