package pl.com.app.repository;


import pl.com.app.model.Token;
import pl.com.app.repository.generic.GenericRepository;

import java.util.Optional;

public interface TokenRepository extends GenericRepository<Token> {
    Optional<Token> getOneByVoterId(Long voterId);
    Optional<Token> findTokenByTokenAndPesel(String token, String pesel);
}
