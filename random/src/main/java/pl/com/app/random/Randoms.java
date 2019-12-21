package pl.com.app.random;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;

@Component
public class Randoms {
    private static final String CHARS = "123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String createRandomCode(int codeLength) {
        return new SecureRandom()
                .ints(codeLength, 0, CHARS.length())
                .mapToObj(CHARS::charAt)
                .map(Object::toString)
                .collect(Collectors.joining());
    }
}
