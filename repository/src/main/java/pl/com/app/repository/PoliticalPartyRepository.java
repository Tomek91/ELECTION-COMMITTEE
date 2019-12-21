package pl.com.app.repository;


import pl.com.app.model.PoliticalParty;
import pl.com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface PoliticalPartyRepository extends GenericRepository<PoliticalParty> {
    Optional<PoliticalParty> findByName(String name);
}
