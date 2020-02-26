package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.Candidate;
import utm.valeria.votelectronic.model.PoliticalParty;

import java.util.List;
import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    
    Optional<Candidate> findByCandidateNameAndCandidateSurname(String candidateName, String candidateSurname);

}
