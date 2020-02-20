package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.model.Fingerprint;
import utm.valeria.votelectronic.model.FingerprintScan;

public interface FingerprintService {
    
    Fingerprint getFingerprintByFingerprintId(String fingerprintId) throws FingerprintNotFoundException;
    void transferFingerprintScanToWorkstation(FingerprintScan fingerprintScan) throws FingerprintNotFoundException;
}
