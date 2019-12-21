package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.PoliticalParty;
import pl.com.app.repository.PoliticalPartyRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

import java.util.Optional;

@Repository
public class PoliticalPartyRepositoryImpl extends AbstractGenericRepository<PoliticalParty> implements PoliticalPartyRepository {

    @Override
    public Optional<PoliticalParty> findByName(String name) {
        try {
            if (name == null) {
                throw new NullPointerException("NAME IS NULL");
            }
            return getEntityManager().createQuery("select p from " + getEntityClass().getCanonicalName()
                    + " p where p.name = :name", getEntityClass())
                    .setParameter("name", name)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
