package pl.com.app.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.app.model.enums.ETour;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "results")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ETour eTour;

    @Column(nullable = false)
    private Integer votes_number;

    @ManyToOne()
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
}
