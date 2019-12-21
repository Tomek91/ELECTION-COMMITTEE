package pl.com.app.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StatisticsByEducationDTO {
    private BigDecimal basicPercent;
    private BigDecimal secondarySchoolPercent;
    private BigDecimal occupationalPercent;
    private BigDecimal highSchoolPercent;
    private BigDecimal universityPercent;
}