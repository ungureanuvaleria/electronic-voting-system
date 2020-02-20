package utm.valeria.votelectronic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import utm.valeria.votelectronic.model.ScanRecord;

import java.util.Optional;

@Repository
public interface ScanRecordRepository extends JpaRepository<ScanRecord, Long> {
    
    Optional<ScanRecord> findScanRecordByScanId(Long scanId);
}
