package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Election;
import pl.com.app.model.enums.ETour;
import pl.com.app.repository.ElectionRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

import java.util.Optional;

@Repository
public class ElectionRepositoryImpl extends AbstractGenericRepository<Election> implements ElectionRepository {

    @Override
    public Optional<Election> findActiveElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return getEntityManager().createQuery("select e from " + getEntityClass().getCanonicalName()
                    + " e where e.electionDateFrom <= now()"
                    + " and e.eTour = :eTour", getEntityClass())
                    .setParameter("eTour", eTour)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public Optional<Election> findEndElection(ETour eTour) {
        try {
            if (eTour == null) {
                throw new NullPointerException("ETOUR IS NULL");
            }
            return getEntityManager().createQuery("select e from " + getEntityClass().getCanonicalName()
                    + " e where e.electionDateFrom <= now() and e.electionDateTo is not null "
                    + " and e.eTour = :eTour", getEntityClass())
                    .setParameter("eTour", eTour)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}