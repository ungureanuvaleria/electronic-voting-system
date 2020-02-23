package utm.valeria.votelectronic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "political_parties")
@Data
public class PoliticalParty {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "party_id")
    private String partyId;
    
    @Column(name = "party_name")
    private String partyName;
}
