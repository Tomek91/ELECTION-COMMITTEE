package pl.com.app.model;

import lombok.*;
import pl.com.app.model.enums.ETour;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "elections")
public class Election {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime electionDateFrom;

    private LocalDateTime electionDateTo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETour eTour;
}
