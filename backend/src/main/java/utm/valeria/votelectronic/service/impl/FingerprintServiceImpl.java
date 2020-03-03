package utm.valeria.votelectronic.service.impl;

import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.exception.FingerprintNotFoundException;
import utm.valeria.votelectronic.exception.UserNotFoundException;
import utm.valeria.votelectronic.model.Fingerprint;
import utm.valeria.votelectronic.model.FingerprintScan;
import utm.valeria.votelectronic.model.User;
import utm.valeria.votelectronic.model.Workstation;
import utm.valeria.votelectronic.repository.FingerprintRepository;
import utm.valeria.votelectronic.service.FingerprintService;
import utm.valeria.votelectronic.service.ScanRecordService;
import utm.valeria.votelectronic.service.UserService;

import javax.inject.Inject;
import java.util.Optional;

@Service
public class FingerprintServiceImpl implements FingerprintService {
    
    private FingerprintRepository fingerprintRepository;
    private SimpMessagingTemplate brokerMessagingTemplate;
    private UserService userService;
    private ScanRecordService scanRecordService;
    
    @Inject
    public void setFingerprintRepository(FingerprintRepository fingerprintRepository) {
        this.fingerprintRepository = fingerprintRepository;
    }
    
    @Inject
    public void setBrokerMessagingTemplate(SimpMessagingTemplate brokerMessagingTemplate) {
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }
    
    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    
    @Inject
    public void setScanRecordService(ScanRecordService scanRecordService) {
        this.scanRecordService = scanRecordService;
    }
    
    @Override
    @Transactional
    public Fingerprint getFingerprintByFingerprintId(String fingerprintId) throws FingerprintNotFoundException {
        Optional<Fingerprint> fingerprintOptional = this.fingerprintRepository.findByFingerprintId(fingerprintId);
        
        if (fingerprintOptional.isPresent()) {
            return fingerprintOptional.get();
        } else {
            throw new FingerprintNotFoundException("Fingerprint with " + fingerprintId + " id was not found");
        }
    }
    
    @Override
    @Transactional
    public void transferFingerprintScanToWorkstation(FingerprintScan fingerprintScan) {
        String fingerprintId = fingerprintScan.getFingerprintId();
        int fingerId = fingerprintScan.getUserId();
        try {
            Fingerprint fingerprint = getFingerprintByFingerprintId(fingerprintId);
            Workstation workstation = fingerprint.getWorkstation();
            User user = this.userService.getUserByFingerId(fingerId);
            
            if (workstation.getIsRegistered() && workstation.getSessionId() != null) {
                /*
                    ToDo: Verify it there is a ScanRecord entity for this workstation.
                        If there is, ignore this scan.
                 */
                Long scanRecordId = this.scanRecordService.createNewRecord(fingerprintScan);
                this.brokerMessagingTemplate.convertAndSendToUser(workstation.getSessionId(),
                        "/fingerprints",
                                    scanRecordId,
                        createHeaders(workstation.getSessionId()));
            }
        } catch (FingerprintNotFoundException | UserNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @Override
    @Transactional
    public void setFingerprintSessionId(String fingerprintId, String sessionId) throws FingerprintNotFoundException {
        Optional<Fingerprint> fingerprintOptional = this.fingerprintRepository.findByFingerprintId(fingerprintId);
        
        if (fingerprintOptional.isPresent()) {
            Fingerprint fingerprint = fingerprintOptional.get();
            fingerprint.setSessionId(sessionId);
        } else {
            throw new FingerprintNotFoundException("Fingerprint with id " + fingerprintId + "was not found!");
        }
    }
    
    @Override
    @Transactional
    public void parseNewMessage(String message, String fingerprintSessionId) throws FingerprintNotFoundException {
        System.out.println(message);
        Optional<Fingerprint> fingerprintOptional = this.fingerprintRepository.findBySessionId(fingerprintSessionId);
        if (fingerprintOptional.isPresent()) {
            Workstation workstation = fingerprintOptional.get().getWorkstation();
            this.brokerMessagingTemplate.convertAndSendToUser(workstation.getSessionId(),
                    "/fingerprints",
                               message,
                               createHeaders(workstation.getSessionId()));
        } else {
            throw new FingerprintNotFoundException("Fingerprint with sessionId" + fingerprintSessionId + "was not found!");
        }
    }
    
    private MessageHeaders createHeaders(String sessionId) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        stompHeaderAccessor.setSessionId(sessionId);
        stompHeaderAccessor.setLeaveMutable(Boolean.TRUE);
        return stompHeaderAccessor.getMessageHeaders();
    }
}
