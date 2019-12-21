package pl.com.app.exceptions;

import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@ToString
public class   ExceptionInfo {
    private ExceptionCode exceptionCode;
    private String exceptionMessage;
    private LocalDateTime exceptionDateTime;

    public ExceptionInfo(ExceptionCode exceptionCode, String exceptionMessage) {
        this.exceptionCode = exceptionCode;
        this.exceptionMessage = exceptionMessage;
        this.exceptionDateTime = LocalDateTime.now();
    }
}
