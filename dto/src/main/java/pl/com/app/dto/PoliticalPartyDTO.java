package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoliticalPartyDTO {
    private Long id;
    @Pattern(regexp = "[A-Z ]+", message = "Name is not correct")
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Set year is not correct")
    private LocalDate setYear;
    private MultipartFile file;
    private String photo;
}
