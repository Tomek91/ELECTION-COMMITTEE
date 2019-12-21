package pl.com.app.repository.impl;


import org.springframework.stereotype.Repository;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Token;
import pl.com.app.repository.TokenRepository;
import pl.com.app.repository.generic.AbstractGenericRepository;

import java.util.Optional;

@Repository
public class TokenRepositoryImpl extends AbstractGenericRepository<Token> implements TokenRepository {

    @Override
    public Optional<Token> getOneByVoterId(Long voterId) {
        try {
            if (voterId == null) {
                throw new NullPointerException("VOTER ID IS NULL");
            }
            return getEntityManager().createQuery("select t from " + getEntityClass().getCanonicalName()
                    + " t join t.voter v where v.id = :voterId", getEntityClass())
                    .setParameter("voterId", voterId)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }

    @Override
    public Optional<Token> findTokenByTokenAndPesel(String token, String pesel) {
        try {
            if (token == null) {
                throw new NullPointerException("TOKEN IS NULL");
            }
            if (pesel == null) {
                throw new NullPointerException("PESEL IS NULL");
            }
            return getEntityManager().createQuery("select t from " + getEntityClass().getCanonicalName()
                    + " t join t.voter v where v.pesel = :pesel and t.token = :token and t.expirationDate >= now()", getEntityClass())
                    .setParameter("pesel", pesel)
                    .setParameter("token", token)
                    .getResultList()
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.REPOSITORY, e.getMessage());
        }
    }
}
