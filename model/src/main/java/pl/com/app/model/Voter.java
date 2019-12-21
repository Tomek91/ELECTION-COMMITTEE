package pl.com.app.model;

import lombok.*;
import pl.com.app.model.enums.EEducation;
import pl.com.app.model.enums.EGender;

import javax.persistence.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "voters")
public class Voter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String pesel;

    @Enumerated(EnumType.STRING)
    private EGender eGender;

    @Enumerated(EnumType.STRING)
    private EEducation eEducation;

    @ManyToOne()
    @JoinColumn(name = "constituency_id", nullable = false)
    private Constituency constituency;

    @OneToOne(mappedBy = "voter")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Token token;

    private Boolean isVoteInFirstTour;

    private Boolean isVoteInSecondTour;
}
