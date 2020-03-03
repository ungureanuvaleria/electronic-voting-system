package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.Workstation;

import java.util.Optional;

@Repository
public interface WorkstationRepository extends JpaRepository<Workstation, Long> {
    
    Optional<Workstation> findByWorkstationId(String workstationId);
    Optional<Workstation> findBySessionId(String sessionId);
}
