package utm.valeria.votelectronic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "fingerprints_devices")
@Data
public class Fingerprint {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "fingerprint_id")
    private String fingerprintId;
    
    @OneToOne()
    @JoinColumn(name = "workstation_id")
    private Workstation workstation;
}
