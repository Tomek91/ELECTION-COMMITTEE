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
public class StatisticsByConstituencyDTO {
    private BigDecimal eastPercent;
    private BigDecimal westPercent;
    private BigDecimal southPercent;
    private BigDecimal northPercent;
}