package pl.com.app.repository;


import pl.com.app.model.Election;
import pl.com.app.model.enums.ETour;
import pl.com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface ElectionRepository extends GenericRepository<Election> {
    Optional<Election> findActiveElection(ETour eTour);
    Optional<Election> findEndElection(ETour eTour);
}
