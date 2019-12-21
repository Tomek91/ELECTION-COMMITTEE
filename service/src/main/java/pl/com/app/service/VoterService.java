package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.AuthDTO;
import pl.com.app.dto.ConstituencyDTO;
import pl.com.app.dto.TokenDTO;
import pl.com.app.dto.VoterDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Constituency;
import pl.com.app.model.Voter;
import pl.com.app.model.enums.ETour;
import pl.com.app.repository.ConstituencyRepository;
import pl.com.app.repository.TokenRepository;
import pl.com.app.repository.VoterRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class VoterService {
    private final VoterRepository voterRepository;
    private final TokenRepository tokenRepository;
    private final ConstituencyRepository constituencyRepository;
    private final ModelMapper modelMapper;

    public VoterDTO addVoter(VoterDTO voterDTO) {
        try {
            if (voterDTO == null) {
                throw new NullPointerException("VOTER IS NULL");
            }

            if (voterDTO.getConstituencyDTO() == null) {
                throw new NullPointerException("CONSTITUENCY IS NULL");
            }

            if (voterDTO.getConstituencyDTO().getId() == null) {
                throw new NullPointerException("CONSTITUENCY ID IS NULL");
            }

            ConstituencyDTO constituencyDTO = constituencyRepository
                    .findById(voterDTO.getConstituencyDTO().getId())
                    .map(modelMapper::fromConstituencyToConstituencyDTO)
                    .orElseThrow(NullPointerException::new);

            voterDTO.setConstituencyDTO(constituencyDTO);
            Voter voter = voterRepository.saveOrUpdate(modelMapper.fromVoterDTOToVoter(voterDTO));
            return modelMapper.fromVoterToVoterDTO(voter);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void modifyVoter(VoterDTO voterDTO) {
        try {
            if (voterDTO == null) {
                throw new NullPointerException("VOTER IS NULL");
            }

            if (voterDTO.getConstituencyDTO() == null) {
                throw new NullPointerException("CONSTITUENCY IS NULL");
            }

            if (voterDTO.getConstituencyDTO().getId() == null) {
                throw new NullPointerException("CONSTITUENCY ID IS NULL");
            }

            Constituency constituency = constituencyRepository
                    .findById(voterDTO.getConstituencyDTO().getId())
                    .orElseThrow(NullPointerException::new);

            Voter voter = voterRepository
                    .findById(voterDTO.getId())
                    .orElseThrow(NullPointerException::new);

            voter.setConstituency(constituency);
            voter.setPesel(voterDTO.getPesel() == null ? voter.getPesel() : voterDTO.getPesel());
            voter.setEGender(voterDTO.getEGender() == null ? voter.getEGender() : voterDTO.getEGender());
            voter.setEEducation(voterDTO.getEEducation() == null ? voter.getEEducation() : voterDTO.getEEducation());
            voter.setIsVoteInFirstTour(voterDTO.getIsVoteInFirstTour() == null ? voter.getIsVoteInFirstTour() : voterDTO.getIsVoteInFirstTour());
            voter.setIsVoteInSecondTour(voterDTO.getIsVoteInSecondTour() == null ? voter.getIsVoteInSecondTour() : voterDTO.getIsVoteInSecondTour());

            voterRepository.saveOrUpdate(voter);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public VoterDTO getOneVoter(Long voterId) {
        try {
            if (voterId == null) {
                throw new NullPointerException("VOTER ID IS NULL");
            }

            return voterRepository
                    .findById(voterId)
                    .map(modelMapper::fromVoterToVoterDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public List<VoterDTO> getAllVoters() {
        try {
            return voterRepository
                    .findAll()
                    .stream()
                    .map(modelMapper::fromVoterToVoterDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteVoter(Long voterId) {
        try {
            if (voterId == null) {
                throw new NullPointerException("VOTER ID IS NULL");
            }
            TokenDTO tokenDTO = tokenRepository.getOneByVoterId(voterId)
                    .map(modelMapper::fromTokenToTokenDTO)
                    .orElseThrow(NullPointerException::new);
            tokenRepository.delete(tokenDTO.getId());
            voterRepository.delete(voterId);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            voterRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public boolean isNotUniquePesel(String pesel) {
        try {
            if (pesel == null) {
                throw new NullPointerException("PESEL IS NULL");
            }
            return this.findVoterByPesel(pesel).isPresent();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public Optional<Voter> findVoterByPesel(String pesel) {
        try {
            if (pesel == null) {
                throw new NullPointerException("PESEL IS NULL");
            }
            return voterRepository.findVoterByPesel(pesel);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void saveOrUpdateAll(List<VoterDTO> list) {
        try {
            if (list == null){
                throw new NullPointerException("LIST IS NULL");
            }

            voterRepository.saveOrUpdateAll(
                    list
                            .stream()
                            .map(modelMapper::fromVoterDTOToVoter)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public boolean verifyIsVoterCanVote(ETour eTour, AuthDTO authDTO) {
        try {
            if (eTour == null || authDTO == null){
                throw new NullPointerException("AUTH IS NULL");
            }
            VoterDTO voterDTO = this.findVoterByPesel(authDTO.getPesel())
                    .map(modelMapper::fromVoterToVoterDTO)
                    .orElseThrow(() -> new NullPointerException("VOTER IS NULL"));
            if (eTour == ETour.FIRST_TOUR){
                return voterDTO.getIsVoteInFirstTour() == null || !voterDTO.getIsVoteInFirstTour();
            } else {
                return voterDTO.getIsVoteInSecondTour() == null || !voterDTO.getIsVoteInSecondTour();
            }
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public List<VoterDTO> getAllVotersByTour(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return voterRepository.getAllVotersByTour(eTour)
                    .stream()
                    .map(modelMapper::fromVoterToVoterDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
