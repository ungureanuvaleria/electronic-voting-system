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
        fingerprint.setSessionId(null);
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
        user.setName("Valeria");
        user.setSurname("Ungureanu");
        user.setFingerId(2);
        this.userRepository.save(user);
    
//        User user1 = new User();
//        user1.setName("Stanislav");
//        user1.setSurname("Sanduta");
//        user1.setFingerId(2);
//        this.userRepository.save(user1);

        PoliticalParty politicalParty1 = new PoliticalParty();
        politicalParty1.setPartyId("1");
        politicalParty1.setPartyName("PAS");
        this.politicalPartyRepository.save(politicalParty1);

        Candidate candidate1 = new Candidate();
        candidate1.setCandidateName("Marian");
        candidate1.setCandidateSurname("Lupu");
        candidate1.setCandidateImgUrl("../../assets/images/politicalParties/PDM.png");
        candidate1.setPoliticalParty(politicalParty1);

        Candidate candidate2 = new Candidate();
        candidate2.setCandidateName("Mihai");
        candidate2.setCandidateSurname("Ghimpu");
        candidate2.setCandidateImgUrl("../../assets/images/politicalParties/PL.png");
        candidate2.setPoliticalParty(politicalParty1);

        Candidate candidate3 = new Candidate();
        candidate3.setCandidateName("Iurie");
        candidate3.setCandidateSurname("Leancă");
        candidate3.setCandidateImgUrl("../../assets/images/politicalParties/PPEM.png");
        candidate3.setPoliticalParty(politicalParty1);

        Candidate candidate4 = new Candidate();
        candidate4.setCandidateName("Andrei");
        candidate4.setCandidateSurname("Năstase");
        candidate4.setCandidateImgUrl("../../assets/images/politicalParties/PPDA.png");
        candidate4.setPoliticalParty(politicalParty1);

        Candidate candidate5 = new Candidate();
        candidate5.setCandidateName("Maia");
        candidate5.setCandidateSurname("Sandu");
        candidate5.setCandidateImgUrl("../../assets/images/politicalParties/PAS.png");
        candidate5.setPoliticalParty(politicalParty1);

        Candidate candidate6 = new Candidate();
        candidate6.setCandidateName("Igor");
        candidate6.setCandidateSurname("Dodon");
        candidate6.setCandidateImgUrl("../../assets/images/politicalParties/PSRM.png");
        candidate6.setPoliticalParty(politicalParty1);

        this.candidateRepository.save(candidate1);
        this.candidateRepository.save(candidate2);
        this.candidateRepository.save(candidate3);
        this.candidateRepository.save(candidate4);
        this.candidateRepository.save(candidate5);
        this.candidateRepository.save(candidate6);
    }
}
