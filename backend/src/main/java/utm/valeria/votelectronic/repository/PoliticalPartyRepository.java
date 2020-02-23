package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.PoliticalParty;

import java.util.Optional;

@Repository
public interface PoliticalPartyRepository extends JpaRepository<PoliticalParty, Long> {
    
    Optional<PoliticalParty> findByPartyName(String partyName);
}
