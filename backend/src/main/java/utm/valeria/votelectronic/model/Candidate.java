package utm.valeria.votelectronic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "candidates")
@Data
public class Candidate {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "candidate_name")
    private String candidateName;
    
    @Column(name = "candidate_surname")
    private String candidateSurname;

    @Column(name = "candidate_img_Url")
    private String candidateImgUrl;
    
    @ManyToOne
    @JoinColumn(name = "party_id", nullable = false)
    private PoliticalParty politicalParty;
}
