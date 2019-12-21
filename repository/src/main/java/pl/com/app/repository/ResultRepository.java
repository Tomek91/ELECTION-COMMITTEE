package pl.com.app.repository;


import pl.com.app.model.Result;
import pl.com.app.repository.generic.GenericRepository;

public interface ResultRepository extends GenericRepository<Result> {
    void deleteResultsByCandidate(Long id);
}
