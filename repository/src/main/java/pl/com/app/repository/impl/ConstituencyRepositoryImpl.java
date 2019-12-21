package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Constituency;
import pl.com.app.repository.ConstituencyRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

import java.util.Optional;

@Repository
public class ConstituencyRepositoryImpl extends AbstractGenericRepository<Constituency> implements ConstituencyRepository {

    @Override
    public Optional<Constituency> findByName(String name) {
        try {
            if (name == null) {
                throw new NullPointerException("NAME IS NULL");
            }
            return getEntityManager().createQuery("select c from " + getEntityClass().getCanonicalName()
                    + " c where c.name = :name", getEntityClass())
                    .setParameter("name", name)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
