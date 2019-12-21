package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.ElectionDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.enums.ETour;
import pl.com.app.repository.ElectionRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Transactional
public class ElectionService {
    private final ElectionRepository electionRepository;
    private final ModelMapper modelMapper;

    public void addElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }

            ElectionDTO electionDTO = ElectionDTO
                    .builder()
                    .electionDateFrom(LocalDateTime.now())
                    .eTour(eTour)
                    .build();
            electionRepository.saveOrUpdate(modelMapper.fromElectionDTOToElection(electionDTO));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void endElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }

            Optional<ElectionDTO> electionDTOOpt = findActiveElection(eTour);
            if (!electionDTOOpt.isPresent()) {
                throw new NullPointerException("ELECTION IS NULL");
            }

            ElectionDTO electionDTO = electionDTOOpt.get();
            electionDTO.setElectionDateTo(LocalDateTime.now());
            electionRepository.saveOrUpdate(modelMapper.fromElectionDTOToElection(electionDTO));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public Optional<ElectionDTO> findActiveElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return electionRepository
                    .findActiveElection(eTour)
                    .map(modelMapper::fromElectionToElectionDTO);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public Optional<ElectionDTO> findEndElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return electionRepository
                    .findEndElection(eTour)
                    .map(modelMapper::fromElectionToElectionDTO);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public boolean isActiveElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return this.findActiveElection(eTour).isPresent();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public boolean isEndElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return this.findEndElection(eTour).isPresent();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            electionRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
