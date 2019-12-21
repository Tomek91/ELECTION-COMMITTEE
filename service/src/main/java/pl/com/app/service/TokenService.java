package pl.com.app.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.app.dto.AuthDTO;
import pl.com.app.dto.TokenDTO;
import pl.com.app.dto.VoterDTO;
import pl.com.app.exceptions.ExceptionCode;
import pl.com.app.exceptions.MyException;
import pl.com.app.model.Token;
import pl.com.app.random.Randoms;
import pl.com.app.repository.TokenRepository;
import pl.com.app.service.mappers.ModelMapper;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class TokenService {
    private final TokenRepository tokenRepository;
    private final VoterService voterService;
    private final ModelMapper modelMapper;
    private final Randoms random;

    public void addTokenByVoterId(Long voterId) {
        try {
            if (voterId == null) {
                throw new NullPointerException("VOTER ID IS NULL");
            }
            VoterDTO voterDTO = voterService.getOneVoter(voterId);
            TokenDTO tokenDTO = TokenDTO.builder()
                    .token(generateUniqueToken())
                    .expirationDate(LocalDateTime.now().plusMinutes(15L))
                    .voterDTO(voterDTO)
                    .build();

            tokenRepository.saveOrUpdate(modelMapper.fromTokenDTOToToken(tokenDTO));
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    private String generateUniqueToken() {
        List<String> tokens = tokenRepository.findAll()
                .stream()
                .map(Token::getToken)
                .collect(Collectors.toList());
        String newToken = null;
        do{
            newToken = random.createRandomCode(4);
        }while (tokens.contains(newToken));
        return newToken;
    }

    public TokenDTO getOneByVoterId(Long voterId) {
        try {
            if (voterId == null) {
                throw new NullPointerException("VOTER ID IS NULL");
            }
            return tokenRepository.getOneByVoterId(voterId)
                    .map(modelMapper::fromTokenToTokenDTO)
                    .orElseThrow(NullPointerException::new);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            tokenRepository.deleteAll();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public boolean verifyCredentials(AuthDTO authDTO) {
        try {
            if (authDTO == null) {
                throw new NullPointerException("AUTH IS NULL");
            }
            if (authDTO.getToken() == null) {
                throw new NullPointerException("TOKEN IS NULL");
            }
            if (authDTO.getPesel() == null) {
                throw new NullPointerException("PESEL IS NULL");
            }
            return this.findTokenByTokenAndPesel(authDTO.getToken(), authDTO.getPesel()).isPresent();
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    private Optional<Token> findTokenByTokenAndPesel(String token, String pesel) {
        try {
            if (token == null) {
                throw new NullPointerException("TOKEN IS NULL");
            }
            if (pesel == null) {
                throw new NullPointerException("PESEL IS NULL");
            }
            return tokenRepository.findTokenByTokenAndPesel(token, pesel);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void saveOrUpdateAll(List<TokenDTO> list) {
        try {
            if (list == null){
                throw new NullPointerException("LIST IS NULL");
            }

            tokenRepository.saveOrUpdateAll(
                    list
                            .stream()
                            .map(modelMapper::fromTokenDTOToToken)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }

    public void deleteToken(Long tokenId) {
        try {
            if (tokenId == null) {
                throw new NullPointerException("TOKEN ID IS NULL");
            }
            tokenRepository.delete(tokenId);
        } catch (Exception e) {
            throw new MyException(ExceptionCode.SERVICE, e.getMessage());
        }
    }
}
