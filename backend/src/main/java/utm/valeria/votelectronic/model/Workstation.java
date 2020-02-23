package utm.valeria.votelectronic.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "workstations")
@Data
public class Workstation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "workstation_id")
    @JsonProperty("workstation_id")
    private String workstationId;
    
    @Column(name = "workstation_password")
    @JsonProperty("workstation_password")
    private String password;
    
    @Column(name = "is_registered")
    private Boolean isRegistered;
    
    @Column(name = "session_id")
    private String sessionId;
    
    @OneToOne(mappedBy = "workstation", cascade = CascadeType.ALL, optional = false)
    private Fingerprint fingerprint;
}
