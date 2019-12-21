package pl.com.app.service.mappers;


import org.springframework.stereotype.Service;
import pl.com.app.dto.*;
import pl.com.app.model.*;

import java.util.HashSet;

@Service
public class ModelMapper {
    public CandidateDTO fromCandidateToCandidateDTO(Candidate candidate) {
        return candidate == null ? null : CandidateDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .surname(candidate.getSurname())
                .birthday(candidate.getBirthday())
                .constituencyDTO(candidate.getConstituency() == null ? null : fromConstituencyToConstituencyDTO(candidate.getConstituency()))
                .politicalPartyDTO(candidate.getPoliticalParty() == null ? null : fromPoliticalPartyToPoliticalPartyDTO(candidate.getPoliticalParty()))
                .photo(candidate.getPhoto())
                .build();
    }

    public Candidate fromCandidateDTOToCandidate(CandidateDTO candidateDTO) {
        return candidateDTO == null ? null : Candidate.builder()
                .id(candidateDTO.getId())
                .name(candidateDTO.getName())
                .surname(candidateDTO.getSurname())
                .birthday(candidateDTO.getBirthday())
                .constituency(candidateDTO.getConstituencyDTO() == null ? null : fromConstituencyDTOToConstituency(candidateDTO.getConstituencyDTO()))
                .politicalParty(candidateDTO.getPoliticalPartyDTO() == null ? null : fromPoliticalPartyDTOToPoliticalParty(candidateDTO.getPoliticalPartyDTO()))
                .photo(candidateDTO.getPhoto())
                .results(new HashSet<>())
                .build();
    }

    public VoterDTO fromVoterToVoterDTO(Voter voter) {
        return voter == null ? null : VoterDTO.builder()
                .id(voter.getId())
                .pesel(voter.getPesel())
                .eGender(voter.getEGender())
                .eEducation(voter.getEEducation())
                .constituencyDTO(voter.getConstituency() == null ? null : fromConstituencyToConstituencyDTO(voter.getConstituency()))
                .isVoteInFirstTour(voter.getIsVoteInFirstTour())
                .isVoteInSecondTour(voter.getIsVoteInSecondTour())
                .build();
    }

    public Voter fromVoterDTOToVoter(VoterDTO voterDTO) {
        return voterDTO == null ? null : Voter.builder()
                .id(voterDTO.getId())
                .pesel(voterDTO.getPesel())
                .eGender(voterDTO.getEGender())
                .eEducation(voterDTO.getEEducation())
                .constituency(voterDTO.getConstituencyDTO() == null ? null : fromConstituencyDTOToConstituency(voterDTO.getConstituencyDTO()))
                .isVoteInFirstTour(voterDTO.getIsVoteInFirstTour())
                .isVoteInSecondTour(voterDTO.getIsVoteInSecondTour())
                .build();
    }

    public TokenDTO fromTokenToTokenDTO(Token token) {
        return token == null ? null : TokenDTO.builder()
                .id(token.getId())
                .token(token.getToken())
                .expirationDate(token.getExpirationDate())
                .voterDTO(token.getVoter() == null ? null : fromVoterToVoterDTO(token.getVoter()))
                .build();
    }

    public Token fromTokenDTOToToken(TokenDTO tokenDTO) {
        return tokenDTO == null ? null : Token.builder()
                .id(tokenDTO.getId())
                .token(tokenDTO.getToken())
                .expirationDate(tokenDTO.getExpirationDate())
                .voter(tokenDTO.getVoterDTO() == null ? null : fromVoterDTOToVoter(tokenDTO.getVoterDTO()))
                .build();
    }

    public PoliticalPartyDTO fromPoliticalPartyToPoliticalPartyDTO(PoliticalParty politicalParty) {
        return politicalParty == null ? null : PoliticalPartyDTO.builder()
                .id(politicalParty.getId())
                .name(politicalParty.getName())
                .setYear(politicalParty.getSetYear())
                .photo(politicalParty.getPhoto())
                .build();
    }

    public PoliticalParty fromPoliticalPartyDTOToPoliticalParty(PoliticalPartyDTO politicalPartyDTO) {
        return politicalPartyDTO == null ? null : PoliticalParty.builder()
                .id(politicalPartyDTO.getId())
                .name(politicalPartyDTO.getName())
                .setYear(politicalPartyDTO.getSetYear())
                .photo(politicalPartyDTO.getPhoto())
                .candidates(new HashSet<>())
                .build();
    }

    public ConstituencyDTO fromConstituencyToConstituencyDTO(Constituency constituency) {
        return constituency == null ? null : ConstituencyDTO.builder()
                .id(constituency.getId())
                .name(constituency.getName())
                .build();
    }

    public Constituency fromConstituencyDTOToConstituency(ConstituencyDTO constituencyDTO) {
        return constituencyDTO == null ? null : Constituency.builder()
                .id(constituencyDTO.getId())
                .name(constituencyDTO.getName())
                .voters(new HashSet<>())
                .candidates(new HashSet<>())
                .build();
    }

    public ResultDTO fromResultToResultDTO(Result result) {
        return result == null ? null : ResultDTO.builder()
                .id(result.getId())
                .eTour(result.getETour())
                .votes_number(result.getVotes_number())
                .candidateDTO(result.getCandidate() == null ? null : fromCandidateToCandidateDTO(result.getCandidate()))
                .build();
    }

    public Result fromResultDTOToResult(ResultDTO resultDTO) {
        return resultDTO == null ? null : Result.builder()
                .id(resultDTO.getId())
                .eTour(resultDTO.getETour())
                .votes_number(resultDTO.getVotes_number())
                .candidate(resultDTO.getCandidateDTO() == null ? null : fromCandidateDTOToCandidate(resultDTO.getCandidateDTO()))
                .build();
    }

    public ElectionDTO fromElectionToElectionDTO(Election election) {
        return election == null ? null : ElectionDTO.builder()
                .id(election.getId())
                .electionDateFrom(election.getElectionDateFrom())
                .electionDateTo(election.getElectionDateTo())
                .eTour(election.getETour())
                .build();
    }

    public Election fromElectionDTOToElection(ElectionDTO electionDTO) {
        return electionDTO == null ? null : Election.builder()
                .id(electionDTO.getId())
                .electionDateFrom(electionDTO.getElectionDateFrom())
                .electionDateTo(electionDTO.getElectionDateTo())
                .eTour(electionDTO.getETour())
                .build();
    }
}
