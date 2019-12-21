package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Result;
import pl.com.app.repository.ResultRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

@Repository
public class ResultRepositoryImpl extends AbstractGenericRepository<Result> implements ResultRepository {
    @Override
    public void deleteResultsByCandidate(Long candidateId) {
        try {
            if (candidateId == null) {
                throw new NullPointerException("CANDIDATE ID IS NULL");
            }
            getEntityManager().createQuery("select r from " + getEntityClass().getCanonicalName()
                    + " r join r.candidate c where c.id = :candidateId", getEntityClass())
                    .setParameter("candidateId", candidateId)
                    .getResultList()
                    .stream()
                    .forEach(t -> getEntityManager().remove(t));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
