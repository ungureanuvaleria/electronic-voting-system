package utm.valeria.votelectronic.service.impl;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import utm.valeria.votelectronic.enums.WorkstationMessagesEnum;
import utm.valeria.votelectronic.exception.WorkstationNotFoundException;
import utm.valeria.votelectronic.exception.WrongCredentialsException;
import utm.valeria.votelectronic.model.Fingerprint;
import utm.valeria.votelectronic.model.Workstation;
import utm.valeria.votelectronic.model.WorkstationCredentials;
import utm.valeria.votelectronic.repository.WorkstationRepository;
import utm.valeria.votelectronic.service.WorkstationService;

import javax.inject.Inject;
import java.util.Optional;
import java.util.function.Supplier;

@Service
public class WorkstationServiceImpl implements WorkstationService {
    
    private WorkstationRepository workstationRepository;
    private SimpMessagingTemplate brokerMessagingTemplate;
    
    @Inject
    public void setWorkstationRepository(WorkstationRepository workstationRepository) {
        this.workstationRepository = workstationRepository;
    }
    
    @Inject
    public void setBrokerMessagingTemplate(SimpMessagingTemplate brokerMessagingTemplate) {
        this.brokerMessagingTemplate = brokerMessagingTemplate;
    }
    
    @Override
    @Transactional
    public Workstation registerWorkstation(WorkstationCredentials workstationCredentials)
            throws WorkstationNotFoundException, WrongCredentialsException {
        
        String workstationId = workstationCredentials.getWorkstationId();
        Workstation workstation = getWorkstationByWorkstationId(workstationId);
        
        String workstationPassword = workstationCredentials.getWorkstationPassword();
        validateWorkstationPassword(workstation, workstationPassword);
        
        workstation.setIsRegistered(Boolean.TRUE);
        
        return workstation;
    }
    
    @Override
    @Transactional
    public Workstation getWorkstationByWorkstationId(String workstationId) throws WorkstationNotFoundException {
        Optional<Workstation> workstationOptional = this.workstationRepository.findByWorkstationId(workstationId);
        
        if (workstationOptional.isPresent()) {
            return workstationOptional.get();
        } else {
            throw new WorkstationNotFoundException("A workstation with " + workstationId + " id was not found!");
        }
    }
    
    private void validateWorkstationPassword(Workstation workstation, String workstationPassword)
            throws WrongCredentialsException {
        
        String password = workstation.getPassword();
        
        if (!workstationPassword.equals(password)) {
            throw new WrongCredentialsException("Wrong workstation password!");
        }
    }
    
    @Override
    @Transactional
    public void setWorkstationSessionId(String workstationId, String sessionId) throws WorkstationNotFoundException {
        Workstation workstation = this.getWorkstationByWorkstationId(workstationId);
        workstation.setSessionId(sessionId);
    }
    
    @Override
    @Transactional
    public void parseMessage(String message, String workstationSessionId) throws WorkstationNotFoundException {
        Optional<Workstation> workstationOptional = this.workstationRepository.findBySessionId(workstationSessionId);
        
        if (workstationOptional.isPresent()) {
            Fingerprint fingerprint = workstationOptional.get().getFingerprint();
            this.brokerMessagingTemplate.convertAndSendToUser(fingerprint.getSessionId(),
                    "/fingerprints",
                    message,
                    createHeaders(fingerprint.getSessionId()));
        }
    }
    
    private MessageHeaders createHeaders(String sessionId) {
        StompHeaderAccessor stompHeaderAccessor = StompHeaderAccessor.create(StompCommand.MESSAGE);
        stompHeaderAccessor.setSessionId(sessionId);
        stompHeaderAccessor.setLeaveMutable(Boolean.TRUE);
        return stompHeaderAccessor.getMessageHeaders();
    }
}
