package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.Fingerprint;

import java.util.Optional;

@Repository
public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {
    
    Optional<Fingerprint> findByFingerprintId(String fingerprintId);
    Optional<Fingerprint> findBySessionId(String sessionId);
}
