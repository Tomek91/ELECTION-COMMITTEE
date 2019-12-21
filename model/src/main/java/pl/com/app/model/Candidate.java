package pl.com.app.model;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "candidates")
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    private LocalDate birthday;

    @ManyToOne()
    @JoinColumn(name = "constituency_id", nullable = false)
    private Constituency constituency;

    @ManyToOne()
    @JoinColumn(name = "political_party_id", nullable = false)
    private PoliticalParty politicalParty;

    private String photo;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Result> results = new HashSet<>();
}
