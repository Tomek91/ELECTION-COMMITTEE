package pl.com.app.repository;


import pl.com.app.model.Constituency;
import pl.com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface ConstituencyRepository extends GenericRepository<Constituency> {
    Optional<Constituency> findByName(String name);
}
