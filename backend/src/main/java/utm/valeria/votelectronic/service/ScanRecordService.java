package utm.valeria.votelectronic.service;

import utm.valeria.votelectronic.exception.ScanRecordNotFoundException;
import utm.valeria.votelectronic.model.FingerprintScan;
import utm.valeria.votelectronic.model.ScanRecord;

public interface ScanRecordService {
    
    ScanRecord getScanRecordByScanId(Long scanId) throws ScanRecordNotFoundException;
    Long createNewRecord(FingerprintScan fingerprintScan);
}
