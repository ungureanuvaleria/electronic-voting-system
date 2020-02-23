package utm.valeria.votelectronic.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ballot_papers")
@Data
public class BallotPaper {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "candidate_id", nullable = false)
    private Candidate candidate;
    
    @Column(name = "vote_time")
    private LocalDateTime voteTime;
}
