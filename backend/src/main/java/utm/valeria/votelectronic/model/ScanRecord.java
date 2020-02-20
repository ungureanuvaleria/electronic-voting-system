package utm.valeria.votelectronic.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "scan_records")
@Data
public class ScanRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "scan_id")
    private Long scanId;
    
    @OneToOne
    @JoinColumn(name = "workstation_id", unique = true)
    private Workstation workstation;
    
    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;
    
    public static ScanRecord from(Workstation workstation, User user) {
        ScanRecord scanRecord = new ScanRecord();
        scanRecord.setWorkstation(workstation);
        scanRecord.setUser(user);
        return scanRecord;
    }
}
