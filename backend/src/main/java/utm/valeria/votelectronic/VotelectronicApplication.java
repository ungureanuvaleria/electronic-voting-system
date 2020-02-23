package utm.valeria.votelectronic;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.model.*;
import utm.valeria.votelectronic.repository.*;

import javax.inject.Inject;

@SpringBootApplication
public class VotelectronicApplication implements CommandLineRunner {
    
    @Inject
    private FingerprintRepository fingerprintRepository;
    
    @Inject
    private WorkstationRepository workstationRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private PoliticalPartyRepository politicalPartyRepository;
    
    @Inject
    private CandidateRepository candidateRepository;

    public static void main(String[] args) {
        SpringApplication.run(VotelectronicApplication.class, args);
    }
    
    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Fingerprint fingerprint = new Fingerprint();
        fingerprint.setFingerprintId("Fingerprint01");
        fingerprint = this.fingerprintRepository.save(fingerprint);
    
        Workstation workstation = new Workstation();
        workstation.setWorkstationId("Workstation01");
        workstation.setPassword("Workstation01");
        workstation.setIsRegistered(Boolean.FALSE);
        workstation.setSessionId(null);
        workstation.setFingerprint(fingerprint);
        workstation = this.workstationRepository.save(workstation);
        
        fingerprint.setWorkstation(workstation);
        
        User user = new User();
        user.setName("John");
        user.setSurname("Connor");
        user.setFingerId(4);
        
        this.userRepository.save(user);
    
        PoliticalParty politicalParty = new PoliticalParty();
        politicalParty.setPartyId("PAS");
        politicalParty.setPartyName("PAS");
        politicalParty = this.politicalPartyRepository.save(politicalParty);
    
        Candidate candidate1 = new Candidate();
        candidate1.setCandidateName("Maia");
        candidate1.setCandidateSurname("Sandu");
        candidate1.setPoliticalParty(politicalParty);
    
        Candidate candidate2 = new Candidate();
        candidate2.setCandidateName("Andrei");
        candidate2.setCandidateSurname("Năstase");
        candidate2.setPoliticalParty(politicalParty);
        
        this.candidateRepository.save(candidate1);
        this.candidateRepository.save(candidate2);
    }
}
