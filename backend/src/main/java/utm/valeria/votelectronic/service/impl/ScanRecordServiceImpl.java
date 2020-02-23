package utm.valeria.votelectronic.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.exception.ScanRecordNotFoundException;
import utm.valeria.votelectronic.exception.UserNotFoundException;
import utm.valeria.votelectronic.model.*;
import utm.valeria.votelectronic.repository.ScanRecordRepository;
import utm.valeria.votelectronic.service.FingerprintService;
import utm.valeria.votelectronic.service.ScanRecordService;
import utm.valeria.votelectronic.service.UserService;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import java.util.Optional;

@Service
public class ScanRecordServiceImpl implements ScanRecordService {
    
    private ScanRecordRepository scanRecordRepository;
    private UserService userService;
    private FingerprintService fingerprintService;
    
    @Inject
    public void setScanRecordRepository(ScanRecordRepository scanRecordRepository) {
        this.scanRecordRepository = scanRecordRepository;
    }
    
    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Inject
    public void setFingerprintService(FingerprintService fingerprintService) {
        this.fingerprintService = fingerprintService;
    }
    
    @Override
    @Transactional
    public ScanRecord getScanRecordByScanId(Long scanId) throws ScanRecordNotFoundException {
        Optional<ScanRecord> scanRecordOptional = this.scanRecordRepository.findScanRecordByScanId(scanId);
        
        if (scanRecordOptional.isPresent()) {
            return scanRecordOptional.get();
        } else {
            throw new ScanRecordNotFoundException("ScanRecord with id " + scanId + " was not found!");
        }
    }
    
    @Override
    @Transactional
    public Long createNewRecord(FingerprintScan fingerprintScan) {
        String fingerprintId = fingerprintScan.getFingerprintId();
        int fingerId = fingerprintScan.getUserId();
        try {
            Fingerprint fingerprint = this.fingerprintService.getFingerprintByFingerprintId(fingerprintId);
            Workstation workstation = fingerprint.getWorkstation();
            User user = this.userService.getUserByFingerId(fingerId);
            
            ScanRecord scanRecord = ScanRecord.from(workstation, user);
            scanRecord = this.scanRecordRepository.save(scanRecord);
            return scanRecord.getScanId();
        } catch (FingerprintNotFoundException | ConstraintViolationException | UserNotFoundException ex) {
            System.out.println(ex.getMessage());
            return -1L;
        }
    }
}
