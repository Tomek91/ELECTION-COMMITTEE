package pl.com.app.repository;


import pl.com.app.model.Voter;
import pl.com.app.model.enums.ETour;
import pl.com.app.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public interface VoterRepository extends GenericRepository<Voter> {
    Optional<Voter> findVoterByPesel(String pesel);
    List<Voter> getAllVotersByTour(ETour eTour);
}
