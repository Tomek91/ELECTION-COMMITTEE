package pl.com.app.repository;


import pl.com.app.model.Candidate;
import pl.com.app.repository.generic.GenericRepository;

public interface CandidateRepository extends GenericRepository<Candidate> {
    Long candidatesNumbersByPoliticalParty(Long politicalPartyId);
}
